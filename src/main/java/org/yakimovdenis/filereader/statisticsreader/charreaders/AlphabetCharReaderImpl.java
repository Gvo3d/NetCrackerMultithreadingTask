package org.yakimovdenis.filereader.statisticsreader.charreaders;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AlphabetCharReaderImpl implements CharacterEqualiator {
    private static final Logger log = Logger.getLogger(AlphabetCharReaderImpl.class.getName());

    public CharacterEqualiator clone() {
        try {
            return (CharacterEqualiator) super.clone();
        } catch (CloneNotSupportedException e) {
            log.log(Level.SEVERE, "Can't clone object with standart clone method, switching to newInstance()");
            try {
                return this.getClass().newInstance();
            } catch (Exception e1) {
                log.log(Level.SEVERE, "Can't clone object with newInstance() clone method");
            }
        }
        return null;
    }

    public String getUsedMethod() {
        return "alphabetic";
    }

    public boolean matchesCondition(Character character) {
        return Character.isLetter(character);
    }
}
