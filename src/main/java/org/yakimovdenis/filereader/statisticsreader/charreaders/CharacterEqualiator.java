package org.yakimovdenis.filereader.statisticsreader.charreaders;

public interface CharacterEqualiator extends Cloneable {
    abstract CharacterEqualiator clone();
    public String getUsedMethod();
    public boolean matchesCondition(Character character);
}
