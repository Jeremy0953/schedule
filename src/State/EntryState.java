package State;

import Entry.PlanningEntry;
import Exceptions.CantCancelException;
import Location.Location;
import Location.SingleChangeableLocation;
import Resource.Resource;

/**
 * 状态类接口
 */
public interface EntryState {
    /**
     * 为计划项分配资源
     * @param entry 计划项
     * @param resource 资源类
     */
    public void allocate(PlanningEntry entry, Resource resource);

    /**
     * 运行该计划项
     * @param entry 计划项
     */
    public void run(PlanningEntry entry);

    /**
     * 阻塞计划项
     * @param entry 计划项
     */
    public void block(PlanningEntry entry);

    /**
     * 取消计划项
     * @param entry 计划项
     */
    public void cancel(PlanningEntry entry) throws CantCancelException;

    /**
     * 结束计划项
     * @param entry 计划项
     */
    public void end(PlanningEntry entry);

    /**
     * 为计划项设置地点
     * @param entry 计划项
     * @param locationInterface 地点组合接口
     * @param location 地点
     */
    public void setlocation(PlanningEntry entry, SingleChangeableLocation locationInterface, Location location);
}
