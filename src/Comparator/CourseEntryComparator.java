package Comparator;

import Entry.CourseEntry;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 课程比较器，根据课程开始时间进行比较
 */
public class CourseEntryComparator  implements Comparator<CourseEntry>, Serializable {
    @Override
    public int compare(CourseEntry o1, CourseEntry o2) {
        if (o1.getTimeList().get(0).getBeginTime().isBefore(o2.getTimeList().get(0).getBeginTime()))
            return -1;
        if (o1.getTimeList().get(0).getBeginTime().isAfter(o2.getTimeList().get(0).getBeginTime()))
            return 1;
        return 0;
    }
}
