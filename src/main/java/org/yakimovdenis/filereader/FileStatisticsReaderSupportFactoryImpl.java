package org.yakimovdenis.filereader;

import org.yakimovdenis.filereader.statisticsreader.charreaders.AlphabetCharReaderImpl;
import org.yakimovdenis.filereader.statisticsreader.charreaders.NumericCharReaderImpl;
import org.yakimovdenis.filereader.statisticsreader.charreaders.SpecialSymbolCharReaderImpl;
import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReaderSupport;
import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReaderSupportImpl;

public class FileStatisticsReaderSupportFactoryImpl implements FileStatisticsReaderSupportFactory {
    public FileStatisticsReaderSupport createInstance() {
        FileStatisticsReaderSupport statisticsReaderSupport = new FileStatisticsReaderSupportImpl();
        statisticsReaderSupport.setWordReader(new AlphabetCharReaderImpl());
        statisticsReaderSupport.setDigitReader(new NumericCharReaderImpl());
        statisticsReaderSupport.setSpecialSymbolReader(new SpecialSymbolCharReaderImpl());
        return statisticsReaderSupport;
    }
}
