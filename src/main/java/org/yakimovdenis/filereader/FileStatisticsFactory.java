package org.yakimovdenis.filereader;

import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReader;

/**
 * Created by Gvozd on 09.06.2017.
 */
public interface FileStatisticsFactory {
    FileStatisticsReader createInstance();
}
