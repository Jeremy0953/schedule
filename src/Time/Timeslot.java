package Time;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Timeslot {
    //AF：将两个LocalDateTime对象beginTime和endTime映射为从biginTime到endTime的时间对
    //RI：要求beginTime比endTime早
    //rep from exposure:rep 用private进行修饰
    private LocalDateTime beginTime;
    private LocalDateTime endTime;

    /**
     * 构造函数
     * @param begin 开始日期，String类型，要求必须是yyyy-MM-dd HH:mm形式
     * @param end 结束日期，String类型，要求必须是yyyy-MM-dd HH:mm形式
     */

    public Timeslot(String begin,String end) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        beginTime = LocalDateTime.parse(begin, pattern);
        endTime = LocalDateTime.parse(end, pattern);
        checkRep();
        //防御策略：检查是否满足开始时间早于结束时间的要求
    }

    /**
     * 构造函数
     * @param begin  开始时间
     * @param end 结束时间
     */
    public Timeslot(LocalDateTime begin,LocalDateTime end)
    {
        beginTime = begin;
        endTime = end;
        checkRep();
    }

    /**
     * 构造函数
     * @param timeslot 接收一个时间对然后复制一份
     */
    public Timeslot(Timeslot timeslot)
    {
        beginTime = timeslot.getBeginTime();
        endTime = timeslot.getEndTime();
        checkRep();
    }

    /**
     * 返回起始时间
     * @return 起始时间
     */
    public LocalDateTime getBeginTime()
    {
        return beginTime;
    }

    /**
     * 返回结束时间
     * @return 结束时间
     */
    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    /**
     * 检查RI不变性
     */
    private void checkRep()
    {
        if(beginTime.isAfter(endTime))
            assert false;
    }

    /**
     * 重写equals函数
     * @param o 比较对象
     * @return 若开始时间和结束时间均相同则认为相同
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timeslot)) return false;
        Timeslot timeslot = (Timeslot) o;
        return Objects.equals(beginTime, timeslot.beginTime) &&
                Objects.equals(endTime, timeslot.endTime);
    }

    /**
     * 重写hashCode函数
     * @return 返回hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(beginTime, endTime);
    }
}
