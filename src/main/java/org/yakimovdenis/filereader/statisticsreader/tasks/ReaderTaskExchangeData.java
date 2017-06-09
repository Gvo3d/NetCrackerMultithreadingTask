package org.yakimovdenis.filereader.statisticsreader.tasks;

public class ReaderTaskExchangeData {
    private Long startingTime;
    private Long endingTime;
    private Long resultChars;
    private String method;

    public ReaderTaskExchangeData(Long startingTime, Long endingTime, Long resultChars, String method) {
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.resultChars = resultChars;
        this.method = method;
    }

    public ReaderTaskExchangeData(Long startingTime, String method) {
        this.startingTime = startingTime;
        this.method = method;
    }

    public void setEndingTime(Long endingTime) {
        this.endingTime = endingTime;
    }

    public void setResultChars(Long resultChars) {
        this.resultChars = resultChars;
    }

    public Long getStartingTime() {
        return startingTime;
    }

    public Long getEndingTime() {
        return endingTime;
    }

    public Long getResultChars() {
        return resultChars;
    }

    public String getMethod() {
        return method;
    }
}
