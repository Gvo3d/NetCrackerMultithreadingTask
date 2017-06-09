package org.yakimovdenis.filereader.statisticsreader;

import org.yakimovdenis.filereader.statisticsreader.charreaders.CharacterEqualiator;
import org.yakimovdenis.filereader.statisticsreader.support.NoFileStatisticReaderSupportException;
import org.yakimovdenis.filereader.statisticsreader.tasks.CharacterReaderTask;
import org.yakimovdenis.filereader.statisticsreader.tasks.StatisticsTask;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FileStatisticsReaderImpl implements FileStatisticsReader {
    private Logger log = LogManager.getLogManager().getLogger(FileStatisticsReaderImpl.class.getName());
    private static final int THREADSCOUNT = 4;
    private File targetFile;
    private FileStatisticsReaderSupport support = null;
    private ExecutorService threadPool;

    public void setTargetFile(File targetFile) {
        this.targetFile = targetFile;
    }

    public void setFileStatisticsReaderSupport(FileStatisticsReaderSupport support) {
        this.support = support;
    }

    public void calculate() {
        for (Runnable runnable: init()){
            threadPool.execute(new Thread(runnable));
        }
    }

    private List<Runnable> init(){
        if (null==support){
            throw new NoFileStatisticReaderSupportException(this.toString());
        }
        String data = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(targetFile);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            data = dataInputStream.readUTF();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can not read targetFile "+targetFile.getName()+" from "+this.toString());
        }
        if (null==data){
            log.log(Level.SEVERE, targetFile.getName()+" read a NULL from "+this.toString());
        }

        threadPool = Executors.newFixedThreadPool(THREADSCOUNT);
        StatisticsTask statisticsTask = new StatisticsTask("StatisticTask");
        String charactedTask = "CharReaderTask";

        CharacterEqualiator[] newTasks = new CharacterEqualiator[3];
        newTasks[0] = support.getWordReader();
        newTasks[1] = support.getDigitReader();
        newTasks[2] = support.getSpecialSymbolReader();
       List<Runnable> runnables = new ArrayList<Runnable>();
        for (CharacterEqualiator equaliator: newTasks){
            Exchanger<Long> exchanger = new Exchanger<Long>();
            CharacterReaderTask task = new CharacterReaderTask(charactedTask, new String(data),equaliator,exchanger);
            statisticsTask.addNewReaderTaskData(charactedTask,task.getMethod(),exchanger);
            runnables.add(task);
        }
        runnables.add(statisticsTask);
        return runnables;
    }

}
