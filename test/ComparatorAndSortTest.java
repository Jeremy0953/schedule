import Comparator.*;
import static org.junit.Assert.*;

import Entry.*;
import Location.Location;
import Location.SimpleLocation;
import Time.Timeslot;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparatorAndSortTest {
    /**
     * 测试flightEntry
     * 用comparetor实现排序
     */
    @Test
    public void flightEntryTest() throws ParseException {
        FlightEntry flightEntry1 = FlightEntryFactory.getEntry(new SimpleLocation("A",true),new SimpleLocation("B",true),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"001");
        FlightEntry flightEntry2 = FlightEntryFactory.getEntry(new SimpleLocation("A",true),new SimpleLocation("B",true),new Timeslot("2020-05-16 15:30","2020-05-16 15:50"),"002");
        List<FlightEntry> flightEntries = new ArrayList<>();
        flightEntries.add(flightEntry2);
        flightEntries.add(flightEntry1);
        Collections.sort(flightEntries,new FlightComparator());
        assertEquals(flightEntry1,flightEntries.get(0));
        assertEquals(flightEntry2,flightEntries.get(1));
        Collections.sort(flightEntries,new ArrivalFlightComparator());
        assertEquals(flightEntry2,flightEntries.get(0));
        assertEquals(flightEntry1,flightEntries.get(1));
    }
    /**
     * 测试TrainEntry
     */
    @Test
    public void trainEntryTest() throws ParseException {
        List<Location> locations = new ArrayList<>();
        locations.add(new SimpleLocation("A",true));
        locations.add(new SimpleLocation("B",true));
        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(new Timeslot("2020-05-16 15:00","2020-05-16 16:00"));
        TrainEntry trainEntry1 = TrainEntryFactory.getEntry(timeslots,locations,"001");
        List<Timeslot> timeslots1 = new ArrayList<>();
        timeslots1.add(new Timeslot("2020-05-16 15:10","2020-05-16 15:40"));
        TrainEntry trainEntry2 = TrainEntryFactory.getEntry(timeslots1,locations,"001");
        List<TrainEntry> trainEntries = new ArrayList<>();
        trainEntries.add(trainEntry2);
        trainEntries.add(trainEntry1);
        Collections.sort(trainEntries,new TrainEntryComparator());
        assertEquals(trainEntry1,trainEntries.get(0));
        assertEquals(trainEntry2,trainEntries.get(1));
    }
    /**
     * 测试CourseEntry
     */
    @Test
    public void courseEntryTest() throws ParseException {
        CourseEntry courseEntry1 = CourseEntryFactory.getEntry(new SimpleLocation("A",false),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"math");
        CourseEntry courseEntry2 = CourseEntryFactory.getEntry(new SimpleLocation("B",false),new Timeslot("2020-05-16 15:05","2020-05-16 15:30"),"english");
        List<CourseEntry> courseEntries = new ArrayList<>();
        courseEntries.add(courseEntry2);
        courseEntries.add(courseEntry1);
        Collections.sort(courseEntries,new CourseEntryComparator());
        assertEquals(courseEntry1,courseEntries.get(0));
        assertEquals(courseEntry2,courseEntries.get(1));
    }
}
