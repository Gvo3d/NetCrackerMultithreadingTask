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
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class StatisticsTask implements Runnable {
    private Logger log = LogManager.getLogManager().getLogger(StatisticsTask.class.getName());
    private Set<ReaderTaskDataHolder> dataHolder;
    private String thisThreadName;
    private File outputFile;

    public StatisticsTask(String threadsName) {
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

    public void addNewReaderTaskData(String taskName, String method, Exchanger<Long> exchanger) {
        ReaderTaskDataHolder holder = new ReaderTaskDataHolder(taskName, method, exchanger);
        dataHolder.add(holder);
    }

    private Long exchangeData(Long toExchange, ReaderTaskDataHolder holder){
        Long result = null;
        try {
            result = (holder.getExchanger().exchange(0L));
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Can't exchange data with "+holder.getTaskName()+" from " + thisThreadName + ". Reason: " + e);
        }
        return result;
    }

    public void run() {
        Iterator<ReaderTaskDataHolder> iterator = dataHolder.iterator();
        while (iterator.hasNext()) {
            ReaderTaskDataHolder holder = iterator.next();
            holder.setStartingTime(exchangeData(0L, holder));
        }
        iterator = dataHolder.iterator();
        while (iterator.hasNext()) {
            ReaderTaskDataHolder holder = iterator.next();
                holder.setEndingTime(exchangeData(0L, holder));
        }
        iterator = dataHolder.iterator();
        while (iterator.hasNext()) {
            ReaderTaskDataHolder holder = iterator.next();
            holder.setResultChars(exchangeData(0L, holder));
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(outputFile);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't manage file ("+outputFile.getName()+") for writer from " + thisThreadName + ". Reason: " + e);
        }
        DateFormat currentTimeFormat = new SimpleDateFormat("HH-mm-ss_MM-dd-yyyy");
        for (ReaderTaskDataHolder taskData : dataHolder) {
            Long startData = taskData.getStartingTime();
            Long endData = taskData.getEndingTime();
            StringBuilder builder = new StringBuilder();
            builder.append("Thread ");
            builder.append(taskData.getTaskName());
            builder.append(" was using ");
            builder.append(taskData.getMethod());
            builder.append(" search method.\nStarted in ");
            builder.append(currentTimeFormat.format(new Date(startData)));
            builder.append(", ended in ");
            builder.append(currentTimeFormat.format(new Date(endData)));
            builder.append(" and lasted for ");
            builder.append(endData - startData);
            builder.append(" milliseconds.\nResulting number of valid characters is ");
            builder.append(taskData.getResultChars());
            builder.append("\n");
            try {
                writer.write(builder.toString());
            } catch (IOException e) {
                log.log(Level.SEVERE, "Can't write data to file "+outputFile.getName()+" from " + thisThreadName + ". Reason: " + e);
            }
        }
    }
}
