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
  private List<ResponseLabel> getListWithDay = new ArrayList<>();
  private List<ResponseLabel> getListWithoutDay = new ArrayList<>();

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
        case "GETTotalVertWithSpecificDay":
          getListWithDay.add(rl);
          break;
        case "GETTotalVertWithoutSpecificDay":
          getListWithoutDay.add(rl);
          break;
      }
    }
//    Collections.sort(postList, new ResonseLabelComparator());
//    Collections.sort(getList, new ResonseLabelComparator());
  }

  public void getResult() {
    this.postList = new ArrayList<>();
    this.getListWithDay = new ArrayList<>();
    this.getListWithoutDay = new ArrayList<>();
    assign();
    resultUtil();
  }

//  public void getSecondResult() {
//    this.postList = new ArrayList<>();
//    this.getList = new ArrayList<>();
//    assign();
//    System.out.println("Second Results: ");
//    secondResultUtil();
//  }

//  private void firstResultUtil() {
//    long success = 0, fail = 0;
//    long startTime = Long.MAX_VALUE, finishTime = Long.MIN_VALUE, numRequest = 0;
//    float throughput = 0;
//
//    for (ResponseLabel rlp : postList) {
//      ++numRequest;
//      if (rlp.isSuccess()) ++success;
//      else ++fail;
//      startTime = Math.min(startTime, rlp.getStart());
//      finishTime = Math.max(finishTime, rlp.getFinish());
//    }
//
//    for (ResponseLabel rlg : getList) {
//      ++numRequest;
//      if (rlg.isSuccess()) ++success;
//      else ++fail;
//      startTime = Math.min(startTime, rlg.getStart());
//      finishTime = Math.max(finishTime, rlg.getFinish());
//    }
//
//    System.out.println("Number successful request sent: " + success);
//    System.out.println("Number failed request sent: " + fail);
//    System.out.println("Wall time: " + (finishTime - startTime));
//    System.out.println("Throughput: " + success * 1000 / (finishTime - startTime));
//  }

  private void resultUtil() {
    double meanResTimePost = 0, meanResTimeGetWithDay = 0, meanResTimeGetWithoutDay = 0;
    long  medResTimePost = 0, maxResTimePost = 0, pert99ResTimePost = 0;
    long medResTimeGetWithDay = 0, maxResTimeGetWithDay = 0, pert99ResTimeGetWithDay = 0;
    long medResTimeGetWithoutDay = 0, maxResTimeGetWithoutDay = 0, pert99ResTimeGetWithoutDay = 0;
    long startTime = Long.MAX_VALUE, finishTime = Long.MIN_VALUE,
        sumResTimeGetWithDay = 0, sumResTimeGetWithoutDay = 0, sumResTimePos = 0, failedReq = 0;

    Collections.sort(postList, new ResonseLabelLatencyComparator());
    Collections.sort(getListWithDay, new ResonseLabelLatencyComparator());
    Collections.sort(getListWithoutDay, new ResonseLabelLatencyComparator());

    medResTimePost = postList.get(postList.size() / 2).getLatency();
    maxResTimePost = postList.get(postList.size() - 1).getLatency();
    pert99ResTimePost = postList.get((int)Math.floor((postList.size() * 0.99))).getLatency();

    medResTimeGetWithDay = getListWithDay.get(getListWithDay.size() / 2).getLatency();
    maxResTimeGetWithDay = getListWithDay.get(getListWithDay.size() - 1).getLatency();
    pert99ResTimeGetWithDay = getListWithDay.get((int)Math.floor((getListWithDay.size() * 0.99))).getLatency();

    medResTimeGetWithoutDay = getListWithoutDay.get(getListWithoutDay.size() / 2).getLatency();
    maxResTimeGetWithoutDay = getListWithoutDay.get(getListWithoutDay.size() - 1).getLatency();
    pert99ResTimeGetWithoutDay = getListWithoutDay.get((int)Math.floor((getListWithoutDay.size() * 0.99))).getLatency();

//    printLast1Per(getList);
//    printLast1Per(postList);

    for (ResponseLabel rlp : postList) {
      startTime = Math.min(startTime, rlp.getStart());
      finishTime = Math.max(finishTime, rlp.getFinish());
      sumResTimePos += rlp.getLatency();
      failedReq += rlp.isSuccess() ? 0 : 1;
    }
    for (ResponseLabel rlgwd : getListWithDay) {
      startTime = Math.min(startTime, rlgwd.getStart());
      finishTime = Math.max(finishTime, rlgwd.getFinish());
      sumResTimeGetWithDay += rlgwd.getLatency();
      failedReq += rlgwd.isSuccess() ? 0 : 1;
    }
    for (ResponseLabel rlgwod : getListWithoutDay) {
      startTime = Math.min(startTime, rlgwod.getStart());
      finishTime = Math.max(finishTime, rlgwod.getFinish());
      sumResTimeGetWithoutDay += rlgwod.getLatency();
      failedReq += rlgwod.isSuccess() ? 0 : 1;
    }

    System.out.println("Total Request Sent: " + (postList.size() + getListWithDay.size() + getListWithoutDay.size()));
    System.out.println("Total Request Sent For Get Total Vert With Specific Day: " + getListWithDay.size());
    System.out.println("Total Request Sent For Get Total Vert Without Specific Day: " + getListWithoutDay.size());
    System.out.println("Total Successful Requests: " + (postList.size() + getListWithDay.size() + getListWithoutDay.size() - failedReq));
    System.out.println("Total Failed Requests: " + failedReq);
    System.out.println("Mean Response Time For Get Total Vert With Specific Day: " + (double)sumResTimeGetWithDay / getListWithDay.size() + " milliseconds");
    System.out.println("Mean Response Time For Get Total Vert Without Specific Day: " + (double)sumResTimeGetWithoutDay / getListWithoutDay.size() + " milliseconds");
    System.out.println("Mean Response Time For Post: " + (double)sumResTimePos / postList.size() + " milliseconds");
    System.out.println("Median Response Time For Get Total Vert With Specific Day: " + medResTimeGetWithDay + " milliseconds");
    System.out.println("Median Response Time For Get Total Vert Without Specific Day: " + medResTimeGetWithoutDay + " milliseconds");
    System.out.println("Median Response Time For Post: " + medResTimePost + " milliseconds");
    System.out.println("99 Percentile Time For Get Total Vert With Specific Day: " + pert99ResTimeGetWithDay + " milliseconds");
    System.out.println("99 Percentile Time For Get Total Vert Without Specific Day: " + pert99ResTimeGetWithoutDay + " milliseconds");
    System.out.println("99 Percentile Time For Post: " + pert99ResTimePost + " milliseconds");
    System.out.println("Max Response Time For Get Total Vert With Specific Day: " + maxResTimeGetWithDay + " milliseconds");
    System.out.println("Max Response Time For Get Total Vert Without Specific Day: " + maxResTimeGetWithoutDay + " milliseconds");
    System.out.println("Max Response Time For Post: " + maxResTimePost + " milliseconds");
    System.out.println("Total Wall Time: " + (finishTime - startTime) + " milliseconds");
    System.out.println("Throughput: " + (double)(getListWithDay.size() + getListWithoutDay.size() + postList.size()) * 1000.0 / (finishTime - startTime) + " number of req / second");
  }

  private void printLast1Per(List<ResponseLabel> list) {
    System.out.print("(");
    for (int i = list.size() - 1; i >= list.size() * 0.99; i--) {
      System.out.print(list.get(i) + ", ");
    }
    System.out.print(")");
    System.out.println();
  }

}




















