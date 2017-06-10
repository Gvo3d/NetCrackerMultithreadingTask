package org.yakimovdenis.filereader;

import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReader;

public class FileStatisticsBuilderImpl implements FileStatisticsBuilder {
    private FileStatisticsFactory fileStatisticsFactory;
    private FileStatisticsReaderSupportFactory fileStatisticsReaderSupportFactory;

    public FileStatisticsBuilderImpl(FileStatisticsFactory fileStatisticsFactory, FileStatisticsReaderSupportFactory fileStatisticsReaderSupportFactory) {
        this.fileStatisticsFactory = fileStatisticsFactory;
        this.fileStatisticsReaderSupportFactory = fileStatisticsReaderSupportFactory;
    }

    public FileStatisticsReader buildInstance() {
        FileStatisticsReader reader = fileStatisticsFactory.createInstance();
        reader.setFileStatisticsReaderSupport(fileStatisticsReaderSupportFactory.createInstance());
        return reader;
    }
}
