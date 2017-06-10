package org.yakimovdenis.filereader.statisticsreader.support;

public class NoFileStatisticReaderSupportException extends RuntimeException {
    private static final String DEFAULTMESSAGE = "No FileStatisticReaderSupport for reader ";

    public NoFileStatisticReaderSupportException(String message) {
        super(DEFAULTMESSAGE + message);
    }
}
