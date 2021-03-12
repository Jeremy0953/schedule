package debug;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FlightClientTest {
    private List<Plane> planes = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();
    private SimpleDateFormat pattern= new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Calendar calendar1;
    private Calendar calendar2;
    @Before
    public void init()
    {
        planes.clear();
        flights.clear();
    }
    /**
     * 测试策略：
     * 等价类划分：
     * 航班数目：0，1，>1
     * 航班中产生冲突的航班数目：0，大于0小于航班总数，等于航班总数
     * 飞机数目：0，1，>1
     * 飞机数目与航班数目关系：小于冲突航班数，等于发生冲突的航班数，大于发生冲突的航班数
     */
    //覆盖航班数目为0，发生冲突航班数目为0，飞机数目为0，飞机数目等于冲突航班数目情况
    @Test
    public void test1()
    {
        assertTrue(new FlightClient().planeAllocation(planes,flights));
    }
    //覆盖航班数目为1，发生冲突航班数目为0，飞机数目为0，飞机数目等于冲突航班数目情况
    @Test
    public void test2() throws ParseException {
        Flight flight = new Flight();
        calendar1 = Calendar.getInstance();
        calendar1.setTime(pattern.parse("2020-06-02 00:00"));
        flight.setDepartTime(calendar1);
        calendar2 =  Calendar.getInstance();
        calendar2.setTime(pattern.parse("2020-06-02 01:00"));
        flight.setArrivalTime(calendar2);
        flight.setFlightDate(calendar1);
        flight.setFlightNo("1");
        flights.add(flight);
        assertFalse(new FlightClient().planeAllocation(planes,flights));
    }
    //覆盖航班数目为2，发生冲突航班数目等于航班数，飞机数目为1，飞机数目小于冲突航班数目情况
    @Test
    public void test3() throws ParseException {
        Flight flight1 = new Flight();
        calendar1 = Calendar.getInstance();
        calendar1.setTime(pattern.parse("2020-06-02 00:00"));
        flight1.setDepartTime(calendar1);
        calendar2 =  Calendar.getInstance();
        calendar2.setTime(pattern.parse("2020-06-02 01:00"));
        flight1.setArrivalTime(calendar2);
        flight1.setFlightDate(calendar1);
        flight1.setFlightNo("1");
        flights.add(flight1);
        Flight flight2 = new Flight();
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendar1.setTime(pattern.parse("2020-06-02 00:30"));
        calendar2.setTime(pattern.parse("2020-06-02 01:30"));
        flight2.setDepartTime(calendar1);
        flight2.setArrivalTime(calendar2);
        flight2.setFlightDate(calendar1);
        flight2.setFlightNo("2");
        flights.add(flight2);
        Plane plane = new Plane();
        plane.setPlaneNo("1");
        planes.add(plane);
        assertFalse(new FlightClient().planeAllocation(planes,flights));
    }
    //覆盖航班数目为3，发生冲突航班数目小于航班数，飞机数目为2，飞机数目等于冲突航班数目情况
    @Test
    public void test4() throws ParseException {
        Flight flight1 = new Flight();
        calendar1 = Calendar.getInstance();
        calendar1.setTime(pattern.parse("2020-06-02 00:00"));
        flight1.setDepartTime(calendar1);
        calendar2 =  Calendar.getInstance();
        calendar2.setTime(pattern.parse("2020-06-02 01:00"));
        flight1.setArrivalTime(calendar2);
        flight1.setFlightDate(calendar1);
        flight1.setFlightNo("1");
        flights.add(flight1);
        Flight flight2 = new Flight();
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendar1.setTime(pattern.parse("2020-06-02 00:30"));
        calendar2.setTime(pattern.parse("2020-06-02 01:30"));
        flight2.setDepartTime(calendar1);
        flight2.setArrivalTime(calendar2);
        flight2.setFlightDate(calendar1);
        flight2.setFlightNo("2");
        flights.add(flight2);
        Flight flight3 = new Flight();
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendar1.setTime(pattern.parse("2020-06-02 02:00"));
        calendar2.setTime(pattern.parse("2020-06-02 03:00"));
        flight3.setDepartTime(calendar1);
        flight3.setArrivalTime(calendar2);
        flight3.setFlightDate(calendar1);
        flight3.setFlightNo("3");
        flights.add(flight3);
        Plane plane1 = new Plane();
        plane1.setPlaneNo("1");
        planes.add(plane1);
        Plane plane2 = new Plane();
        plane2.setPlaneNo("2");
        planes.add(plane2);
        assertTrue(new FlightClient().planeAllocation(planes,flights));
    }
    //覆盖航班数目为3，发生冲突航班数目小于航班数，飞机数目为2，飞机数目大于冲突航班数目情况
    @Test
    public void test5() throws ParseException {
        Flight flight1 = new Flight();
        calendar1 = Calendar.getInstance();
        calendar1.setTime(pattern.parse("2020-06-02 00:00"));
        flight1.setDepartTime(calendar1);
        calendar2 =  Calendar.getInstance();
        calendar2.setTime(pattern.parse("2020-06-02 01:00"));
        flight1.setArrivalTime(calendar2);
        flight1.setFlightDate(calendar1);
        flight1.setFlightNo("1");
        flights.add(flight1);
        Flight flight2 = new Flight();
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendar1.setTime(pattern.parse("2020-06-02 00:30"));
        calendar2.setTime(pattern.parse("2020-06-02 01:30"));
        flight2.setDepartTime(calendar1);
        flight2.setArrivalTime(calendar2);
        flight2.setFlightDate(calendar1);
        flight2.setFlightNo("2");
        flights.add(flight2);
        Flight flight3 = new Flight();
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendar1.setTime(pattern.parse("2020-06-02 02:00"));
        calendar2.setTime(pattern.parse("2020-06-02 03:00"));
        flight3.setDepartTime(calendar1);
        flight3.setArrivalTime(calendar2);
        flight3.setFlightDate(calendar1);
        flight3.setFlightNo("3");
        flights.add(flight3);
        Plane plane1 = new Plane();
        plane1.setPlaneNo("1");
        planes.add(plane1);
        Plane plane2 = new Plane();
        plane2.setPlaneNo("2");
        planes.add(plane2);
        Plane plane3 = new Plane();
        plane3.setPlaneNo("3");
        planes.add(plane3);
        assertTrue(new FlightClient().planeAllocation(planes,flights));
    }
}
