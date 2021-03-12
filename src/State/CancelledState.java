package State;

import Entry.PlanningEntry;
import Exceptions.CantCancelException;
import Location.Location;
import Location.SingleChangeableLocation;
import Resource.Resource;

/**
 * 取消状态
 */
public class CancelledState implements EntryState {
    /**
     * 返回取消状态字符串
     * @return 取消字符串
     */
    @Override
    public String toString() {
        return "Cancelled";
    }

    /**
     * 由于取消状态所以无法进行资源分配
     * @param entry 计划项
     * @param resource 资源类
     */
    @Override
    public void allocate(PlanningEntry entry, Resource resource) {
        System.out.println("the entry is cancelled,can't allocate");
    }

    /**
     * 运行，由于取消状态所以无法正常运行
     * @param entry 计划项
     */
    @Override
    public void run(PlanningEntry entry) {
        System.out.println("the entry is cancelled,can't run");
    }

    /**
     * 阻塞，由于取消状态所以无法正常阻塞
     * @param entry 计划项
     */
    @Override
    public void block(PlanningEntry entry) {
        System.out.println("the entry is cancelled,can't block");
    }

    /**
     * 取消
     * @param entry 计划项
     * @throws CantCancelException 由于已经位于取消状态所以如果依旧执行取消操作则会抛出异常
     */
    @Override
    public void cancel(PlanningEntry entry) throws CantCancelException {
        throw new CantCancelException("this entry is cancelled already");
    }

    /**
     * 结束，由于取消状态所以无法正常结束
     * @param entry 计划项
     */
    @Override
    public void end(PlanningEntry entry) {
        System.out.println("the entry is cancelled,can't end");
    }

    /**
     * 设置地点，由于取消状态所以无法正常设置
     * @param entry 计划项
     * @param locationInterface 地点组合接口
     * @param location 地点
     */
    @Override
    public void setlocation(PlanningEntry entry, SingleChangeableLocation locationInterface, Location location) {
        System.out.println("the entry is cancelled,can't set location");
    }
}
