package io.swagger.thread.Comparator;

import io.swagger.thread.model.ResponseLabel;
import java.util.Comparator;

public class ResonseLabelComparator  implements Comparator<ResponseLabel> {
  @Override
  public int compare(ResponseLabel o1, ResponseLabel o2) {
    if (o1.getStart() == o2.getStart()) {
      return (int)o1.getFinish() - (int)o2.getFinish();
    } else {
      return (int)o1.getStart() - (int)o2.getStart();
    }
  }
}
