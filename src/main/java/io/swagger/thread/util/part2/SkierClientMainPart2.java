package io.swagger.thread.util.part2;

import io.swagger.thread.model.Counter;
import io.swagger.thread.model.Data;
import io.swagger.thread.model.Statistics;
import io.swagger.thread.thread.SkierClientRunnable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SkierClientMainPart2 {
  private static final int DEFAULT_MAX_THREAD = 32,
      DEFAULT_NUM_SKIERS = 20000,
      DEFAULT_NUM_SKI_LIFT = 40,
      DEFAULT_MIN_LIFT = 5,
      DEFAULT_SKI_DAY = 1,
      PHASE_ONE_MIN_TIME = 1,
      PHASE_ONE_MAX_TIME = 90,
      PHASE_TWO_MIN_TIME = 91,
      PHASE_TWO_MAX_TIME = 360,
      PHASE_THREE_MIN_TIME = 361,
      PHASE_THREE_MAX_TIME = 420,
      NUM_POST = 1000,
      MIN_NUM_GET = 5,
      MAX_NUM_GET = 10;

  /*
  Change this field to make it load balancer or single instance
  When changing this, please uncomment the other
   */
  private static String option = "single-instance";
//  private static String option = "load-balancer";


  private static final String DEFAULT_RESORT_NAME = "SilverMt";
  private static final String DEFAULT_SINGLE_INSTANCE_ADDRESS = "http://18.234.101.197:";
  private static final String DEFAULT_PORT = "8080";
  private static final String DEFAULT_LOAD_BALANCER = "http://skier-load-balancer-alpha-1927772865.us-east-1.elb.amazonaws.com";

  private static int numThread, numSkiers, numSkiLift, skiDay;
  private static String resortName, port;
  private static ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_MAX_THREAD);
  private static Statistics stats = new Statistics();
  private static int threadCount = 1;
  private static String address;

  public SkierClientMainPart2() {
    this.numThread = this.DEFAULT_MAX_THREAD;
    this.numSkiers = this.DEFAULT_NUM_SKIERS;
    this.numSkiLift = this.DEFAULT_NUM_SKI_LIFT;
    this.skiDay = this.DEFAULT_SKI_DAY;
    this.port = this.DEFAULT_PORT;
    this.resortName = this.DEFAULT_RESORT_NAME;
    this.address = this.DEFAULT_SINGLE_INSTANCE_ADDRESS;
  }

  public static void main(String[] args) {
    SkierClientMainPart2 scm = new SkierClientMainPart2();

    //  parse arguments
    parseArgs(args);

    System.out.println("Starting. Client Running with " + option + " mode");

    // Run first analysis
    runAnalysis();

    executor.shutdown();
  }

  private static void runAnalysis() {

    Counter counter1 = submitTask("one");
    while (counter1.get() > numThread / 4 * 0.9);

    Counter counter2 = submitTask("two");
    while (counter2.get() > numThread * 0.9);

    Counter counter3 = submitTask("three");
    while (counter1.get() > 0 || counter2.get() > 0 || counter3.get() > 0 );

    System.out.println();
    System.out.println("Task finish in " + option + " mode");
    System.out.println("Resort is: " + resortName);
    System.out.println("Number of skiers: " + numSkiers);
    System.out.println("Number of liftId: " + numSkiLift);
    System.out.println("Number of processing threads: " + numThread);
    System.out.println("Following are results:");

    stats.getResult();
  }

  private static Counter submitTask(String phase) {
    switch (phase) {
      case "one":
        return submitTaskUtil(PHASE_ONE_MIN_TIME, PHASE_ONE_MAX_TIME,
            DEFAULT_MIN_LIFT, numSkiLift, NUM_POST, MIN_NUM_GET, numThread / 4, phase);
      case "two":
        return submitTaskUtil(PHASE_TWO_MIN_TIME, PHASE_TWO_MAX_TIME,
            DEFAULT_MIN_LIFT, numSkiLift, NUM_POST, MIN_NUM_GET, numThread, phase);
      case "three":
        return submitTaskUtil(PHASE_THREE_MIN_TIME, PHASE_THREE_MAX_TIME,
            DEFAULT_MIN_LIFT, numSkiLift, NUM_POST, MAX_NUM_GET, numThread / 4, phase);
    }
    return null;
  }

  private static Counter submitTaskUtil(int minTime, int maxTime,
      int minLift, int maxLift, int numPost, int numGet, int numThread, String phase) {
    Counter task = new Counter(numThread);
    System.out.println("Start task");
    for (int i = 0; i < numThread; i++) {
      System.out.println("Start Thread: " + threadCount);
      int minSkierId = i * (numSkiers / numThread) + 1;
      int maxSkierId = (i + 1) * (numSkiers / numThread);
      Data data = new Data(numPost, numGet, minTime, maxTime,
          minLift, maxLift, minSkierId, maxSkierId,
          address, DEFAULT_RESORT_NAME, port, DEFAULT_SKI_DAY, threadCount++,
          phase, DEFAULT_LOAD_BALANCER, option);
      executor.submit(new SkierClientRunnable(data, task, stats));
    }
    return task;
  }

  private static void parseArgs(String[] args) {
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "--max-thread":
          numThread = Integer.parseInt(args[++i]);
          break;
        case "--number-skiers":
          numSkiers = Integer.parseInt(args[++i]);
          break;
        case "--number-ski-lift":
          numSkiLift = Integer.parseInt(args[++i]);
          break;
        case "--ski-day":
          skiDay = Integer.parseInt(args[++i]);
          break;
        case "--port":
          port = args[++i];
          break;
        case "--single-instance":
          option = "single-instance";
          break;
        case "--load-balancer":
          option = "load-balancer";
          break;
        case "--address":
          address = args[++i];
          break;
        case "--resort-name":
          resortName = args[++i];
          break;
      }
    }
  }
}
