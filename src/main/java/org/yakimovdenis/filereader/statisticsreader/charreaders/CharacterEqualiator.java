package org.yakimovdenis.filereader.statisticsreader.charreaders;

/**
 * @interface <code>CharacterEqualiation</code> interface for a special purpose - to pass a <code>Character</code> throught a special rule validation.
 *
 * <p>This class validates a character with <code>matchesCondition</code> method and returns a boolean if a character matches rule or not.
 *
 * @autor Yakimov Denis
 */
public interface CharacterEqualiator extends Cloneable {
    /**
     * Method that overrides standart <code>java.lang.Object</code> clone method.
     *
     * @return CharacterEqualiator    a clone of current character equaliator.
     */
    abstract CharacterEqualiator clone();

    /**
     * This method is returning a string about what rule-method was used. Like 'numeric' or 'alphabetic'. For logging purposes.
     *
     * @return String    method that was used.
     */
    String getUsedMethod();

    /**
     * Main method for validating a character. Returns 'true' is character is valid or false if it's not.
     *
     * @param character  a target character that will be matched.
     * @return boolean    result of validation.
     */
    boolean matchesCondition(Character character);
}
