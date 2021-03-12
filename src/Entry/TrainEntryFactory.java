package Entry;

import Location.Location;
import Time.Timeslot;

import java.util.List;

/**
 * 构造高铁计划项工厂方法
 */
public class TrainEntryFactory {
    /**
     * 工厂方法
     * @param timeList 时刻表
     * @param locationsList 航线地点列表，要求长度是timeList长度+1
     * @param name 高铁计划项名
     * @return 新建的高铁计划项
     */
    public static TrainEntry getEntry(List<Timeslot> timeList, List<Location> locationsList,String name)
    {
        if (timeList.size()!=locationsList.size()-1)
            assert false;
        TrainEntry trainEntry = TrainEntry.getInstance(timeList,locationsList);
        trainEntry.creat(name);
        return trainEntry;
    }
}
