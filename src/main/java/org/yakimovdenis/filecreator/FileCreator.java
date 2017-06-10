package org.yakimovdenis.filecreator;

import java.io.File;

public interface FileCreator {
    void setFileName(String fileName);

    void setMinimalLineNumber(int lineNumber);

    void setMinimalLineLingth(int minimalLineLingth);

    void setSeed(int seed);

    File doGenerate();
}
