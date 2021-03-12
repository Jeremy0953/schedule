package Entry;

import Location.SingleChangeableLocation;
import Resource.SingleResource;
import Resource.Teacher;
import Time.MultipleTimeLotEntry;

/**
 * 课程计划项组合接口
 */
public interface CoursePlanningEntry extends SingleChangeableLocation, SingleResource<Teacher>, MultipleTimeLotEntry {
}
