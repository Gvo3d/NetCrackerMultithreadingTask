package org.yakimovdenis.filereader.statisticsreader;

import java.io.File;

public interface FileStatisticsReader {
    public void setFileStatisticsReaderSupport(FileStatisticsReaderSupport support);
    public void setTargetFile(File targetFile);
    public void calculate();
}
