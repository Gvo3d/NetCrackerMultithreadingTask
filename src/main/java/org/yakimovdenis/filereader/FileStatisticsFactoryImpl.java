package org.yakimovdenis.filereader;

import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReader;
import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReaderImpl;

public class FileStatisticsFactoryImpl implements FileStatisticsFactory {
    public FileStatisticsReader createInstance() {
        return new FileStatisticsReaderImpl();
    }
}
