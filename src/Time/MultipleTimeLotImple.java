package Time;

import java.util.ArrayList;
import java.util.List;

public class MultipleTimeLotImple implements MultipleTimeLotEntry {
    /**
     * AF：将list映射成多个有序时间对的集合
     * RI：要求list中每个时间对的开始时间都要晚于前面时间对的结束时间，每个时间对的结束时间都要小于下一个时间对的开始时间
     * rep form exposure:将list用关键字private修饰，以及在get函数中进行防御性复制
     */
    private List<Timeslot> list = new ArrayList<>();

    /**
     * 构造函数
     * @param timeslotList 时间对的list
     */
    public MultipleTimeLotImple(List<Timeslot> timeslotList) {
        for (int i =0;i<timeslotList.size();i++)
        {
            list.add(timeslotList.get(i));
        }
        checkRep();
    }

    /**
     * 得到时间列表
     * @return 返回时间对list的一个副本
     */
    @Override
    public List<Timeslot> getTimeList() {
        List<Timeslot> ret = new ArrayList<>();
        for (int i = 0;i<list.size();i++)
        {
            ret.add(new Timeslot(list.get(i)));
        }
        return ret;
    }
    /**
     * 保证RI不变
     */
    private void checkRep()
    {
        for (int i = 0;i<list.size()-1;i++)
        {
            if (list.get(i).getEndTime().isAfter(list.get(i+1).getBeginTime()))
                assert false;
        }
    }
}
