package io.swagger.thread.model;

import java.util.Objects;

public class Data {
  private int numPost, numGet, minDay, maxDay, minLiftId, maxLiftId, minSkierId, maxSkierId;
  private String address, resortID, port;

  public Data(int numPost, int numGet, int minDay, int maxDay, int minLiftId, int maxLiftId,
      int minSkierId, int maxSkierId, String address, String resortID, String port) {
    this.numPost = numPost;
    this.numGet = numGet;
    this.minDay = minDay;
    this.maxDay = maxDay;
    this.minLiftId = minLiftId;
    this.maxLiftId = maxLiftId;
    this.minSkierId = minSkierId;
    this.maxSkierId = maxSkierId;
    this.address = address;
    this.resortID = resortID;
    this.port = port;
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

  public int getMinDay() {
    return minDay;
  }

  public int getMaxDay() {
    return maxDay;
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

  public void setMinDay(int minDay) {
    this.minDay = minDay;
  }

  public void setMaxDay(int maxDay) {
    this.maxDay = maxDay;
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
        minDay == data.minDay &&
        maxDay == data.maxDay &&
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
        .hash(numPost, numGet, minDay, maxDay, minLiftId, maxLiftId, minSkierId, maxSkierId,
            address,
            resortID, port);
  }

  @Override
  public String toString() {
    return "Data{" +
        "numPost=" + numPost +
        ", numGet=" + numGet +
        ", minDay=" + minDay +
        ", maxDay=" + maxDay +
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
