package org.yakimovdenis.filereader.statisticsreader.charreaders;

public class SpecialSymbolCharReaderImpl implements CharacterEqualiator {
    private String regexString = "\\W\\D{0}";

    public void setRegexString(String regexString) {
        this.regexString = regexString;
    }

    public CharacterEqualiator clone() {
        return this.clone();
    }

    public String getUsedMethod() {
        return "special characters";
    }

    public boolean matchesCondition(Character character) {
            return Character.toString(character).matches(regexString);
    }
}
