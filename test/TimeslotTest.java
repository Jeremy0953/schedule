import static org.junit.Assert.*;

import Time.Timeslot;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeslotTest {

    /**
     * 测试策略：
     * 新建一个Timeslot对象并且调用getBeginTime和getEndTime看是否相同
     */
    @Test(expected = DateTimeParseException.class)
    public void constructorTest() throws ParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Timeslot timeslot = new Timeslot(LocalDateTime.parse("2020-05-16 08:00",dateTimeFormatter),LocalDateTime.parse("2020-05-17 08:00",dateTimeFormatter));
        assertEquals(LocalDateTime.parse("2020-05-16 08:00",dateTimeFormatter),timeslot.getBeginTime());
        assertEquals(LocalDateTime.parse("2020-05-17 08:00",dateTimeFormatter),timeslot.getEndTime());
        timeslot = new Timeslot("2020-05-16 08:00","2020-05-17 08:00");
        assertEquals(LocalDateTime.parse("2020-05-16 08:00",dateTimeFormatter),timeslot.getBeginTime());
        assertEquals(LocalDateTime.parse("2020-05-17 08:00",dateTimeFormatter),timeslot.getEndTime());
        //分别测试两种构造方法和get函数
        timeslot = new Timeslot("2020-05-16 08:00","2020-05-17 08-00");
        //测试不合语法的输入
        timeslot = null;
    }

}
