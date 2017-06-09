package org.yakimovdenis.filereader.statisticsreader.charreaders;

/**
 * Created by Gvozd on 09.06.2017.
 */
public class AlphabetCharReaderImpl implements CharacterEqualiator {

    public CharacterEqualiator clone() {
        return this.clone();
    }

    public String getUsedMethod() {
        return "alphabetic";
    }

    public boolean matchesCondition(Character character) {
        return Character.isLetter(character);
    }
}
