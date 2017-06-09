package org.yakimovdenis.filereader.statisticsreader.support;

/**
 * Created by Gvozd on 10.06.2017.
 */
public class NoFileStatisticReaderSupportException extends RuntimeException {
    private static final String DEFAULTMESSAGE = "No FileStatisticReaderSupport for reader ";

    public NoFileStatisticReaderSupportException(String message) {
        super(DEFAULTMESSAGE+message);
    }
}
