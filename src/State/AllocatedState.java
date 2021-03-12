package State;

import Entry.CourseEntry;
import Entry.PlanningEntry;
import Location.SingleChangeableLocation;
import Resource.Resource;
import Location.Location;

/**
 * 已分配资源后的状态
 */
public class AllocatedState implements EntryState {
    /**
     * toString方法重写，返回分配状态字符串
     * @return
     */
    @Override
    public String toString() {
        return "Allocated";
    }

    /**
     * 重写资源分配函数，由于已分配资源所以不再进行分配
     * @param entry 计划项
     * @param resource 资源类
     */
    @Override
    public void allocate(PlanningEntry entry, Resource resource) {
        System.out.println("this entry has been allocated,it can't be allocated again");
    }

    /**
     * 运行计划项
     * @param entry 计划项
     */
    @Override
    public void run(PlanningEntry entry) {
        entry.setState(new RunningState());
    }

    /**
     * 阻塞计划项,由于处于Allocated状态所以无法阻塞
     * @param entry 计划项
     */
    @Override
    public void block(PlanningEntry entry) {
        System.out.println("this entry is allocated now,it can't be blocked.");
    }

    /**
     * 取消计划项
     * @param entry 计划项
     */
    @Override
    public void cancel(PlanningEntry entry) {
        entry.setState(new CancelledState());
    }

    /**
     * 结束计划项, 无法直接停止
     * @param entry 计划项
     */
    @Override
    public void end(PlanningEntry entry) {
        System.out.println("this entry is allocated now,it can't be finished.");
    }

    /**
     * 设置地点，只针对课程有效
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
