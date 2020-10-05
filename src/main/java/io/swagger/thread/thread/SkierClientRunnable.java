package io.swagger.thread.thread;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import io.swagger.client.model.SkierVertical;
import io.swagger.thread.model.Data;
import io.swagger.thread.model.ResponseLabel;
import io.swagger.thread.model.Statistics;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SkierClientRunnable implements Runnable {

  private Data data;
  private ConcurrentHashMap<Data, Boolean> taskMap;
  private Statistics stats;
  private SkiersApi skiersApi;
  private ApiClient apiClient;
  private Random rand;
  private String part;
  private final Logger LOGGER = LogManager.getLogger();

  public SkierClientRunnable(Data data, ConcurrentHashMap<Data, Boolean> taskMap, Statistics stats, String part) {
    this.data = data;
    this.taskMap = taskMap;
    this.stats = stats;
    this.apiClient = new ApiClient();
    this.apiClient.setBasePath(data.getAddress() + data.getPort() + "/BSDS_Assignment_war_exploded/");
    this.skiersApi = new SkiersApi(this.apiClient);
    this.rand = new Random();
    this.part = part;
  }

  @Override
  public void run() {
    for (int i = 0; i < data.getNumGet(); i++) {
      get();
    }
    for (int i = 0; i < data.getNumPost(); i++) {
      post();
    }
    taskMap.remove(data);
  }

  private void get() {
    long start = System.currentTimeMillis();
    ApiResponse<SkierVertical> response = null;
    try {
      String day = "" + data.getMinDay() + rand.nextInt(data.getMaxDay() - data.getMinDay() + 1);
      String ski = "" + data.getMinSkierId() + rand.nextInt(data.getMaxSkierId() - data.getMinSkierId() + 1);
      response = skiersApi.getSkierDayVerticalWithHttpInfo(data.getResortID(), day, ski);
    } catch (ApiException e) {
      LOGGER.error("Encountered an error: ", e);
    } finally {
      long finish = System.currentTimeMillis();
      ResponseLabel label = new ResponseLabel(null != response && response.getStatusCode() == 200, start, finish,  "GET", data);
      if (part.equals("second")) {
        label.computeLatency();
      }
      stats.insert(label);
    }
  }

  private void post() {
    long start = System.currentTimeMillis();
    ApiResponse response = null;
    try {
      String day = "" + data.getMinDay() + rand.nextInt(data.getMaxDay() - data.getMinDay() + 1);
      String ski = "" + data.getMinSkierId() + rand.nextInt(data.getMaxSkierId() - data.getMinSkierId() + 1);
      String lift = "" + data.getMinLiftId() + rand.nextInt(data.getMaxLiftId() - data.getMinLiftId() + 1);

      LiftRide ride = new LiftRide();
      ride.setDayID(day);
      ride.setSkierID(ski);
      ride.setLiftID(lift);
      response = skiersApi.writeNewLiftRideWithHttpInfo(ride);
    } catch (ApiException e) {
      LOGGER.error("Encountered an error: ", e);
    } finally {
      long finish = System.currentTimeMillis();
      ResponseLabel label = new ResponseLabel(null != response && response.getStatusCode() == 201, start, finish, "POST", data);
      if (part.equals("second")) {
        label.computeLatency();
      }
      stats.insert(label);
    }
  }
}
