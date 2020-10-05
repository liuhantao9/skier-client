package io.swagger.thread.Comparator;

import io.swagger.thread.model.ResponseLabel;
import java.util.Comparator;

public class ResonseLabelLatencyComparator implements Comparator<ResponseLabel> {

  @Override
  public int compare(ResponseLabel o1, ResponseLabel o2) {
    return (int)o1.getLatency() - (int)o2.getLatency();
  }
}
