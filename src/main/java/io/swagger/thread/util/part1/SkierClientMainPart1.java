//package io.swagger.thread.util.part1;
//
//import io.swagger.thread.model.Data;
//import io.swagger.thread.model.Statistics;
//import io.swagger.thread.thread.SkierClientRunnable;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class SkierClientMainPart1 {
//  private static final int DEFAULT_MAX_THREAD = 256,
//      DEFAULT_NUM_SKIERS = 40000,
//      DEFAULT_NUM_SKI_LIFT = 40,
//      DEFAULT_MIN_LIFT = 5,
//      DEFAULT_SKI_DAY = 1,
//      PHASE_ONE_MIN_DAY = 1,
//      PHASE_ONE_MAX_DAY = 90,
//      PHASE_TWO_MIN_DAY = 91,
//      PHASE_TWO_MAX_DAY = 360,
//      PHASE_THREE_MIN_DAY = 361,
//      PHASE_THREE_MAX_DAY = 420,
//      NUM_POST = 100,
//      MIN_NUM_GET = 5,
//      MAX_NUM_GET = 10;
//  private static final String DEFAULT_RESORT_NAME = "SilverMt";
//  private static final String ADDRESS = "http://localhost:";
//  private static final String DEFAULT_PORT = "8080";
//
//  private static int numThread, numSkiers, numSkiLift, skiDay;
//  private static String resortName, port;
//  private static ConcurrentHashMap<Data, Boolean> taskMap = new ConcurrentHashMap<>();;
//  private static ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_MAX_THREAD);
//  private static Statistics stats = new Statistics();
//
//  public SkierClientMainPart1() {
//    this.numThread = this.DEFAULT_MAX_THREAD;
//    this.numSkiers = this.DEFAULT_NUM_SKIERS;
//    this.numSkiLift = this.DEFAULT_NUM_SKI_LIFT;
//    this.skiDay = this.DEFAULT_SKI_DAY;
//    this.port = this.DEFAULT_PORT;
//    this.resortName = this.DEFAULT_RESORT_NAME;
//  }
//
//  public static void main(String[] args) {
//    SkierClientMainPart1 scm = new SkierClientMainPart1();
//    // parse arguments
//    parseArgs(args);
//
//    // Run first analysis
//    runAnalysis("first");
//
//    executor.shutdown();
//  }
//
//  private static void runAnalysis(String part) {
//
//    submitTask("one", part);
//    while (taskMap.size() > numThread / 4 * 0.9);
//
//    submitTask("two", part);
//    while (taskMap.size() > numThread * 0.9);
//
//    submitTask("three", part);
//    while (taskMap.size() > numThread * 0.9);
//
//      stats.getFirstResult();
//  }
//
//  private static void submitTask(String phase, String part) {
//    switch (phase) {
//      case "one":
//        submitTaskUtil(PHASE_ONE_MIN_DAY, PHASE_ONE_MAX_DAY,
//            DEFAULT_MIN_LIFT, numSkiLift, NUM_POST, MIN_NUM_GET, numThread / 4, part);
//        break;
//      case "two":
//        submitTaskUtil(PHASE_TWO_MIN_DAY, PHASE_TWO_MAX_DAY,
//            DEFAULT_MIN_LIFT, numSkiLift, NUM_POST, MIN_NUM_GET, numThread, part);
//        break;
//      case "three":
//        submitTaskUtil(PHASE_THREE_MIN_DAY, PHASE_THREE_MAX_DAY,
//            DEFAULT_MIN_LIFT, numSkiLift, NUM_POST, MAX_NUM_GET, numThread, part);
//        break;
//    }
//  }
//
//  private static void submitTaskUtil(int minDay, int maxDay,
//      int minLift, int maxLift, int numPost, int numGet, int numThread, String part) {
//    for (int i = 0; i < numThread; i++) {
//      int minSkierId = i * (numSkiers / numThread) + 1;
//      int maxSkierId = (i + 1) * (numSkiers / numThread);
//      Data data = new Data(numPost, numGet, minDay, maxDay,
//          minLift, maxLift, minSkierId, maxSkierId, ADDRESS, DEFAULT_RESORT_NAME, port);
//      taskMap.put(data, true);
//      executor.submit(new SkierClientRunnable(data, taskMap, stats, part));
//    }
//  }
//
//  private static void parseArgs(String[] args) {
//    for (int i = 0; i < args.length; i += 2) {
//      switch (args[i]) {
//        case "--max-thread":
//          numThread = Integer.parseInt(args[i + 1]);
//          break;
//        case "--number-skiers":
//          numSkiers = Integer.parseInt(args[i + 1]);
//          break;
//        case "--number-ski-lift":
//          numSkiLift = Integer.parseInt(args[i + 1]);
//          break;
//        case "--ski-day":
//          skiDay = Integer.parseInt(args[i + 1]);
//          break;
//        case "--port":
//          port = args[i + 1];
//          break;
//        case "--resort-name":
//          resortName = args[i + 1];
//          break;
//      }
//    }
//  }
//}
