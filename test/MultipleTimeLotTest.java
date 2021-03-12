import static org.junit.Assert.*;

import Time.MultipleTimeLotImple;
import Time.Timeslot;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MultipleTimeLotTest {
    /**
     * 测试策略：
     * list长度：0，1，大于1
     */
    @Test
    public void constructorTest() throws ParseException {
        List<Timeslot> timeslots = new ArrayList<>();
        MultipleTimeLotImple mtl = new MultipleTimeLotImple(timeslots);
        assertEquals(0,mtl.getTimeList().size());
        //size == 0
        timeslots.add(new Timeslot("2020-05-01 01:00","2020-05-01 02:00"));
        mtl = new MultipleTimeLotImple(timeslots);
        assertEquals(1,mtl.getTimeList().size());
        assertEquals(new Timeslot("2020-05-01 01:00","2020-05-01 02:00"),mtl.getTimeList().get(0));
        //size == 1
        timeslots.add(new Timeslot("2020-05-01 03:00","2020-05-01 04:00"));
        mtl = new MultipleTimeLotImple(timeslots);
        assertEquals(2,mtl.getTimeList().size());
        for (int i = 0;i<timeslots.size();i++)
        {
            assertEquals(timeslots.get(i),mtl.getTimeList().get(i));
        }
        //size == 2
    }
}
