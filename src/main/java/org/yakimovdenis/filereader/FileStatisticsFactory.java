package org.yakimovdenis.filereader;

import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReader;

/**
 * @interface FileStatisticsFactory
 *
 * <p> Factory object that will create and return <code>FileStatisticsReader</code> implementstion.
 *
 * @autor Yakimov Denis
 */
public interface FileStatisticsFactory {

    /**
     * Method for creating instance of <code>FileStatisticsReader</code> interface.
     *
     * @return FileStatisticsReader.
     */
    FileStatisticsReader createInstance();
}
