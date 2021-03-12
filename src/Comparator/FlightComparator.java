package Comparator;

import Entry.FlightEntry;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 航班比较器，用于按开始起飞时间进行从小到大排序
 */
public class FlightComparator implements Comparator<FlightEntry>, Serializable {

    @Override
    public int compare(FlightEntry o1, FlightEntry o2) {
        if (o1.getTimeList().get(0).getBeginTime().isBefore(o2.getTimeList().get(0).getBeginTime()))
            return -1;
        if (o1.getTimeList().get(0).getBeginTime().isAfter(o2.getTimeList().get(0).getBeginTime()))
            return 1;
        return 0;
    }
}
