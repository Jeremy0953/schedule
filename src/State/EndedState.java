package State;

import Entry.PlanningEntry;
import Exceptions.CantCancelException;
import Location.Location;
import Location.SingleChangeableLocation;
import Resource.Resource;

/**
 * 结束状态
 */
public class EndedState implements EntryState {
    /**
     * 返回字符串
     * @return 返回Ended字符串
     */
    @Override
    public String toString() {
        return "Ended";
    }

    /**
     * 分配资源，但由于已经结束所以无法进行
     * @param entry 计划项
     * @param resource 资源类
     */
    @Override
    public void allocate(PlanningEntry entry, Resource resource) {
        System.out.println("the entry is finished,can't do that.");
    }

    /**
     * 运行，但由于已经结束所以无法进行
     * @param entry 计划项
     */
    @Override
    public void run(PlanningEntry entry) {
        System.out.println("the entry is finished,can't do that.");
    }

    /**
     * 阻塞，但由于已经结束所以无法进行
     * @param entry 计划项
     */
    @Override
    public void block(PlanningEntry entry) {
        System.out.println("the entry is finished,can't do that.");
    }

    /**
     * 取消计划项，由于已经处于结束状态所以无法取消
     * @param entry 计划项
     * @throws CantCancelException 由于已经处于结束状态所以无法取消而抛出异常
     */
    @Override
    public void cancel(PlanningEntry entry) throws CantCancelException {
        throw new CantCancelException("the entry is finished,can't do that.");
    }

    /**
     * 结束，但由于已经结束所以无法进行
     * @param entry 计划项
     */
    @Override
    public void end(PlanningEntry entry) {
        System.out.println("the entry is finished,can't do that.");
    }

    /**
     * 分配地点，但由于已经结束所以无法进行
     * @param entry 计划项
     * @param locationInterface 地点组合接口
     * @param location 地点
     */
    @Override
    public void setlocation(PlanningEntry entry, SingleChangeableLocation locationInterface, Location location) {
        System.out.println("the entry is finished,can't do that.");
    }
}
