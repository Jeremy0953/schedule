package API;

import Entry.CourseEntry;
import Entry.FlightEntry;
import Entry.PlanningEntry;
import Entry.TrainEntry;
import State.WaitingState;

import java.util.List;
/**
 * 算法2
 * @param <R> 资源泛型
 */
public class Strategy2<R> implements Strategy<R> {
    R r;
    PlanningEntry e;
    List<? extends PlanningEntry> entries;
    /**
     * 构造函数
     * @param r 资源
     * @param e 使用该资源的计划项
     * @param entries 计划项列表(前置条件要求所有计划项是同一个类型的）
     */
    public Strategy2(R r, PlanningEntry e, List<? extends PlanningEntry> entries) {
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
        boolean firstFlag = false;
        if (e instanceof FlightEntry)
        {
            FlightEntry ret = null;
            for (int i = 0;i<entries.size();i++)
            {
                if (entries.get(i).getState() instanceof WaitingState)
                    continue;
                if(((FlightEntry)entries.get(i)).getResource().equals(r))
                {
                    if (((FlightEntry) entries.get(i)).getTimeList().get(0).getBeginTime().isBefore(((FlightEntry) e).getTimeList().get(0).getBeginTime()))
                    {
                        if (firstFlag) {
                            if (((FlightEntry) entries.get(i)).getTimeList().get(0).getBeginTime().isAfter(ret.getTimeList().get(0).getBeginTime()))
                            {
                                ret = (FlightEntry) entries.get(i);
                            }
                        }
                        else {
                            ret = (FlightEntry) entries.get(i);
                            firstFlag = true;
                        }
                    }
                }
            }
            if (ret!=null&&ret.equals(e))
                return null;
            if(ret!=null)
                assert ret.getResource().equals(r)&&
                    ret.getTimeList().get(0).getBeginTime().isBefore(((FlightEntry) e).getTimeList().get(0).getBeginTime());
            return ret;
        }
        if (e instanceof TrainEntry)
        {
            TrainEntry ret = null;
            for (int i = 0;i<entries.size();i++)
            {
                if (entries.get(i).getState() instanceof WaitingState)
                    continue;
                if(((TrainEntry)entries.get(i)).getResource().contains(r))
                {
                    if (((TrainEntry) entries.get(i)).getTimeList().get(0).getBeginTime().isBefore(((TrainEntry) e).getTimeList().get(0).getBeginTime()))
                    {
                        if (firstFlag) {
                            if (((TrainEntry) entries.get(i)).getTimeList().get(0).getBeginTime().isAfter(ret.getTimeList().get(0).getBeginTime()))
                            {
                                ret = (TrainEntry) entries.get(i);
                            }
                        }
                        else {
                            ret = (TrainEntry) entries.get(i);
                            firstFlag = true;
                        }
                    }
                }
            }
            if (ret!=null&&ret.equals(e))
                return null;
            if(ret!=null)
                assert ret.getResource().contains(r)
                    &&ret.getTimeList().get(0).getBeginTime().isBefore(((TrainEntry) e).getTimeList().get(0).getBeginTime());
            return ret;
        }
        if (e instanceof CourseEntry)
        {
            CourseEntry ret = null;
            for (int i = 0;i<entries.size();i++)
            {
                if (entries.get(i).getState() instanceof WaitingState)
                    continue;
                if(((CourseEntry)entries.get(i)).getResource().equals(r))
                {
                    if (((CourseEntry) entries.get(i)).getTimeList().get(0).getBeginTime().isBefore(((CourseEntry) e).getTimeList().get(0).getBeginTime()))
                    {
                        if (firstFlag) {
                        if (((CourseEntry) entries.get(i)).getTimeList().get(0).getBeginTime().isAfter(ret.getTimeList().get(0).getBeginTime()))
                        {
                            ret = (CourseEntry) entries.get(i);
                        }
                    }
                        else {
                        ret = (CourseEntry) entries.get(i);
                        firstFlag = true;
                    }
                    }
                }
            }
            if (ret!=null&&ret.equals(e))
                return null;
            if (ret!=null)
                assert ret.getResource().equals(r)
                    &&ret.getTimeList().get(0).getBeginTime().isBefore(((CourseEntry) e).getTimeList().get(0).getBeginTime());
            return ret;
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
