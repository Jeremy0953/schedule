package Entry;

import Location.Location;
import Resource.Resource;
import Time.MultipleTimeLotImple;
import Time.Timeslot;
import Location.MultipleLocationEntryImpl;
import Resource.Plane;
import Resource.SingleResourceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Resource.SingleResource;

/**
 * 航班计划项
 * AF：将该类映射到在途径location中的所有地点，起飞时间降落时间位于mtl中，由plane飞机进行飞行的航班集合中
 * RI：location列表长度为mtl长度+1
 * rep from exposure:使用private修饰
 */
public class FlightEntry extends CommomPlanningEntry implements FlightPlanningEntry {
    private MultipleLocationEntryImpl location;
    private MultipleTimeLotImple mtl;
    private SingleResourceImpl<Plane> plane;

    /**
     * 构造方法
     * @param location 由地点列表创建的地点组合类
     * @param mtl 由Timeslot类列表创建的时间组合类，要求长度为location列表长度减1
     */
    private FlightEntry(MultipleLocationEntryImpl location, MultipleTimeLotImple mtl) {
        this.location = location;
        this.mtl = mtl;
        checkRep();
    }

    /**
     * 工厂方法
     * @param beginLocation 起始地点
     * @param endLocation 终止地点
     * @param timeslot 由起飞时间和终止时间构成的时间对
     * @return 返回一个未命名的航班计划项实例
     */
    static FlightEntry getInstance(Location beginLocation, Location endLocation, Timeslot timeslot)
    {
        List<Location> locations = new ArrayList<>();
        locations.add(beginLocation);
        locations.add(endLocation);
        List<Timeslot> timeslotList = new ArrayList<>();
        timeslotList.add(timeslot);
        FlightEntry flightEntry = new FlightEntry(new MultipleLocationEntryImpl(locations),new MultipleTimeLotImple(timeslotList));
        return flightEntry;
    }

    /**
     * 得到地点列表
     * @return 不可改的地点列表
     */
    @Override
    public List<Location> getLocation() {
        return Collections.unmodifiableList(location.getLocation());
    }

    /**
     * 获得时间列表
     * @return 获得不可改的时间列表
     */
    @Override
    public List<Timeslot> getTimeList() {
        return Collections.unmodifiableList(mtl.getTimeList());
    }

    /**
     * 设置资源
     * @param plane 要分配给该计划项的飞机对象
     */
    /*@Override
    public void setResource(Plane plane) {
        if (this.plane!=null)
            this.plane.setResource(plane);
    }*/

    /**
     * 获得资源
     * @return 返回已分配的资源，若还未分配返回null
     */
    @Override
    public Plane getResource() {
        if(plane!=null)
            return this.plane.getResource();
        else{
            return null;
        }
    }

    /**
     * 设置资源委派关系
     * @param resource 资源类（要求类型是(SingleResourceImpl<Plane>)
     */
    @Override
    public void setResourceDelegation(Resource resource) {
        if (resource == null)
        {
            plane = null;
            return;
        }
        if (resource instanceof SingleResourceImpl)
            plane = (SingleResourceImpl<Plane>) resource;
        else
            assert false;
    }

    /**
     * make sure RI = true
     */
    private void  checkRep()
    {
        if (mtl.getTimeList().size()!=location.getLocation().size()-1)
            assert false;
    }
}

