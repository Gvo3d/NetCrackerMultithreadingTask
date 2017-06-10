package org.yakimovdenis.filereader;

import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReader;

/**
 * @interface FileStatisticsBuilder
 *
 * <p> Special class that will build <code>FileStatisticsReader</code> and fill it with <code>FileStatisticsReaderSupport</code>.
 *
 * @autor Yakimov Denis
 */
public interface FileStatisticsBuilder {

    /**
     * Method for building <code>FileStatisticsReader</code> object.
     *
     * @return FileStatisticsReader.
     */
    FileStatisticsReader buildInstance();
}
