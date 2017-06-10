package org.yakimovdenis.filereader.statisticsreader.tasks;

import org.yakimovdenis.filereader.statisticsreader.charreaders.CharacterEqualiator;

import java.io.*;
import java.util.concurrent.Exchanger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CharacterReaderTask implements Runnable {
    private static final Logger log = Logger.getLogger(CharacterReaderTask.class.getName());
    private CharacterEqualiator equaliator;
    private char lineSeparator = '\n';
    private Exchanger<ReaderTaskExchangeData> statisticsDataExchanger;
    private long validCharsCount;
    private String thisThreadName;
    private File outputFile;
    private File targetFile;
    private FileWriter outWriter;
    private ReaderTaskExchangeData exchangeDataHolder;

    public CharacterReaderTask(String threadName, File targetFile, CharacterEqualiator equaliator, Exchanger<ReaderTaskExchangeData> statisticsDataExchanger) {
        this.targetFile = targetFile;
        this.equaliator = equaliator;
        this.statisticsDataExchanger = statisticsDataExchanger;
        thisThreadName = threadName+"_"+equaliator.getUsedMethod();
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
        this.exchangeDataHolder = new ReaderTaskExchangeData(System.currentTimeMillis(), equaliator.getUsedMethod());
    }

    private void exchangeData(ReaderTaskExchangeData data){
        try {
            statisticsDataExchanger.exchange(data);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Can't exchangeDataHolder data with listener-thread from "+thisThreadName+". Reason: "+e);
        }
    }

    public void run() {
        init();
       outWriter = null;
        try {
            outWriter = new FileWriter(outputFile);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't open file writer from "+thisThreadName+". Reason: "+e);
        }

        String data = null;
        try {
            FileReader reader = new FileReader(targetFile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((data = bufferedReader.readLine())!=null){
                for (Character character: data.toCharArray()){
                    if (equaliator.matchesCondition(character)){
                    try {
                        outWriter.write(character);
                    } catch (IOException e) {
                        log.log(Level.SEVERE, "Can't write data to file from "+thisThreadName+". Reason: "+e);
                    }
                    validCharsCount++;
                }
                }
                outWriter.write(lineSeparator);
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can not read targetFile "+targetFile.getName()+" from "+this.toString());
        }

        this.exchangeDataHolder.setEndingTime(System.currentTimeMillis());
        this.exchangeDataHolder.setResultChars(validCharsCount);
        this.exchangeDataHolder.setOutputFile(outputFile.getAbsolutePath());
        this.exchangeDataHolder.setThreadId(Thread.currentThread().getName()+" "+this.toString());
        exchangeData(this.exchangeDataHolder);
        try {
            outWriter.flush();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't flush data and close file from "+thisThreadName+". Reason: "+e);
        }
        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        outWriter.close();
        targetFile = null;
        statisticsDataExchanger = null;
        log.log(Level.FINE, "Finalizing reader thread: "+this.toString());
    }
}
