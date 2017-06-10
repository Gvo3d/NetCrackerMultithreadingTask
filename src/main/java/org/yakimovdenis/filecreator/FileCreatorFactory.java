package org.yakimovdenis.filecreator;

/**
 * @interface FileCreatorFactory interface for creating FileCreators implementations
 *
 * <p> Has only one method for returning <code>FileCreator</code>.
 *
 * @autor Yakimov Denis
 */
public interface FileCreatorFactory {
    /**
     * Method for creating instance of <code>FileCreator</code>.
     *
     * @return FileCreator.
     */
    FileCreator createInstance();
}
