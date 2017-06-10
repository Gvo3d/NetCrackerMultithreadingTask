package org.yakimovdenis.filereader.statisticsreader.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Exchanger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticsTask implements Runnable {
    private static final Logger log = Logger.getLogger(StatisticsTask.class.getName());
    private Set<ReaderTaskDataHolder> dataHolder;
    private String thisThreadName;
    private File outputFile;
    private FileWriter outWriter;
    private Object notifyMonitor;

    public StatisticsTask(String threadsName, Object notifyMonitor) {
        this.notifyMonitor = notifyMonitor;
        this.thisThreadName = threadsName;
        this.dataHolder = new HashSet<ReaderTaskDataHolder>();
        this.outputFile = new File(thisThreadName + "_" + Thread.currentThread().getName());
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public void setOutputFileName(String fileName) {
        this.outputFile = new File(fileName);
    }

    private void init() {
        if (null == outputFile) {
            outputFile = new File(thisThreadName + ".txt");
        }
        if (outputFile.exists()) {
            outputFile.delete();
        }
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't create file(" + outputFile.getName() + ") for Statistics Task: " + thisThreadName + ". Reason: " + e);
        }
    }

    //Method for creating ReaderTaskDataHolder that will contain data about reader thread work
    public void addNewReaderTaskData(String taskName, Exchanger<ReaderTaskExchangeData> exchanger) {
        ReaderTaskDataHolder holder = new ReaderTaskDataHolder(taskName, exchanger);
        dataHolder.add(holder);
    }

    public void run() {
        init();
        Iterator<ReaderTaskDataHolder> iterator = dataHolder.iterator();
        while (iterator.hasNext()) {
            ReaderTaskDataHolder holder = iterator.next();
            ReaderTaskExchangeData result = null;
            try {
                result = (holder.getExchanger().exchange(null));
            } catch (InterruptedException e) {
                log.log(Level.SEVERE, "Can't exchange data with " + holder.getTaskName() + " from " + thisThreadName + ". Reason: " + e);
            }
            holder.setExchangedData(result);
        }
        outWriter = null;
        try {
            outWriter = new FileWriter(outputFile);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't manage file (" + outputFile.getName() + ") for writer from " + thisThreadName + ". Reason: " + e);
        }
        DateFormat currentTimeFormat = new SimpleDateFormat("HH:mm:ss_MM-dd-yyyy");
        StringBuilder builder = new StringBuilder();
        builder.append("Statistics thread: ");
        builder.append(thisThreadName);
        builder.append(" ");
        builder.append(Thread.currentThread().getName());
        builder.append(" ");
        builder.append(this.toString());
        builder.append("\nCurrent time: ");
        builder.append(currentTimeFormat.format(new Date(System.currentTimeMillis())));
        builder.append("\n\n");
        for (ReaderTaskDataHolder taskData : dataHolder) {
            Long startData = taskData.getExchangedData().getStartingTime();
            Long endData = taskData.getExchangedData().getEndingTime();
            builder.append("Thread ");
            builder.append(taskData.getTaskName());
            builder.append(" ");
            builder.append(taskData.getExchangedData().getThreadId());
            builder.append(" was using ");
            builder.append(taskData.getExchangedData().getMethod());
            builder.append(" search method.\nStarted in ");
            builder.append(currentTimeFormat.format(new Date(startData)));
            builder.append(", ended in ");
            builder.append(currentTimeFormat.format(new Date(endData)));
            builder.append(" and lasted for ");
            builder.append(endData - startData);
            builder.append(" milliseconds.\nResulting number of valid characters is ");
            builder.append(taskData.getExchangedData().getResultChars());
            builder.append("\nResults contains in ");
            builder.append(taskData.getExchangedData().getOutputFile());
            builder.append("\n\n");
        }
        try {
            outWriter.write(builder.toString());
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't write data to file " + outputFile.getName() + " from " + thisThreadName + ". Reason: " + e);
        }
        try {
            outWriter.flush();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't flush data and close file " + outputFile.getName() + " from " + thisThreadName + ". Reason: " + e);
        }
        synchronized (notifyMonitor) {
            log.log(Level.FINE, "Statistics gathering was ended, notifying sleeping reader from " + this.toString());
            notifyMonitor.notify();
        }
        try {
            finalize();
        } catch (Throwable throwable) {
            log.log(Level.WARNING, "Algorithm worked fine, but throwed " + throwable + " while finalizing " + this.toString());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        outWriter.close();
        outputFile = null;
        log.log(Level.FINE, "Finalizing statistics thread: " + this.toString());
    }
}
