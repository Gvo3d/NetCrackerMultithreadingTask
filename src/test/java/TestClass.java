import org.junit.BeforeClass;
import org.junit.Test;
import org.yakimovdenis.filecreator.FileCreator;
import org.yakimovdenis.filecreator.FileCreatorFactory;
import org.yakimovdenis.filecreator.FileCreatorFactoryImpl;
import org.yakimovdenis.filereader.*;
import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReader;
import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReaderImpl;
import org.yakimovdenis.filereader.statisticsreader.FileStatisticsReaderSupport;
import org.yakimovdenis.filereader.statisticsreader.charreaders.AlphabetCharReaderImpl;
import org.yakimovdenis.filereader.statisticsreader.charreaders.NumericCharReaderImpl;
import org.yakimovdenis.filereader.statisticsreader.charreaders.SpecialSymbolCharReaderImpl;
import org.yakimovdenis.filereader.statisticsreader.support.NoFileStatisticReaderSupportException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class TestClass {
    private static File targetFile;
    private static final String FILENAME = "target.txt";
    private static final Character WORD = 'k';
    private static final Character DIGIT = '7';
    private static final Character SYMBOL = '$';
    private static final Character NEWLINE = '\n';

    @BeforeClass
    public static void createTargetFile() {
        targetFile = new File(FILENAME);
        FileOutputStream fos = null;
        try {
             fos = new FileOutputStream(targetFile);
        } catch (FileNotFoundException e) {
            System.err.println("Can't create OutputStream for file "+FILENAME);
        }
        StringBuilder lineData = new StringBuilder();
        lineData.append(WORD);
        lineData.append(DIGIT);
        lineData.append(SYMBOL);
        lineData.append(NEWLINE);
        try {
            for (int i=0; i<3; i++){
                fos.write(lineData.toString().getBytes());
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            System.err.println("Can't write to file "+FILENAME);
        }
    }

    @Test
    public void testFileCreatorAndFileCreatorFactory() {
        String fileName = "forTestingPurposes.txt";
        FileCreator creator = new FileCreatorFactoryImpl().createInstance();
        assertNotNull(creator);
        creator.setFileName(fileName);
        creator.doGenerate();
        File file = new File(fileName);
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testAlphabeticCharReader() {
        AlphabetCharReaderImpl wordReader = new AlphabetCharReaderImpl();
        assertEquals(true, wordReader.matchesCondition(WORD));
        assertEquals(false, wordReader.matchesCondition(DIGIT));
        assertEquals(false, wordReader.matchesCondition(SYMBOL));
        assertNotNull(wordReader.getUsedMethod());
        AlphabetCharReaderImpl clone = (AlphabetCharReaderImpl) wordReader.clone();
        assertEquals(clone.getClass(), AlphabetCharReaderImpl.class);
    }

    @Test
    public void testNumericCharReader() {
        NumericCharReaderImpl numericCharReader = new NumericCharReaderImpl();
        assertEquals(false, numericCharReader.matchesCondition(WORD));
        assertEquals(true, numericCharReader.matchesCondition(DIGIT));
        assertEquals(false, numericCharReader.matchesCondition(SYMBOL));
        assertNotNull(numericCharReader.getUsedMethod());
        NumericCharReaderImpl clone = (NumericCharReaderImpl) numericCharReader.clone();
        assertEquals(clone.getClass(), NumericCharReaderImpl.class);
    }

    @Test
    public void testSpecialSymbolCharReader() {
        SpecialSymbolCharReaderImpl symbolCharReader = new SpecialSymbolCharReaderImpl();
        assertEquals(false, symbolCharReader.matchesCondition(WORD));
        assertEquals(false, symbolCharReader.matchesCondition(DIGIT));
        assertEquals(true, symbolCharReader.matchesCondition(SYMBOL));
        assertNotNull(symbolCharReader.getUsedMethod());
        SpecialSymbolCharReaderImpl clone = (SpecialSymbolCharReaderImpl) symbolCharReader.clone();
        assertEquals(clone, symbolCharReader);
    }

    @Test
    public void testFileStatisticsReader(){
        FileStatisticsFactory factory = new FileStatisticsFactoryImpl();
        FileStatisticsReader reader = factory.createInstance();
        FileStatisticsReaderSupport support = new FileStatisticsReaderSupportFactoryImpl().createInstance();
        CharacterEqualiatorProxy wordProxy = new CharacterEqualiatorProxy(support.getWordReader());
        CharacterEqualiatorProxy digitProxy = new CharacterEqualiatorProxy(support.getDigitReader());
        CharacterEqualiatorProxy symbolProxy = new CharacterEqualiatorProxy(support.getSpecialSymbolReader());
        support.setWordReader(wordProxy);
        support.setDigitReader(digitProxy);
        support.setSpecialSymbolReader(symbolProxy);
        reader.setFileStatisticsReaderSupport(support);
        reader.setTargetFile(targetFile);
        reader.calculate();
//Target file is target.txt haas 3 lines of 3 symbols, so each CharacterEqualiator must have 3 valid results.
        assertEquals(3, wordProxy.getCountedChars());
        assertEquals(3, digitProxy.getCountedChars());
        assertEquals(3, symbolProxy.getCountedChars());
    }

    @Test
    public void testFileStatisticsReaderFactory(){
        FileStatisticsReader reader = createFileStatisticsReader();
        assertNotNull(reader);
    }

    @Test(expected = NoFileStatisticReaderSupportException.class)
    public void testFileStatisticsReaderException(){
        String tempFileName = "tempfile.txt";
        FileStatisticsReader reader = new FileStatisticsReaderImpl();
        File targetFile = createFileWithRandomContent(generateFileCreationFactory().createInstance(), tempFileName);
        reader.setTargetFile(targetFile);
        reader.calculate();
    }

    private static FileCreatorFactory generateFileCreationFactory() {
        return new FileCreatorFactoryImpl();
    }

    private static File createFileWithRandomContent(FileCreator fileCreator, String newFileName) {
        fileCreator.setFileName(newFileName);
        return fileCreator.doGenerate();
    }

    private static FileStatisticsReader createFileStatisticsReader() {
        FileStatisticsFactory factory = new FileStatisticsFactoryImpl();
        FileStatisticsReaderSupportFactory supportFactory = new FileStatisticsReaderSupportFactoryImpl();
        FileStatisticsBuilder builder = new FileStatisticsBuilderImpl(factory, supportFactory);
        return builder.buildInstance();
    }
}
