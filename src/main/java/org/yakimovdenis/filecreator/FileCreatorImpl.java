package org.yakimovdenis.filecreator;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileCreatorImpl implements FileCreator {
    private static final Logger log = Logger.getLogger(FileCreatorImpl.class.getName());
    private static final String DEFAULTFILENAME = "output.txt";
    private static final int DEFAULTLINENUM = 10;
    private static final int DEFAULTLINELENGTH = 30;
    private int fileCreatorSeed = 128;
    private Random random = new Random(System.currentTimeMillis());
    private int minimalLineLength;
    private int minimalLineNum;
    private String fileName;
    private StringBuilder data;

    FileCreatorImpl() {
        this.fileName = DEFAULTFILENAME;
        this.minimalLineLength = DEFAULTLINELENGTH;
        this.minimalLineNum = DEFAULTLINENUM;
        this.data = new StringBuilder();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMinimalLineNumber(int lineNumber) {
        this.minimalLineNum = lineNumber;
    }

    public void setMinimalLineLingth(int minimalLineLingth) {
        this.minimalLineLength = minimalLineLingth;
    }

    public void setSeed(int seed) {
        this.fileCreatorSeed = seed;
    }

    public File doGenerate() {
        for (int i = 0; i < generateInteger(minimalLineNum); i++) {
            int currentLineLength = generateInteger(minimalLineLength);
            String resultLine = RandomStringUtils.randomAscii(currentLineLength);
            data.append(resultLine);
            data.append("\n");
        }
        File result = null;
        try {
            result = writeFile();
        } catch (IOException e) {
            log.log(Level.SEVERE, "File can't be created. Reason: " + e);
        }
        return result;
    }

    //Method for generating random integer for multiple purposes like lineLength generation of lineNum generation
    private int generateInteger(int minimalValue) {
        int lineNum = random.nextInt(minimalValue + fileCreatorSeed);
        if (lineNum < minimalValue) {
            lineNum = minimalValue;
        }
        return lineNum;
    }

    private File writeFile() throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(data.toString().getBytes());
        bufferedOutputStream.flush();
        fileOutputStream.close();
        return file;
    }
}
