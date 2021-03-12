package Entry;

import Exceptions.CantCancelException;
import Resource.Resource;
import State.EntryState;
import State.WaitingState;

/**
 * 基本计划项实现类
 */
public abstract class CommomPlanningEntry implements PlanningEntry {
    protected String planningEntryName;
    protected EntryState state;

    /**
     * 创建计划项，给计划项命名，将计划项置于等待状态（使用先必须先用getInstance方法或者工厂来创建一个实例之后再用该方法命名 ）
     * @param name 计划项名字
     */
    @Override
    public void creat(String name) {
        planningEntryName = name;
        state = new WaitingState();
        checkRep();
    }

    /**
     * 运行，委派给state
     */
    @Override
    public void run() {
        state.run(this);
    }

    /**
     * 取消，委派给state
     */
    @Override
    public void cancel() throws CantCancelException {
        state.cancel(this);
    }

    /**
     * 结束，委派给state
     */
    @Override
    public void end() {
        state.end(this);
    }

    /**
     * 获得状态
     * @return state该计划项当前所处的状态类
     */
    @Override
    public EntryState getState() {
        return state;
    }

    /**
     * 设置状态
     * @param entryState 状态变量
     */
    @Override
    public void setState(EntryState entryState) {
        state = entryState;
    }

    /**
     * 获取计划项名字
     * @return 计划项名字
     */
    @Override
    public String getName() {
        return planningEntryName;
    }

    /**
     * 分配资源
     * @param resource 资源类
     */
    @Override
    public void allocate(Resource resource) {
        state.allocate(this,resource);
    }

    /**
     * 返回计划项号
     * @return 计划项号
     */
    @Override
    public String toString() {
        return getName();
    }
    /**
     * make sure RI == true;
     */
    private void checkRep()
    {
        if (planningEntryName==null||state==null)
            assert false;
    }
}
