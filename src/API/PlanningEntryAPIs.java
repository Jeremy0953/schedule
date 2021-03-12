package API;

import Entry.CourseEntry;
import Entry.FlightEntry;
import Entry.PlanningEntry;
import Entry.TrainEntry;
import State.WaitingState;
import Resource.Plane;
import java.util.Collections;
import java.util.List;

/**
 * 扩展API
 * @param <R>
 */
public class PlanningEntryAPIs <R>{
    /**
     * 检查位置冲突
     * @param entries 计划项 列表(前置条件要求所有计划项是同一个类型的）
     * @return 若这些计划项中存在位置冲突则返回true，否则false
     * 防御策略，遍历查看列表中的每个元素是否是同一个类型
     */
    public static boolean checkLocationConflict(List<? extends PlanningEntry> entries)
    {
        if (entries.isEmpty())
            return false;
        else{
            if (entries.get(0) instanceof FlightEntry)
            {
                for (int i = 0;i<entries.size();i++)
                {
                    if (!(entries.get(i) instanceof FlightEntry))
                        assert false;
                }
                return false;
            }
            if (entries.get(0) instanceof TrainEntry)
            {
                for (int i = 0;i<entries.size();i++)
                {
                    if (!(entries.get(i) instanceof TrainEntry))
                        assert false;
                }
                return false;
            }
            if(entries.get(0) instanceof CourseEntry)
            {
                for (int i = 0;i<entries.size();i++)
                {
                    if (!(entries.get(i) instanceof CourseEntry))
                        assert false;
                }
                for (int i = 0;i<entries.size();i++)
                {
                    for (int j = i+1;j<entries.size();j++)
                    {
                        CourseEntry courseEntry1 = (CourseEntry) entries.get(i);
                        CourseEntry courseEntry2 = (CourseEntry) entries.get(j);
                        if (courseEntry1.getLocation().equals(courseEntry2.getLocation()))
                        {
                            if (!((courseEntry1.getTimeList().get(0).getBeginTime().isAfter(courseEntry2.getTimeList().get(0).getEndTime()))
                            ||(courseEntry1.getTimeList().get(0).getEndTime().isBefore(courseEntry2.getTimeList().get(0).getEndTime()))))
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查资源 冲突
     * @param entries 计划项列表(前置条件要求所有计划项是同一个类型的）
     * @return 若这些计划项中存在资源冲突则返回true，否则false
     * 防御策略，遍历查看列表中的每个元素是否是同一个类型
     */
    public static boolean checkResourceExclusiveConflict(List<? extends PlanningEntry> entries) {
        if (entries.isEmpty())
            return false;
        else {
            if (entries.get(0) instanceof FlightEntry)
            {
                for (int i = 0;i<entries.size();i++)
                {
                    if (!(entries.get(i) instanceof FlightEntry))
                        assert false;
                }
            }
            if (entries.get(0) instanceof TrainEntry)
            {
                for (int i = 0;i<entries.size();i++)
                {
                    if (!(entries.get(i) instanceof TrainEntry))
                        assert false;
                }
            }
            if (entries.get(0) instanceof CourseEntry)
            {
                for (int i = 0;i<entries.size();i++)
                {
                    if (!(entries.get(i) instanceof CourseEntry))
                        assert false;
                }
            }
            for (int i = 0; i < entries.size(); i++) {
                for (int j = i + 1; j < entries.size(); j++) {

                    if (entries.get(i) instanceof FlightEntry) {
                        FlightEntry flight1 = (FlightEntry) entries.get(i);
                        FlightEntry flight2 = (FlightEntry) entries.get(j);
                        if (flight1.getState() instanceof WaitingState) {
                            break;
                        }
                        if (flight2.getState() instanceof WaitingState) {
                            continue;
                        }
                        Plane plane1 = flight1.getResource();
                        Plane plane2 = flight2.getResource();
                        if (flight1.getResource().equals(flight2.getResource())) {
                            if ((flight1.getTimeList().get(0).getEndTime().isBefore(flight2.getTimeList().get(0).getBeginTime()))) {
                                continue;
                            }
                            if ((flight1.getTimeList().get(0).getBeginTime().isAfter(flight2.getTimeList().get(0).getEndTime()))) {
                                continue;
                            }
                            return true;
                        }
                    }
                    if (entries.get(i) instanceof TrainEntry) {

                        TrainEntry trainEntry1 = (TrainEntry) entries.get(i);
                        TrainEntry trainEntry2 = (TrainEntry) entries.get(j);
                        if (trainEntry1.getState() instanceof WaitingState) {
                            break;
                        }
                        if (trainEntry2.getState() instanceof WaitingState) {
                            continue;
                        }
                        if (Collections.disjoint(trainEntry1.getResource(), trainEntry2.getResource())) {
                            continue;
                        } else {
                            if ((trainEntry1.getTimeList().get(trainEntry1.getTimeList().size()-1).getEndTime().isBefore(trainEntry2.getTimeList().get(0).getBeginTime()))) {
                                continue;
                            }
                            if ((trainEntry1.getTimeList().get(0).getBeginTime().isAfter(trainEntry2.getTimeList().get(trainEntry2.getTimeList().size()-1).getEndTime()))) {
                                continue;
                            }
                            return true;
                        }
                    }
                    if (entries.get(i) instanceof CourseEntry) {
                        CourseEntry courseEntry1 = (CourseEntry) entries.get(i);
                        CourseEntry courseEntry2 = (CourseEntry) entries.get(j);
                        if (courseEntry1.getState() instanceof WaitingState) {
                            break;
                        }
                        if (courseEntry2.getState() instanceof WaitingState) {
                            continue;
                        }
                        if (courseEntry1.getResource().equals(courseEntry2.getResource())) {
                            if ((courseEntry1.getTimeList().get(0).getEndTime().isBefore(courseEntry2.getTimeList().get(0).getBeginTime()))) {
                                continue;
                            }
                            if ((courseEntry1.getTimeList().get(0).getBeginTime().isAfter(courseEntry2.getTimeList().get(0).getEndTime()))) {
                                continue;
                            }
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    /**
     * 找前序项
     * @param strategy 使用的算法策略类
     * @return 返回前序项
     */
    public PlanningEntry findPreEntryPerResource(Strategy<R> strategy) {
        return strategy.findPreEntryPerResource();
    }
}
