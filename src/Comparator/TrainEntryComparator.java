package Comparator;

import Entry.TrainEntry;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 高铁比较器，根据开始出发时间进行比较
 */
public class TrainEntryComparator implements Comparator<TrainEntry>, Serializable {

    @Override
    public int compare(TrainEntry o1, TrainEntry o2) {
        if (o1.getTimeList().get(0).getBeginTime().isBefore(o2.getTimeList().get(0).getBeginTime()))
            return -1;
        if (o1.getTimeList().get(0).getBeginTime().isAfter(o2.getTimeList().get(0).getBeginTime()))
            return 1;
        return 0;
    }
}
