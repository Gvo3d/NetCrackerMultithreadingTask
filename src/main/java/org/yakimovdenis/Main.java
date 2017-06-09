package org.yakimovdenis;

import org.yakimovdenis.filecreator.FileCreator;
import org.yakimovdenis.filecreator.FileCreatorFactory;
import org.yakimovdenis.filecreator.FileCreatorFactoryImpl;
import org.yakimovdenis.filereader.*;
import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReader;

import java.io.*;
import java.util.logging.LogManager;

public class Main {
    private static final String FILENAME = "test.txt";
    private static final int MINIMALLINELENGTH = 40;
    private static final int MINIMALLINENUM = 15;
    private static final int FILECREATORSEED = 100;

    public static void main(String[] args) {
        loggerInit();
        File targetFile = createFileWithRandomContent(generateFileCreationFactory().createInstance());
        FileStatisticsReader reader = createFileStatisticsReader();
        reader.setTargetFile(targetFile);
        reader.calculate();
    }

    private static void loggerInit(){
        File propertiesFile = new File("src/main/resources/logging.properties");
        InputStream propertiesStream = null;
        try {
            propertiesStream = new FileInputStream(propertiesFile);
        } catch (FileNotFoundException e) {
            System.err.println("There are no logging.properties file for logger.");
        }
        try {
            LogManager.getLogManager().readConfiguration(propertiesStream);
        } catch (IOException e) {
            System.err.println("Logger wasn't able to start properly.");
        }
    }

    private static FileCreatorFactory generateFileCreationFactory(){
       return new FileCreatorFactoryImpl();
    }

    private static File createFileWithRandomContent(FileCreator fileCreator){
        fileCreator.setFileName(FILENAME);
        fileCreator.setMinimalLineLingth(MINIMALLINELENGTH);
        fileCreator.setMinimalLineNumber(MINIMALLINENUM);
        fileCreator.setSeed(FILECREATORSEED);
        return fileCreator.doGenerate();
    }

    private static FileStatisticsReader createFileStatisticsReader(){
        FileStatisticsFactory factory = new FileStatisticsFactoryImpl();
        FileStatisticsReaderSupportFactory supportFactory = new FileStatisticsReaderSupportFactoryImpl();
        FileStatisticsBuilder builder = new FileStatisticsBuilderImpl(factory, supportFactory);
        return builder.buildInstance();
    }
}
