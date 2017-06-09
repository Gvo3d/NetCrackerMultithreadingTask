package org.yakimovdenis.filecreator;

import java.io.File;

public interface FileCreator {
    public void setFileName(String fileName);

    public void setMinimalLineNumber(int lineNumber);

    public void setMinimalLineLingth(int minimalLineLingth);

    public void setSeed(int seed);

    public File doGenerate();
}
