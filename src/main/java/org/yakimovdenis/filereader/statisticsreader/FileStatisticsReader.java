package org.yakimovdenis.filereader.statisticsreader;

import java.io.File;

/**
 * @interface FileStatisticsReader
 *
 * <p> Main class that holds characters equaliators, creates threads and launches them. Main method is 'calculate'.
 *
 * @autor Yakimov Denis
 */
public interface FileStatisticsReader {
    /**
     * Setter for <code>FileStatisticsReaderSupport</code> - specialized class that holds three implementations of <code>CharacterEqualiator</code>.
     *
     * <p>This specialized support class is needed for filling threads with equaliators.
     *
     * @param support a FileStatisticsReaderSupport implementation.
     * @return void.
     */
    void setFileStatisticsReaderSupport(FileStatisticsReaderSupport support);

    /**
     * Setter for target file.
     *
     * <p>This is a setter for a file that will be processed in three threads.
     *
     * @param targetFile a java.io.File object that will be parsed.
     * @return void.
     */
    void setTargetFile(File targetFile);

    /**
     * Main method for parsing file.
     *
     * <p>Method that will process the file, create four threads and launch them, gather information and destroy threadPool after completion.
     *
     * @return void.
     */
    void calculate();
}
