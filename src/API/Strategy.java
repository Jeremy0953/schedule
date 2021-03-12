package API;

import Entry.PlanningEntry;

/**
 * 策略模式的策略接口
 * @param <R> 资源泛型
 */
public interface Strategy<R> {
    public PlanningEntry findPreEntryPerResource();
}
