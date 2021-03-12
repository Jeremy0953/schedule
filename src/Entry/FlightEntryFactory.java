package Entry;

import Location.Location;
import Time.Timeslot;

/**
 * 航班计划项工厂
 */
public class FlightEntryFactory {
    /**
     * 返回新建的航班计划项
     * @param beginLocation 出发地
     * @param endLocation 目的地
     * @param timeslot 航行时间对
     * @param name 航班号
     * @return 返回一个新建的航班
     */
    public static FlightEntry getEntry(Location beginLocation, Location endLocation, Timeslot timeslot,String name)
    {
        FlightEntry flightEntry =  FlightEntry.getInstance(beginLocation,endLocation,timeslot);
        flightEntry.creat(name);
        return flightEntry;
    }
}
