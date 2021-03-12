package State;

import Entry.CourseEntry;
import Entry.PlanningEntry;
import Exceptions.CantCancelException;
import Location.Location;
import Location.SingleChangeableLocation;
import Resource.Resource;

/**
 * 运行状态
 */
public class RunningState implements EntryState {
    /**
     * 返回状态字符串
     * @return 返回Running字符串
     */
    @Override
    public String toString() {
        return "Running";
    }

    /**
     * 分配资源，由于已经处于运行状态所以无法进行
     * @param entry 计划项
     * @param resource 资源类
     */
    @Override
    public void allocate(PlanningEntry entry, Resource resource) {
        System.out.println("the entry is running,cant do it.");
    }

    /**
     * 运行，由于已经处于运行状态所以无法进行
     * @param entry 计划项
     */
    @Override
    public void run(PlanningEntry entry) {
        System.out.println("the entry is running,cant do it.");
    }

    /**
     * 阻塞，由于已经处于运行状态所以无法进行
     * @param entry 计划项
     */
    @Override
    public void block(PlanningEntry entry) {
        entry.setState(new BlockedState());
    }

    /**
     * 取消，由于已经处于运行状态所以无法进行
     * @param entry 计划项
     * @throws CantCancelException 由于已经处于运行状态所以无法进行抛出异常
     */
    @Override
    public void cancel(PlanningEntry entry) throws CantCancelException {

        throw new CantCancelException("the entry is running,cant do it.");
    }

    /**
     * 结束运行
     * @param entry 计划项
     */
    @Override
    public void end(PlanningEntry entry) {
        entry.setState(new EndedState());
    }

    /**
     * 设置地点，只对课程计划项有效
     * @param entry 计划项
     * @param locationInterface 地点组合接口
     * @param location 地点
     */
    @Override
    public void setlocation(PlanningEntry entry, SingleChangeableLocation locationInterface, Location location) {
        if (entry instanceof CourseEntry)
        {
            locationInterface.setLocaiton(location);
        }
    }
}
