package org.yakimovdenis.filereader.statisticsreader;

import org.yakimovdenis.filereader.statisticsreader.charreaders.CharacterEqualiator;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FileStatisticsReaderSupportImpl implements FileStatisticsReaderSupport {
    private Logger log = LogManager.getLogManager().getLogger(FileStatisticsReaderSupportImpl.class.getName());
    private CharacterEqualiator wordReader;
    private CharacterEqualiator digitReader;
    private CharacterEqualiator specialSymbolReader;

    public void setWordReader(CharacterEqualiator wordReader) {
        this.wordReader = wordReader;
    }

    public void setDigitReader(CharacterEqualiator digitReader) {
        this.digitReader = digitReader;
    }

    public void setSpecialSymbolReader(CharacterEqualiator specialSymbolReader) {
        this.specialSymbolReader = specialSymbolReader;
    }

    public CharacterEqualiator getWordReader() {
        return wordReader.clone();
    }

    public CharacterEqualiator getDigitReader() {
        return digitReader.clone();
    }

    public CharacterEqualiator getSpecialSymbolReader() {
        return specialSymbolReader.clone();
    }
}
