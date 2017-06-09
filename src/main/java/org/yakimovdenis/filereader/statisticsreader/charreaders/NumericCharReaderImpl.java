package org.yakimovdenis.filereader.statisticsreader.charreaders;

/**
 * Created by Gvozd on 09.06.2017.
 */
public class NumericCharReaderImpl implements CharacterEqualiator {

    public CharacterEqualiator clone() {
        return this.clone();
    }

    public String getUsedMethod() {
        return "numeric";
    }

    public boolean matchesCondition(Character character) {
        return Character.isDigit(character);
    }
}
