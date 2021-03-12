package State;

import Entry.CourseEntry;
import Entry.FlightEntry;
import Entry.PlanningEntry;
import Entry.TrainEntry;
import Location.Location;
import Location.SingleChangeableLocation;
import Resource.Resource;
import Resource.SingleResource;
import Resource.SingleResourceImpl;
import Resource.MultipleSortedResourceEntry;
/**
 * 等待状态
 */
public class WaitingState implements EntryState {
    /**
     * 返回Waiting字符串
     * @return Waiting
     */
    @Override
    public String toString() {
        return "Waiting";
    }

    /**
     * 分配资源
     * @param entry 计划项
     * @param resource 资源类 (要求和计划项配套）
     */
    @Override
    public void allocate(PlanningEntry entry, Resource resource) {
        if (!((entry instanceof FlightEntry&&resource instanceof SingleResource)
                ||(entry instanceof TrainEntry&& resource instanceof MultipleSortedResourceEntry)
                ||(entry instanceof CourseEntry&&resource instanceof SingleResource)))
            assert false;
        //防御策略，传入的计划项和资源类型必须配套
        entry.setResourceDelegation(resource);
        entry.setState(new AllocatedState());
    }

    /**
     * 运行计划项，但是由于等待状态所以无法进行
     * @param entry 计划项
     */
    @Override
    public void run(PlanningEntry entry) {
        System.out.println("the entry is waiting,can't do that.");
    }

    /**
     * 阻塞，但是由于等待状态所以无法进行
     * @param entry 计划项
     */
    @Override
    public void block(PlanningEntry entry) {
        System.out.println("the entry is waiting,can't do that.");
    }

    /**
     * 取消
     * @param entry 计划项
     */
    @Override
    public void cancel(PlanningEntry entry) {
        entry.setState(new CancelledState());
    }

    /**
     * 结束，但是由于等待状态所以无法进行
     * @param entry 计划项
     */
    @Override
    public void end(PlanningEntry entry) {
        System.out.println("the entry is waiting,can't do that.");
    }

    /**
     * 设置地址
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
