package org.yakimovdenis.filecreator;

import java.io.File;

/**
 * @interface <code>FileCreator</code> interface for generation random files
 *
 * <p> Random content file creation interface. All implementations must implement five methods.
 *
 * @autor Yakimov Denis
 */
public interface FileCreator {
    /**
     * Setter for filename string.
     *
     * <p>The file with that name sill be created, otherwise will be used default string.
     *
     * @param fileName a String for naming file that will be created.
     * @return void.
     */
    void setFileName(String fileName);

    /**
     * Setter for minimal line number.
     *
     * <p><code>FileCreator</code> can create file with random count of lines. This parameter is for minimal line number creation.
     *
     * @param lineNumber integer for minimal line number.
     * @return void.
     */
    void setMinimalLineNumber(int lineNumber);

    /**
     * Setter for minimal line length
     *
     * <p><code>FileCreator</code> can create line in a file with minimal count of characters. This integer for that purpose.
     *
     * @param minimalLineLingth integer for a minimal line length.
     * @return void.
     */
    void setMinimalLineLingth(int minimalLineLingth);

    /**
     * Setter for seed integer.
     *
     * <p>Sees - is an integer that is a maximal for lines count and characters in one line count.
     * This value will be added to minimal length.
     *
     * @param seed a String for naming file that will be created.
     * @return void.
     */
    void setSeed(int seed);

    /**
     * Method for launching generating results algorithm.
     *
     * @return File.
     */
    File doGenerate();
}
