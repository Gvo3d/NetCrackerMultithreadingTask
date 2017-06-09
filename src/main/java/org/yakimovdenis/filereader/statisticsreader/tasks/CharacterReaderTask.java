package org.yakimovdenis.filereader.statisticsreader.tasks;

import org.yakimovdenis.filereader.statisticsreader.charreaders.CharacterEqualiator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Exchanger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class CharacterReaderTask implements Runnable {
    private Logger log = LogManager.getLogManager().getLogger(CharacterReaderTask.class.getName());
    private CharacterEqualiator equaliator;
    private char lineSeparator = '\n';
    private Exchanger<Long> timeExchanger;
    private int validCharsCount;
    private String thisThreadName;
    private File outputFile;
    private String data;

    public CharacterReaderTask(String threadName, String data, CharacterEqualiator equaliator, Exchanger<Long> timeExchanger) {
        this.equaliator = equaliator;
        this.timeExchanger = timeExchanger;
        thisThreadName = threadName+"_"+Thread.currentThread().getName();
    }

    public String getMethod(){
        return equaliator.getUsedMethod();
    }

    public void setLineSeparator(char lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public void setOutputFileName(String fileName){
        this.outputFile = new File(fileName);
    }

    private void init(){
        if (null==outputFile){
            outputFile = new File(thisThreadName+".txt");
        }
        if (outputFile.exists()){
            outputFile.delete();
        }
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't create file("+outputFile.getName()+") for Statistics Task: "+thisThreadName+". Reason: "+e);
        }
    }

    private void exchangeTime(){
        Long now = System.currentTimeMillis();
        try {
            timeExchanger.exchange(now);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Can't exchange time with listener-thread from "+thisThreadName+". Reason: "+e);
        }
    }

    private void exchangeData(Long data){
        try {
            timeExchanger.exchange(data);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Can't exchange data with listener-thread from "+thisThreadName+". Reason: "+e);
        }
    }

    public void run() {
        init();
        exchangeTime();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(outputFile);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't open file writer from "+thisThreadName+". Reason: "+e);
        }
        for (Character character:data.toCharArray()){
            if (character.equals(lineSeparator)){
                try {
                    fileWriter.write(lineSeparator);
                } catch (IOException e) {
                    log.log(Level.SEVERE, "Can't write line separator to file from "+thisThreadName+". Reason: "+e);
                }
            } else {
                if (equaliator.matchesCondition(character)){
                    try {
                        fileWriter.write(character);
                    } catch (IOException e) {
                        log.log(Level.SEVERE, "Can't write data to file from "+thisThreadName+". Reason: "+e);
                    }
                    validCharsCount++;
                }
            }
        }
        exchangeTime();
        exchangeData((long) validCharsCount);
    }
}
