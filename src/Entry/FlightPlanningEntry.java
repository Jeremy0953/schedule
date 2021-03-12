package Entry;

import Location.MultipleLocationEntry;
import Resource.Plane;
import Resource.SingleResource;
import Time.MultipleTimeLotEntry;

/**
 * 航班计划项组合接口
 */
public interface FlightPlanningEntry extends MultipleTimeLotEntry, MultipleLocationEntry, SingleResource<Plane> {
}
