package io.swagger.thread.thread;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import io.swagger.client.model.SkierVertical;
import io.swagger.thread.model.Counter;
import io.swagger.thread.model.Data;
import io.swagger.thread.model.ResponseLabel;
import io.swagger.thread.model.Statistics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SkierClientRunnable implements Runnable {

  private Data data;
  private Counter task;
  private Statistics stats;
  private SkiersApi skiersApi;
  private ApiClient apiClient;
  private Random rand;
  private final Logger LOGGER = LogManager.getLogger();

  public SkierClientRunnable(Data data, Counter task, Statistics stats) {
    this.data = data;
    this.task = task;
    this.stats = stats;
    this.apiClient = new ApiClient();
    System.out.println(data.getOption());
    this.apiClient.setBasePath(data.getAddress());
    this.skiersApi = new SkiersApi(this.apiClient);
    this.rand = new Random();
  }

  @Override
  public void run() {
    for (int i = 0; i < data.getNumPost(); i++) {
      post();
    }
    System.out.println("Finish: " + data.getThreadCount() + ", POST num: " + data.getNumGet());
    for (int i = 0; i < data.getNumGet(); i++) {
      get();
      if (data.getPhase().equals("three")) {
        getExtra();
      }
    }
    task.dec();
    System.out.println("Finish: " + data.getThreadCount() + ", GET num: " + data.getNumGet());
  }

  private void get() {
    long start = System.currentTimeMillis();
    ApiResponse<SkierVertical> response = null;
    ApiResponse<SkierVertical> extraResponse = null;
    try {
      String ski = "" + data.getMinSkierId() + rand.nextInt(data.getMaxSkierId() - data.getMinSkierId() + 1);
      response = skiersApi.getSkierDayVerticalWithHttpInfo(data.getResortID(), String.valueOf(data.getDay()), ski);
      System.out.println("[Thread: " + data.getThreadCount() + "] GET -> " + data.getAddress() + ", response: " + response.getStatusCode());
    } catch (ApiException e) {
      LOGGER.error("Encountered an error: ", e);
    } finally {
      long finish = System.currentTimeMillis();
      ResponseLabel label = new ResponseLabel(null != response && response.getStatusCode() == 200, start, finish,  "GETTotalVertWithSpecificDay", data);
      label.computeLatency();
      stats.insert(label);
    }
  }

  private void getExtra() {
    long start = System.currentTimeMillis();
    ApiResponse<SkierVertical> response = null;
    try {
      String ski = "" + data.getMinSkierId() + rand.nextInt(data.getMaxSkierId() - data.getMinSkierId() + 1);
      response = skiersApi.getSkierResortTotalsWithHttpInfo(ski, new ArrayList<>(Arrays.asList(data.getResortID())));
      System.out.println("[Thread: " + data.getThreadCount() + "] GET(extra) -> " + data.getAddress() + ", response: " + response.getStatusCode());
    } catch (ApiException e) {
      LOGGER.error("Encountered an error: ", e);
    } finally {
      long finish = System.currentTimeMillis();
      ResponseLabel label = new ResponseLabel(null != response && response.getStatusCode() == 200, start, finish, "GETTotalVertWithoutSpecificDay", data);
      label.computeLatency();
      stats.insert(label);
    }
  }

  private void post() {
    long start = System.currentTimeMillis();
    ApiResponse response = null;
    try {
      String time = "" + data.getMinTime() + rand.nextInt(data.getMaxTime() - data.getMinTime() + 1);
      String ski = "" + data.getMinSkierId() + rand.nextInt(data.getMaxSkierId() - data.getMinSkierId() + 1);
      String lift = "" + data.getMinLiftId() + rand.nextInt(data.getMaxLiftId() - data.getMinLiftId() + 1);

      LiftRide ride = new LiftRide();
      ride.setDayID(String.valueOf(data.getDay()));
      ride.setSkierID(ski);
      ride.setLiftID(lift);
      ride.setResortID(data.getResortID());
      ride.setTime(time);
      System.out.println("[Thread: " + data.getThreadCount() + "] POST -> " + data.getAddress());
      response = skiersApi.writeNewLiftRideWithHttpInfo(ride);
    } catch (ApiException e) {
      LOGGER.error("Encountered an error: ", e);
    } finally {
      long finish = System.currentTimeMillis();
      ResponseLabel label = new ResponseLabel(null != response && response.getStatusCode() == 201, start, finish, "POST", data);
      label.computeLatency();
      stats.insert(label);
    }
  }
}
