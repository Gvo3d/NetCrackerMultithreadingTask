package org.yakimovdenis.filereader.statisticsreader.charreaders;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by Gvozd on 09.06.2017.
 */
public class NumericCharReaderImpl implements CharacterEqualiator {
    private Logger log = LogManager.getLogManager().getLogger(NumericCharReaderImpl.class.getName());

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
        return "numeric";
    }

    public boolean matchesCondition(Character character) {
        return Character.isDigit(character);
    }
}
