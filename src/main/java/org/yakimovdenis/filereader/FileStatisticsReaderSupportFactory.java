package org.yakimovdenis.filereader;

import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReaderSupport;

/**
 * Created by Gvozd on 09.06.2017.
 */
public interface FileStatisticsReaderSupportFactory {
    FileStatisticsReaderSupport createInstance();
}
