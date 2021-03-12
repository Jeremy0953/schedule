package Entry;

import Location.Location;
import Location.MultipleLocationEntryImpl;
import Resource.Resource;
import Time.MultipleTimeLotImple;
import Time.Timeslot;
import Resource.Carriage;
import Resource.MultipleSortedResourceEntryImpl;
import java.util.List;

/**
 * 高铁计划项
 * AF：将该类映射 到times构成列车时刻表，locations的列表为所到站台列表，然后resources为分配的有序车厢的高铁计划项
 * RI：locations长度为times长度加1
 * rep from exposure:用private修饰
 */
public class TrainEntry extends CommomPlanningEntry implements TrainPlanningEntry {
    private MultipleTimeLotImple times;
    private MultipleLocationEntryImpl locations;
    private MultipleSortedResourceEntryImpl<Carriage> resources;

    /**
     * 构造方法
     * @param times 时间表
     * @param locations 地点表，要求长度为时间表加1
     */
    private TrainEntry(MultipleTimeLotImple times, MultipleLocationEntryImpl locations) {
        this.times = times;
        this.locations = locations;
        checkRep();
    }

    /**
     * 工厂方法
     * @param timeList 时间列表
     * @param locationsList 地点列表，要求地点列表长度是时间列表长度加1
     * @return
     */
    public static TrainEntry getInstance(List<Timeslot> timeList,List<Location> locationsList)
    {
        return new TrainEntry(new MultipleTimeLotImple(timeList),new MultipleLocationEntryImpl(locationsList));
    }

    /**
     * 阻塞
     */
    @Override
    public void block() {
        state.block(this);
    }

    /**
     * 设置资源委派
     * @param resource 资源类
     */
    @Override
    public void setResourceDelegation(Resource resource) {
        if (resource==null)
        {
            resources = null;
            return;
        }
        if (resource instanceof  MultipleSortedResourceEntryImpl)
            resources = (MultipleSortedResourceEntryImpl<Carriage>)resource;
        else
            assert false;
    }

    /**
     * 获得地点列表
     * @return 地点列表
     */
    @Override
    public List<? extends Location> getLocation() {
        return locations.getLocation();
    }


    /*@Override
    public void setResource(List list) {
        resources.setResource(list);
    }*/

    /**
     * 获得资源列表
     * @return 资源列表
     */
    @Override
    public List getResource() {
        return resources.getResource();
    }

    /**
     * 返回 时间列表
     * @return 返回时间列表
     */
    @Override
    public List<Timeslot> getTimeList() {
        return times.getTimeList();
    }
    /**
     * make sure RI = true
     */
    private void checkRep()
    {
        if (times.getTimeList().size()!=locations.getLocation().size()-1)
            assert false;
    }
}
