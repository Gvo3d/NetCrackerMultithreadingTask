package org.yakimovdenis.filereader.statisticsreader.tasks;

import java.util.concurrent.Exchanger;

public class ReaderTaskDataHolder {
    private String taskName;
    private String method;
    private long startingTime;
    private long endingTime;
    private long resultChars;
    private Exchanger<Long> exchanger;

    public ReaderTaskDataHolder(String taskName, String method, Exchanger<Long> exchanger) {
        this.taskName = taskName;
        this.method = method;
        this.exchanger = exchanger;
    }

    public void setStartingTime(long startingTime) {
        this.startingTime = startingTime;
    }

    public void setEndingTime(long endingTime) {
        this.endingTime = endingTime;
    }

    public void setResultChars(long resultChars) {
        this.resultChars = resultChars;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getMethod() {
        return method;
    }

    public Exchanger<Long> getExchanger() {
        return exchanger;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public long getEndingTime() {
        return endingTime;
    }

    public long getResultChars() {
        return resultChars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReaderTaskDataHolder that = (ReaderTaskDataHolder) o;

        if (!getTaskName().equals(that.getTaskName())) return false;
        return getExchanger().equals(that.getExchanger());
    }

    @Override
    public int hashCode() {
        int result = getTaskName().hashCode();
        result = 31 * result + getExchanger().hashCode();
        return result;
    }
}
