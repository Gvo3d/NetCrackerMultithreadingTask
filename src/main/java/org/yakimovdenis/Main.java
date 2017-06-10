package org.yakimovdenis;

import org.yakimovdenis.filecreator.FileCreator;
import org.yakimovdenis.filecreator.FileCreatorFactory;
import org.yakimovdenis.filecreator.FileCreatorFactoryImpl;
import org.yakimovdenis.filereader.*;
import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReader;
import org.yakimovdenis.logging.LoggingConfiguration;

import java.io.File;

public class Main {
    private static final String FILENAME = "test.txt";
    private static final int MINIMALLINELENGTH = 40;
    private static final int MINIMALLINENUM = 15;
    private static final int FILECREATORSEED = 100;

    public static void main(String[] args) {
        LoggingConfiguration.configure();
        File targetFile = createFileWithRandomContent(generateFileCreationFactory().createInstance());
        System.out.println("Created file "+targetFile.getName());
        FileStatisticsReader reader = createFileStatisticsReader();
        System.out.println("Created reader "+reader.toString());
        reader.setTargetFile(targetFile);
        reader.calculate();
        System.out.println("Calculations done.");
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
