package org.yakimovdenis.filereader;

import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReaderSupport;

/**
 * @interface FileStatisticsReaderSupportFactory
 *
 * <p> Factory object that will create and return <code>FileStatisticsReaderSupport</code> implementstion.
 *
 * @autor Yakimov Denis
 */
public interface FileStatisticsReaderSupportFactory {

    /**
     * Method for creating instance of <code>FileStatisticsReaderSupport</code> interface.
     *
     * @return FileStatisticsReaderSupport.
     */
    FileStatisticsReaderSupport createInstance();
}
