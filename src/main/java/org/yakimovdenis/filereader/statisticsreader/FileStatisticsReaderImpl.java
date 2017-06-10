package org.yakimovdenis.filereader.statisticsreader;

import org.yakimovdenis.filereader.statisticsreader.charreaders.CharacterEqualiator;
import org.yakimovdenis.filereader.statisticsreader.support.NoFileStatisticReaderSupportException;
import org.yakimovdenis.filereader.statisticsreader.tasks.CharacterReaderTask;
import org.yakimovdenis.filereader.statisticsreader.tasks.ReaderTaskExchangeData;
import org.yakimovdenis.filereader.statisticsreader.tasks.StatisticsTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileStatisticsReaderImpl implements FileStatisticsReader {
    private static final Logger log = Logger.getLogger(FileStatisticsReaderImpl.class.getName());
    private static final int THREADSCOUNT = 4;
    private File targetFile;
    private FileStatisticsReaderSupport support = null;
    private ExecutorService threadPool;
    private final Object monitor = new Object();

    public void setTargetFile(File targetFile) {
        this.targetFile = targetFile;
    }

    public void setFileStatisticsReaderSupport(FileStatisticsReaderSupport support) {
        this.support = support;
    }

    public void calculate() {
        List<Future> futures = new ArrayList<Future>();
        for (Runnable runnable : init()) {
            futures.add(threadPool.submit(runnable));
        }
        synchronized (monitor) {
            log.log(Level.INFO, "Making reader object to wait until interrupt: " + this.toString());
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                log.log(Level.SEVERE, "FileStatisticsReader " + this.toString() + " from " + Thread.currentThread().getName() + " was interrupted while sleep. Something went wrong. Reason: " + e);
            }
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
        this.threadPool.shutdownNow();
        this.targetFile = null;
        log.log(Level.FINE, "Finalizing reader class: " + this.toString());
    }

    //Method for creating FixedThreadPool, creating Runnables and fill them with data.
    private List<Runnable> init() {
        if (null == support) {
            throw new NoFileStatisticReaderSupportException(this.toString());
        }
        threadPool = Executors.newFixedThreadPool(THREADSCOUNT);
        String statisticsName = "StatisticTask";
        StatisticsTask statisticsTask = new StatisticsTask(statisticsName, monitor);
        log.log(Level.INFO, "Creating statistics task " + statisticsTask.toString());
        String charactedTask = "CharReaderTask";
        CharacterEqualiator[] newTasks = new CharacterEqualiator[3];
        newTasks[0] = support.getWordReader();
        newTasks[1] = support.getDigitReader();
        newTasks[2] = support.getSpecialSymbolReader();
        List<Runnable> runnables = new ArrayList<Runnable>();
        for (CharacterEqualiator equaliator : newTasks) {
            Exchanger<ReaderTaskExchangeData> exchanger = new Exchanger<ReaderTaskExchangeData>();
            CharacterReaderTask task = new CharacterReaderTask(charactedTask, targetFile, equaliator, exchanger);
            statisticsTask.addNewReaderTaskData(charactedTask, exchanger);
            runnables.add(task);
            StringBuilder builder = new StringBuilder();
            builder.append("Creating reader task: ");
            builder.append(task.toString());
            builder.append(" with ");
            builder.append(equaliator.getUsedMethod());
            builder.append(" method");
            log.log(Level.INFO, builder.toString());
        }
        runnables.add(statisticsTask);
        return runnables;
    }

}
