package Entry;

import Location.Location;
import Time.Timeslot;

/**
 * 课程计划项工厂类，用于创建课程计划项
 */
public class CourseEntryFactory {
    /**
     * 获得新建的课程计划项
     * @param location 课程地点
     * @param timeslot 课程持续时间的时间对
     * @param name 课程名字
     * @return 返回新建的课程
     */
    public static CourseEntry getEntry(Location location, Timeslot timeslot,String name)
    {
        CourseEntry courseEntry = CourseEntry.getInstance(location,timeslot);
        courseEntry.creat(name);
        return courseEntry;
    }
}
