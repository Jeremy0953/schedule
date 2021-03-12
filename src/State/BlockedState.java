package State;

import Entry.PlanningEntry;
import Location.Location;
import Location.SingleChangeableLocation;
import Resource.Resource;

/**
 * 阻塞状态
 */
public class BlockedState implements EntryState {
    /**
     * 重写toString
     * @return 返回状态信息
     */
    @Override
    public String toString() {
        return "Blocked";
    }

    /**
     * 分配资源，由于已经是阻塞状态所以无法分配资源
     * @param entry 计划项
     * @param resource 资源类
     */
    @Override
    public void allocate(PlanningEntry entry, Resource resource) {
        System.out.println("this entry is blocked now ,it can't be allocated,because it has been allocated.");
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
     * 阻塞计划项，由于已经是阻塞状态所以不能再重复阻塞
     * @param entry 计划项
     */
    @Override
    public void block(PlanningEntry entry) {
        System.out.println("this entry is already blocked,it can't be blocked again.");
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
     * 结束计划项，，由于当前处于blocked状态所以无法直接结束
     * @param entry 计划项
     */
    @Override
    public void end(PlanningEntry entry) {
        System.out.println("this entry is blocked now,it can't be finished now.");
    }

    /**
     * 设置地点，由于当前阻塞状态所以无法设置地点
     * @param entry 计划项
     * @param locationInterface 地点组合接口
     * @param location 地点
     */
    @Override
    public void setlocation(PlanningEntry entry, SingleChangeableLocation locationInterface, Location location) {
        System.out.println("this entry can't be set Location now");
    }
}
