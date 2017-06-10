package org.yakimovdenis.filecreator;

public class FileCreatorFactoryImpl implements FileCreatorFactory {
    public FileCreator createInstance() {
        return new FileCreatorImpl();
    }
}
