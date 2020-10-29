package io.swagger.thread.model;

import java.util.Objects;

public class Data {
  private int numPost, numGet, minTime, maxTime, minLiftId, maxLiftId, minSkierId, maxSkierId, day, threadCount;
  private String address, resortID, port, phase, loadBalancerAddress, option;

  public Data(int numPost, int numGet, int minTime, int maxTime, int minLiftId, int maxLiftId,
      int minSkierId, int maxSkierId, String address, String resortID, String port, int day, int threadCount,
      String phase, String loadBalancerAddress, String option
  ) {
    this.numPost = numPost;
    this.numGet = numGet;
    this.minTime = minTime;
    this.maxTime = maxTime;
    this.minLiftId = minLiftId;
    this.maxLiftId = maxLiftId;
    this.minSkierId = minSkierId;
    this.maxSkierId = maxSkierId;
    this.option = option;
    if (option.equals("single-instance")) {
      this.address = address + port;
    } else {
      this.address = loadBalancerAddress;
    }
    this.resortID = resortID;
    this.port = port;
    this.day = day;
    this.threadCount = threadCount;
    this.phase = phase;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public String getOption() {
    return option;
  }

  public void setOption(String option) {
    this.option = option;
  }

  public String getLoadBalancerAddress() {
    return loadBalancerAddress;
  }

  public void setLoadBalancerAddress(String loadBalancerAddress) {
    this.loadBalancerAddress = loadBalancerAddress;
  }

  public int getThreadCount() {
    return threadCount;
  }

  public String getPhase() {
    return phase;
  }

  public void setPhase(String phase) {
    this.phase = phase;
  }

  public void setThreadCount(int threadCount) {
    this.threadCount = threadCount;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public int getNumPost() {
    return numPost;
  }

  public int getNumGet() {
    return numGet;
  }

  public int getMinTime() {
    return minTime;
  }

  public int getMaxTime() {
    return maxTime;
  }

  public int getMinLiftId() {
    return minLiftId;
  }

  public int getMaxLiftId() {
    return maxLiftId;
  }

  public int getMinSkierId() {
    return minSkierId;
  }

  public int getMaxSkierId() {
    return maxSkierId;
  }

  public String getAddress() {
    return address;
  }

  public String getResortID() {
    return resortID;
  }

  public void setNumPost(int numPost) {
    this.numPost = numPost;
  }

  public void setNumGet(int numGet) {
    this.numGet = numGet;
  }

  public void setMinTime(int minTime) {
    this.minTime = minTime;
  }

  public void setMaxTime(int maxTime) {
    this.maxTime = maxTime;
  }

  public void setMinLiftId(int minLiftId) {
    this.minLiftId = minLiftId;
  }

  public void setMaxLiftId(int maxLiftId) {
    this.maxLiftId = maxLiftId;
  }

  public void setMinSkierId(int minSkierId) {
    this.minSkierId = minSkierId;
  }

  public void setMaxSkierId(int maxSkierId) {
    this.maxSkierId = maxSkierId;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setResortID(String resortID) {
    this.resortID = resortID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Data data = (Data) o;
    return numPost == data.numPost &&
        numGet == data.numGet &&
        minTime == data.minTime &&
        maxTime == data.maxTime &&
        minLiftId == data.minLiftId &&
        maxLiftId == data.maxLiftId &&
        minSkierId == data.minSkierId &&
        maxSkierId == data.maxSkierId &&
        Objects.equals(address, data.address) &&
        Objects.equals(resortID, data.resortID) &&
        Objects.equals(port, data.port);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(numPost, numGet, minTime, maxTime, minLiftId, maxLiftId, minSkierId, maxSkierId,
            address,
            resortID, port);
  }

  @Override
  public String toString() {
    return "Data{" +
        "numPost=" + numPost +
        ", numGet=" + numGet +
        ", minTime=" + minTime +
        ", maxTime=" + maxTime +
        ", minLiftId=" + minLiftId +
        ", maxLiftId=" + maxLiftId +
        ", minSkierId=" + minSkierId +
        ", maxSkierId=" + maxSkierId +
        ", address='" + address + '\'' +
        ", resortID='" + resortID + '\'' +
        ", port='" + port + '\'' +
        '}';
  }
}
