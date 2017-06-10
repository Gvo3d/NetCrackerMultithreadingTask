package org.yakimovdenis.filereader.statisticsreader.tasks;

public class ReaderTaskExchangeData {
    private Long startingTime;
    private Long endingTime;
    private Long resultChars;
    private String method;
    private String outputFile;
    private String threadId;

    public ReaderTaskExchangeData(Long startingTime, String method) {
        this.startingTime = startingTime;
        this.method = method;
    }

    void setEndingTime(Long endingTime) {
        this.endingTime = endingTime;
    }

    void setResultChars(Long resultChars) {
        this.resultChars = resultChars;
    }

    Long getStartingTime() {
        return startingTime;
    }

    Long getEndingTime() {
        return endingTime;
    }

    Long getResultChars() {
        return resultChars;
    }

    String getMethod() {
        return method;
    }

    String getOutputFile() {
        return outputFile;
    }

    void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    String getThreadId() {
        return threadId;
    }

    void setThreadId(String threadId) {
        this.threadId = threadId;
    }
}
