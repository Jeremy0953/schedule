package Entry;

import Exceptions.CantCancelException;
import Resource.Resource;
import State.EntryState;

/**
 * 计划项接口
 */
public interface PlanningEntry {
    /**
     * 将计划项命名为name
     * @param name 计划项名字
     */
    public void creat(String name);

    /**
     * 运行计划项
     */
    public void run();

    /**
     * 取消计划项
     */
    public void cancel() throws CantCancelException;

    /**
     * 终止计划项
     */
    public void end();

    /**
     * 得到计划项状态
     * @return 计划项状态
     */
    public EntryState getState();

    /**
     * 设置计划项状态
     * @param entryState 状态变量
     */
    public void setState(EntryState entryState);

    /**
     * 获得计划项编号
     * @return 返回计划项编号
     */
    public String getName();

    /**
     * 为计划项分配资源
     * @param resource 资源类
     */
    public void allocate(Resource resource);

    /**
     * 设置计划项资源委派
     * @param resource 资源类
     */
    public void setResourceDelegation(Resource resource);
}
