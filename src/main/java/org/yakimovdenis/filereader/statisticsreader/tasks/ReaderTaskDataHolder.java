package org.yakimovdenis.filereader.statisticsreader.tasks;

import java.util.concurrent.Exchanger;

public class ReaderTaskDataHolder {
    private String taskName;
    private ReaderTaskExchangeData exchangedData;
    private Exchanger<ReaderTaskExchangeData> exchanger;

    public ReaderTaskDataHolder(String taskName, Exchanger<ReaderTaskExchangeData> exchanger) {
        this.taskName = taskName;
        this.exchanger = exchanger;
    }

    public String getTaskName() {
        return taskName;
    }

    public Exchanger<ReaderTaskExchangeData> getExchanger() {
        return exchanger;
    }

    public ReaderTaskExchangeData getExchangedData() {
        return exchangedData;
    }

    public void setExchangedData(ReaderTaskExchangeData exchangedData) {
        this.exchangedData = exchangedData;
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
