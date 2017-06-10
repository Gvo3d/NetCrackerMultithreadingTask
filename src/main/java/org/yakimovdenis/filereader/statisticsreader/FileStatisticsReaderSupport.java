package org.yakimovdenis.filereader.statisticsreader;

import org.yakimovdenis.filereader.statisticsreader.charreaders.CharacterEqualiator;

/**
 * @interface FileStatisticsReaderSupport
 *
 * <p> Support class that holds three variants of CharacterEquailators.
 *
 * @autor Yakimov Denis
 */
public interface FileStatisticsReaderSupport {

    /**
     * Setter for <code>CharacterEqualiator</code> that will validate characters with letter equaliation rule.
     *
     * @param wordReader a CharacterEqualiator implementation with word-rule.
     * @return void.
     */
    void setWordReader(CharacterEqualiator wordReader);

    /**
     * Setter for <code>CharacterEqualiator</code> that will validate characters with digit equaliation rule.
     *
     * @param digitReader a CharacterEqualiator implementation with number-rule.
     * @return void.
     */
    void setDigitReader(CharacterEqualiator digitReader);

    /**
     * Setter for <code>CharacterEqualiator</code> that will validate characters with symbols equaliation rule.
     *
     * @param specialSymbolReader a CharacterEqualiator implementation with special characters-rule.
     * @return void.
     */
    void setSpecialSymbolReader(CharacterEqualiator specialSymbolReader);

    /**
     * Getter for <code>CharacterEqualiator</code> that will validate characters with letter equaliation rule.
     *
     * <p>This getter must return a clone object CharacterEqualiator for new thread.
     *
     * @return CharacterEqualiator.
     */
    CharacterEqualiator getWordReader();

    /**
     * Getter for <code>CharacterEqualiator</code> that will validate characters with number equaliation rule.
     *
     * <p>This getter must return a clone object CharacterEqualiator for new thread.
     *
     * @return CharacterEqualiator.
     */
    CharacterEqualiator getDigitReader();

    /**
     * Getter for <code>CharacterEqualiator</code> that will validate characters with symbols equaliation rule.
     *
     * <p>This getter must return a clone object CharacterEqualiator for new thread.
     *
     * @return CharacterEqualiator.
     */
    CharacterEqualiator getSpecialSymbolReader();
}
