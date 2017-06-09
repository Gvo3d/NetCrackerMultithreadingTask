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
    private Exchanger<ReaderTaskExchangeData> statisticsDataExchanger;
    private long validCharsCount;
    private String thisThreadName;
    private File outputFile;
    private String data;
    private ReaderTaskExchangeData exchangeDataHolder;

    public CharacterReaderTask(String threadName, String data, CharacterEqualiator equaliator, Exchanger<ReaderTaskExchangeData> statisticsDataExchanger) {
        this.data = data;
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
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(outputFile);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't open file writer from "+thisThreadName+". Reason: "+e);
        }

        for (Character character:data.toCharArray()){
            System.out.println("CHAR='"+character+"'");
            if (character.equals(lineSeparator)){
                try {
                    System.out.println("WRITING AN EOL!");

//TODO: Узнать у Ромы, можно ли загрузить файл и каждому раздать по копии данных? Еслинет, то разобраться с стринг токенайзером и строками, просмотреть почему виснут потоки после завершения своей логики. В случае завершения потоков - убить их самостоятельно. Добавить джавадоки ко всем интерфейсам, добавить README.MD - файл, пробежаться по всем классам и поубирать ошибки, неточности, слегка прокомментировать методы
//                    StreamTokenizer streamTokenizer = new StreamTokenizer(reader);
//                    streamTokenizer.resetSyntax();
//                    streamTokenizer.wordChars(0x23, 0xFF);
//                    streamTokenizer.wordChars('|', '|');
//                    streamTokenizer.wordChars('.', '.');
//                    streamTokenizer.wordChars(',', ',');
//                    streamTokenizer.wordChars(' ', ' ');
//                    streamTokenizer.whitespaceChars('\n', '\n');

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
        this.exchangeDataHolder.setEndingTime(System.currentTimeMillis());
        this.exchangeDataHolder.setResultChars(validCharsCount);
        exchangeData(this.exchangeDataHolder);
        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Can't flush data and close file from "+thisThreadName+". Reason: "+e);
        }
    }
}
