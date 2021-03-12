package Entry;

import Location.Location;
import Resource.Resource;
import Time.MultipleTimeLotImple;
import Time.Timeslot;
import java.util.ArrayList;
import java.util.List;
import Location.SingleChangeableLocationImpl;
import Resource.Teacher;
import Resource.SingleResource;
import Resource.SingleResourceImpl;
import Location.*;

/**
 * 课程计划项实现类
 * AF：将该类映射到一个地点为location，所用资源为resource，时间为time的课程计划项
 * RI：要求time的大小为1
 * rep from exposure:使用private修饰
 */
public class CourseEntry extends CommomPlanningEntry implements CoursePlanningEntry {
    private SingleChangeableLocationImpl location;
    private SingleResource<Teacher> resource;
    private MultipleTimeLotImple time;

    /**
     * 构造方法
     * @param location 地点
     * @param time 时间 （要求该time只有一个时间对）
     */
    private CourseEntry(SingleChangeableLocationImpl location, MultipleTimeLotImple time)
    {
        this.location = location;
        this.time = time;
        checkRep();
    }

    /**
     * 获取计划项的工场方法
     * @param location 地点
     * @param timeslot 时间
     * @return 返回构造出来的课程计划项
     */
    public static CourseEntry getInstance(Location location,Timeslot timeslot)
    {
        List<Timeslot> timeslotsList = new ArrayList<>();
        timeslotsList.add(timeslot);
        return new CourseEntry(new SingleChangeableLocationImpl(location),new MultipleTimeLotImple(timeslotsList));
    }

    /**
     * 设置资源委派关系
     * @param resource 资源类，要求resource是(SingleResource<Teacher>)类
     */
    @Override
    public void setResourceDelegation(Resource resource) {
        if (resource ==null)
        {
            this.resource = null;
            return;
        }
        if(resource instanceof SingleResourceImpl)
        {
            this.resource = (SingleResourceImpl<Teacher>)resource;
        }
        else
            assert false;
        checkRep();
    }

    /**
     * 得到位置信息
     * @return 位置location
     */
    @Override
    public Location getLocation() {
        return this.location.getLocation();
    }

    /**
     * 设置位置
     * @param locaiton 要设置为的Location
     */
    @Override
    public void setLocaiton(Location locaiton) {
        state.setlocation(this,this.location,locaiton);
        checkRep();
    }

    /**
     * 获得时间链表
     * @return 但会时间列表
     */
    @Override
    public List<Timeslot> getTimeList() {
        return time.getTimeList();
    }

    /**
     * 设置资源
     * @param teacher 资源
     */
    /*@Override
    public void setResource(Teacher teacher) {
        this.resource.setResource(teacher);
        checkRep();
    }*/

    /**
     * 获取资源
     * @return 返回已分配的资源，若还未分配返回null
     */
    @Override
    public Teacher getResource() {
        if(resource!=null)
        {
            return this.resource.getResource();
        }
        else
            return null;
    }

    /**
     * make sure RI = true;
     */
    private void checkRep()
    {
        if (location==null||time==null)
            assert false;
        if(time.getTimeList().size()!=1)
            assert false;
    }
}
