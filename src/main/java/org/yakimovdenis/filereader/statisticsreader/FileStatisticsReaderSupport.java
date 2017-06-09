package org.yakimovdenis.filereader.statisticsreader;

import org.yakimovdenis.filereader.statisticsreader.charreaders.CharacterEqualiator;

public interface FileStatisticsReaderSupport {
    void setWordReader(CharacterEqualiator wordReader);

    void setDigitReader(CharacterEqualiator digitReader);

    void setSpecialSymbolReader(CharacterEqualiator specialSymbolReader);

    CharacterEqualiator getWordReader();

    CharacterEqualiator getDigitReader();

    CharacterEqualiator getSpecialSymbolReader();
}
