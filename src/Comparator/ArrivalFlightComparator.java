package Comparator;

import Entry.FlightEntry;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 航班比较器，用于按抵达时间进行排序
 */
public class ArrivalFlightComparator implements Comparator<FlightEntry> , Serializable {
    @Override
    public int compare(FlightEntry o1, FlightEntry o2) {
        if (o1.getTimeList().get(o1.getTimeList().size()-1).getEndTime().isBefore(o2.getTimeList().get(o2.getTimeList().size()-1).getEndTime()))
            return -1;
        if (o1.getTimeList().get(o1.getTimeList().size()-1).getEndTime().isAfter(o2.getTimeList().get(o2.getTimeList().size()-1).getEndTime()))
            return 1;
        return 0;
    }
}
