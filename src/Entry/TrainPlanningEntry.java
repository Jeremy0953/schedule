package Entry;

import Location.MultipleLocationEntry;
import Resource.MultipleSortedResourceEntry;
import Time.MultipleTimeLotEntry;

/**
 * 高铁计划项组合接口
 */
public interface TrainPlanningEntry extends MultipleLocationEntry, MultipleSortedResourceEntry, MultipleTimeLotEntry,BlockableEntry {
}
