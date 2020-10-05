package io.swagger.thread.model;

import io.swagger.client.ApiResponse;
import io.swagger.thread.Comparator.ResonseLabelComparator;
import io.swagger.thread.Comparator.ResonseLabelLatencyComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Statistics {
  private Queue<ResponseLabel> resq = new LinkedBlockingQueue<>();
  private List<ResponseLabel> postList = new ArrayList<>();
  private List<ResponseLabel> getList = new ArrayList<>();

  public Statistics() {}

  public Queue<ResponseLabel> getResQ() {
    return resq;
  }

  public void setResQ(Queue<ResponseLabel> resq) {
    this.resq = resq;
  }

  public List<ResponseLabel> getPostList() {
    return postList;
  }

  public void setPostList(List<ResponseLabel> postList) {
    this.postList = postList;
  }

  public List<ResponseLabel> getGetList() {
    return getList;
  }

  public void setGetList(List<ResponseLabel> getList) {
    this.getList = getList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Statistics that = (Statistics) o;
    return Objects.equals(resq, that.resq) &&
        Objects.equals(postList, that.postList) &&
        Objects.equals(getList, that.getList);
  }

  @Override
  public String toString() {
    return "Statistics{" +
        "resq=" + resq +
        ", postList=" + postList +
        ", getList=" + getList +
        '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(resq, postList, getList);
  }

  public void insert(ResponseLabel label) {
    resq.offer(label);
  }

  public void assign() {
    while (!resq.isEmpty()) {
      ResponseLabel rl = resq.poll();
      switch (rl.getTask()) {
        case "POST":
          postList.add(rl);
          break;
        case "GET":
          getList.add(rl);
          break;
      }
    }
    Collections.sort(postList, new ResonseLabelComparator());
    Collections.sort(getList, new ResonseLabelComparator());
  }

  public void getFirstResult() {
    this.postList = new ArrayList<>();
    this.getList = new ArrayList<>();
    assign();
    System.out.println("First Results: ");
    firstResultUtil();
  }

  public void getSecondResult() {
    this.postList = new ArrayList<>();
    this.getList = new ArrayList<>();
    assign();
    System.out.println("Second Results: ");
    secondResultUtil();
  }

  private void firstResultUtil() {
    long success = 0, fail = 0;
    long startTime = Long.MAX_VALUE, finishTime = Long.MIN_VALUE, numRequest = 0;
    float throughput = 0;

    for (ResponseLabel rlp : postList) {
      ++numRequest;
      if (rlp.isSuccess()) ++success;
      else ++fail;
      startTime = Math.min(startTime, rlp.getStart());
      finishTime = Math.max(finishTime, rlp.getFinish());
    }

    for (ResponseLabel rlg : getList) {
      ++numRequest;
      if (rlg.isSuccess()) ++success;
      else ++fail;
      startTime = Math.min(startTime, rlg.getStart());
      finishTime = Math.max(finishTime, rlg.getFinish());
    }

    System.out.println("Number successful request sent: " + success);
    System.out.println("Number failed request sent: " + fail);
    System.out.println("Wall time: " + (finishTime - startTime));
    System.out.println("Throughput: " + success * 1000 / (finishTime - startTime));
  }

  private void secondResultUtil() {
    double meanResTimePost = 0, meanResTimeGet = 0;
    long  medResTimePost = 0, maxResTimePost = 0, pert99ResTimePost = 0;
    long medResTimeGet = 0, maxResTimeGet = 0, pert99ResTimeGet = 0;
    long wallTime = 0, numRequest = 0, startTime = Long.MAX_VALUE, finishTime = Long.MIN_VALUE;

    Collections.sort(postList, new ResonseLabelLatencyComparator());
    Collections.sort(getList, new ResonseLabelLatencyComparator());

    medResTimePost = postList.get(postList.size() / 2).getLatency();
    maxResTimePost = postList.get(postList.size() - 1).getLatency();
    pert99ResTimePost = postList.get((int)Math.floor((postList.size() * 0.99))).getLatency();

    medResTimeGet = postList.get(getList.size() / 2).getLatency();
    maxResTimeGet = postList.get(getList.size() - 1).getLatency();
    pert99ResTimeGet = postList.get((int)Math.floor((getList.size() * 0.99))).getLatency();

    meanResTimeGet = getList
        .stream()
        .mapToDouble(ResponseLabel::getLatency)
        .average()
        .orElse(Double.NaN);
    meanResTimePost = postList
        .stream()
        .mapToDouble(ResponseLabel::getLatency)
        .average()
        .orElse(Double.NaN);

    for (ResponseLabel rlp : postList) {
      numRequest += rlp.isSuccess() ? 1 : 0;
      startTime = Math.min(startTime, rlp.getStart());
      finishTime = Math.max(finishTime, rlp.getFinish());
    }
    for (ResponseLabel rlg : getList) {
      numRequest += rlg.isSuccess() ? 1 : 0;
      startTime = Math.min(startTime, rlg.getStart());
      finishTime = Math.max(finishTime, rlg.getFinish());
    }


    System.out.println("Mean Response Time For Get: " + meanResTimeGet);
    System.out.println("Mean Response Time For Post: " + meanResTimePost);
    System.out.println("Median Response Time For Get: " + medResTimeGet);
    System.out.println("Median Response Time For Post: " + medResTimePost);
    System.out.println("Max Response Time For Get: " + maxResTimeGet);
    System.out.println("Max Response Time For Post: " + maxResTimePost);
    System.out.println("99 Percentile Time For Get: " + pert99ResTimeGet);
    System.out.println("99 Percentile Time For Post: " + pert99ResTimePost);
    System.out.println("Total Wall Time: " + (finishTime - startTime));
    System.out.println("Throughput: " + numRequest * 1000 / (finishTime - startTime));
  }

}




















