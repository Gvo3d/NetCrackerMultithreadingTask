package org.yakimovdenis.filereader;

import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReaderSupport;
import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReaderSupportImpl;
import org.yakimovdenis.filereader.statisticsreader.charreaders.AlphabetCharReaderImpl;
import org.yakimovdenis.filereader.statisticsreader.charreaders.NumericCharReaderImpl;
import org.yakimovdenis.filereader.statisticsreader.charreaders.SpecialSymbolCharReaderImpl;

public class FileStatisticsReaderSupportFactoryImpl implements FileStatisticsReaderSupportFactory {
    private FileStatisticsReaderSupport support;

    public FileStatisticsReaderSupportFactoryImpl() {
        init();
    }

    private void init() {
        FileStatisticsReaderSupport statisticsReaderSupport = new FileStatisticsReaderSupportImpl();
        statisticsReaderSupport.setWordReader(new AlphabetCharReaderImpl());
        statisticsReaderSupport.setDigitReader(new NumericCharReaderImpl());
        statisticsReaderSupport.setSpecialSymbolReader(new SpecialSymbolCharReaderImpl());
        this.support = statisticsReaderSupport;
    }

    public FileStatisticsReaderSupport createInstance() {
        return support;
    }
}
