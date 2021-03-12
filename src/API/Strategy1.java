package API;

import Comparator.CourseEntryComparator;
import Comparator.FlightComparator;
import Comparator.TrainEntryComparator;
import Entry.CourseEntry;
import Entry.FlightEntry;
import Entry.PlanningEntry;
import Entry.TrainEntry;
import State.WaitingState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 算法1
 * @param <R> 资源泛型
 */
public class Strategy1<R> implements Strategy<R> {
    R r;
    PlanningEntry e;
    List<? extends PlanningEntry> entries;

    /**
     * 构造函数
     * @param r 资源
     * @param e 使用该资源的计划项
     * @param entries 计划项列表(前置条件要求所有计划项是同一个类型的）
     */
    public Strategy1(R r, PlanningEntry e, List<? extends PlanningEntry> entries) {
        this.r = r;
        this.e = e;
        this.entries = entries;
        checkRep();
    }

    /**
     * 找到前一个使用的计划项
     * @return 找到的前一个使用的计划项，若无返回null
     * 防御策略，用断言再次检查是否满足返回的计划项使用了该资源且时间比e要早。
     */
    @Override
    public PlanningEntry findPreEntryPerResource() {
        if(e instanceof FlightEntry)
        {
            List<FlightEntry> flightEntries = new ArrayList<>();
            for (int i = 0;i<entries.size();i++)
            {
                flightEntries.add((FlightEntry)entries.get(i));
            }
            Collections.sort(flightEntries,new FlightComparator());
            int index = flightEntries.indexOf((FlightEntry)e);
            for (int i = index-1;i>=0;i--)
            {
                if (flightEntries.get(i).getState() instanceof WaitingState)
                    continue;
                if (flightEntries.get(i).getResource().equals(r))
                {
                    assert flightEntries.get(i).getResource().equals(r)&&
                            flightEntries.get(i).getTimeList().get(0).getBeginTime().isBefore(((FlightEntry) e).getTimeList().get(0).getBeginTime());
                    return flightEntries.get(i);
                }
            }
            return null;
        }
        if (e instanceof TrainEntry) {
            List<TrainEntry> trainEntries = new ArrayList<>();
            for (int i = 0; i < entries.size(); i++) {
                trainEntries.add((TrainEntry) entries.get(i));
            }
            Collections.sort(trainEntries, new TrainEntryComparator());
            int index = trainEntries.indexOf((TrainEntry) e);
            for (int i = index - 1; i >= 0; i--) {
                if (trainEntries.get(i).getState() instanceof WaitingState)
                    continue;
                if (trainEntries.get(i).getResource().contains(r)) {
                    assert trainEntries.get(i).getResource().contains(r)
                            &&trainEntries.get(i).getTimeList().get(0).getBeginTime().isBefore(((TrainEntry) e).getTimeList().get(0).getBeginTime());
                    return trainEntries.get(i);

                }
            }
            return null;
        }if (e instanceof CourseEntry) {
            List<CourseEntry> courseEntries = new ArrayList<>();
            for (int i = 0; i < entries.size(); i++) {
                courseEntries.add((CourseEntry) entries.get(i));
            }
            Collections.sort(courseEntries, new CourseEntryComparator());
            int index = courseEntries.indexOf((CourseEntry) e);
            for (int i = index - 1; i >= 0; i--)
            {
                if (courseEntries.get(i).getState() instanceof WaitingState)
                    continue;
                if (courseEntries.get(i).getResource().equals(r))
                {
                    assert courseEntries.get(i).getResource().equals(r)
                            &&courseEntries.get(i).getTimeList().get(0).getBeginTime().isBefore(((CourseEntry) e).getTimeList().get(0).getBeginTime());
                    return courseEntries.get(i);
                }
            }
            return null;
        }
        return null;
    }
    private void checkRep()
    {
        if (e instanceof FlightEntry)
        {
            for (int i = 0;i<entries.size();i++)
            {
                if (!(entries.get(i) instanceof FlightEntry))
                    assert false;
            }
            if(!((FlightEntry) e).getResource().equals(r))
                assert false;
        }
        if (e instanceof TrainEntry)
        {
            for (int i = 0;i<entries.size();i++)
            {
                if (!(entries.get(i) instanceof TrainEntry))
                    assert false;
            }
            if(!((TrainEntry) e).getResource().contains(r))
                assert false;
        }
        if (e instanceof CourseEntry)
        {
            for (int i = 0;i<entries.size();i++)
            {
                if (!(entries.get(i) instanceof CourseEntry))
                    assert false;
            }
            if(!((CourseEntry) e).getResource().equals(r))
                assert false;
        }
    }
}
