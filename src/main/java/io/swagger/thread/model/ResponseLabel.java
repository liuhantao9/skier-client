package io.swagger.thread.model;

import java.util.Comparator;
import java.util.Objects;

public class ResponseLabel {
  private String task;
  private boolean success;
  private long start, finish, latency;
  private Data data;

  public ResponseLabel(boolean success, long start, long finish, String task, Data data) {
    this.task = task;
    this.success = success;
    this.start = start;
    this.finish = finish;
    this.data = data;
  }

  public Data getData() {
    return data;
  }

  public void setData(Data data) {
    this.data = data;
  }

  public String getTask() {
    return task;
  }

  public long getLatency() {
    return latency;
  }

  public void setLatency(long latency) {
    this.latency = latency;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public long getStart() {
    return start;
  }

  public void setStart(long start) {
    this.start = start;
  }

  public long getFinish() {
    return finish;
  }

  public void setFinish(long finish) {
    this.finish = finish;
  }

  public void computeLatency() {
    this.latency = this.finish - this.start;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseLabel that = (ResponseLabel) o;
    return success == that.success &&
        start == that.start &&
        finish == that.finish &&
        latency == this.latency &&
        Objects.equals(task, that.task) &&
        Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(task, success, start, finish, data, latency);
  }

  @Override
  public String toString() {
    return "ResponseLabel{" +
        "task='" + task + '\'' +
        ", success=" + success +
        ", start=" + start +
        ", finish=" + finish +
        ", latency=" + latency +
        '}';
  }
}
