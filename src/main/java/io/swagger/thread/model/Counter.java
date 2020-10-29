package io.swagger.thread.model;

public class Counter {
  volatile int count = 0;

  public Counter(int count) {
    this.count = count;
  }

  public synchronized boolean dec() {
    if (count <= 0) return false;
    count--;
    return true;
  }

  public int get() {
    return count;
  }
}
