import static org.junit.Assert.*;

import API.PlanningEntryAPIs;
import API.Strategy1;
import API.Strategy2;
import Entry.*;
import Location.Location;
import Location.SimpleLocation;
import Resource.*;
import Time.Timeslot;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class APItest {
    /**
     * 测试位置冲突
     * 测试策略：
     * 一组计划项中有位置冲突，没有位置冲突
     * 航班、高铁、课程三种计划项
     */
    @Test
    public void checkPositionConflict() throws ParseException {
        FlightEntry flightEntry1 = FlightEntryFactory.getEntry(new SimpleLocation("A",true),new SimpleLocation("B",true),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"001");
        FlightEntry flightEntry2 = FlightEntryFactory.getEntry(new SimpleLocation("A",true),new SimpleLocation("B",true),new Timeslot("2020-05-16 15:30","2020-05-16 15:50"),"002");
        List<FlightEntry> flightEntries = new ArrayList<>();
        flightEntries.add(flightEntry1);
        flightEntries.add(flightEntry2);
        assertFalse(PlanningEntryAPIs.checkLocationConflict(flightEntries));
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
        assertFalse(PlanningEntryAPIs.checkLocationConflict(trainEntries));
        CourseEntry courseEntry1 = CourseEntryFactory.getEntry(new SimpleLocation("A",false),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"math");
        CourseEntry courseEntry2 = CourseEntryFactory.getEntry(new SimpleLocation("B",false),new Timeslot("2020-05-16 15:05","2020-05-16 15:30"),"english");
        List<CourseEntry> courseEntries = new ArrayList<>();
        courseEntries.add(courseEntry2);
        courseEntries.add(courseEntry1);
        assertFalse(PlanningEntryAPIs.checkLocationConflict(courseEntries));
        courseEntries.remove(courseEntry2);
        CourseEntry courseEntry3 = CourseEntryFactory.getEntry(new SimpleLocation("A",false),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"art");
        courseEntries.add(courseEntry3);
        assertTrue(PlanningEntryAPIs.checkLocationConflict(courseEntries));
    }
    /**
     * 测试资源冲突
     * 测试策略同上
     */
    @Test
    public void checkResourceConflictTest() throws ParseException {
        FlightEntry flightEntry1 = FlightEntryFactory.getEntry(new SimpleLocation("A",true),new SimpleLocation("B",true),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"001");
        FlightEntry flightEntry2 = FlightEntryFactory.getEntry(new SimpleLocation("A",true),new SimpleLocation("B",true),new Timeslot("2020-05-16 15:30","2020-05-16 15:50"),"002");
        FlightEntry flightEntry3 = FlightEntryFactory.getEntry(new SimpleLocation("A",true),new SimpleLocation("B",true),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"003");
        flightEntry1.allocate(new SingleResourceImpl<>(new Plane("01","A",12,2)));
        flightEntry2.allocate(new SingleResourceImpl<>(new Plane("01","A",12,2)));
        flightEntry3.allocate(new SingleResourceImpl<>(new Plane("02","B",12,2)));
        List<FlightEntry> flightEntries = new ArrayList<>();
        flightEntries.add(flightEntry1);
        flightEntries.add(flightEntry2);
        assertTrue(PlanningEntryAPIs.checkResourceExclusiveConflict(flightEntries));
        flightEntries.remove(flightEntry1);
        flightEntries.add(flightEntry3);
        assertFalse(PlanningEntryAPIs.checkResourceExclusiveConflict(flightEntries));
        List<Location> locations = new ArrayList<>();
        locations.add(new SimpleLocation("A",true));
        locations.add(new SimpleLocation("B",true));
        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(new Timeslot("2020-05-16 15:00","2020-05-16 16:00"));
        TrainEntry trainEntry1 = TrainEntryFactory.getEntry(timeslots,locations,"001");
        List<Timeslot> timeslots1 = new ArrayList<>();
        timeslots1.add(new Timeslot("2020-05-16 15:10","2020-05-16 15:40"));
        TrainEntry trainEntry2 = TrainEntryFactory.getEntry(timeslots1,locations,"002");
        TrainEntry trainEntry3 = TrainEntryFactory.getEntry(timeslots1,locations,"003");
        List<TrainEntry> trainEntries = new ArrayList<>();
        trainEntries.add(trainEntry2);
        trainEntries.add(trainEntry1);
        List<Carriage> carriages1 = new ArrayList<>();
        carriages1.add(new Carriage("01","B",23,"2000"));
        List<Carriage> carriages2 = new ArrayList<>();
        carriages2.add(new Carriage("01","B",23,"2000"));
        List<Carriage> carriages3 = new ArrayList<>();
        carriages3.add(new Carriage("02","B",23,"2000"));
        trainEntry1.allocate(new MultipleSortedResourceEntryImpl<>(carriages1));
        trainEntry2.allocate(new MultipleSortedResourceEntryImpl<>(carriages2));
        trainEntry3.allocate(new MultipleSortedResourceEntryImpl<>(carriages3));
        assertTrue(PlanningEntryAPIs.checkResourceExclusiveConflict(trainEntries));
        trainEntries.remove(trainEntry1);
        trainEntries.add(trainEntry3);
        assertFalse(PlanningEntryAPIs.checkResourceExclusiveConflict(trainEntries));
        CourseEntry courseEntry1 = CourseEntryFactory.getEntry(new SimpleLocation("A",false),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"math");
        CourseEntry courseEntry2 = CourseEntryFactory.getEntry(new SimpleLocation("B",false),new Timeslot("2020-05-16 15:05","2020-05-16 15:30"),"english");
        CourseEntry courseEntry3 = CourseEntryFactory.getEntry(new SimpleLocation("A",false),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"art");
        Teacher teacher1 = new Teacher("01","a","man","doctor");
        Teacher teacher2 = new Teacher("01","a","man","doctor");
        Teacher teacher3 = new Teacher("03","a","man","doctor");
        courseEntry1.allocate(new SingleResourceImpl<>(teacher1));
        courseEntry2.allocate(new SingleResourceImpl<>(teacher2));
        courseEntry3.allocate(new SingleResourceImpl<>(teacher3));
        List<CourseEntry> courseEntries = new ArrayList<>();
        courseEntries.add(courseEntry2);
        courseEntries.add(courseEntry1);
        assertTrue(PlanningEntryAPIs.checkResourceExclusiveConflict(courseEntries));
        courseEntries.remove(courseEntry2);
        courseEntries.add(courseEntry3);
        assertFalse(PlanningEntryAPIs.checkResourceExclusiveConflict(courseEntries));
    }
    /**
     * 测试找前序项
     * 测试策略：
     * 用两种strategy对三种计划项分别进行求解
     */
    @Test
    public void findPreEntryTest() throws ParseException {
        PlanningEntryAPIs<Plane> planeAPIS = new PlanningEntryAPIs<Plane>();
        FlightEntry flightEntry1 = FlightEntryFactory.getEntry(new SimpleLocation("A",true),new SimpleLocation("B",true),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"001");
        FlightEntry flightEntry2 = FlightEntryFactory.getEntry(new SimpleLocation("A",true),new SimpleLocation("B",true),new Timeslot("2020-05-16 17:00","2020-05-16 18:00"),"002");
        FlightEntry flightEntry3 = FlightEntryFactory.getEntry(new SimpleLocation("A",true),new SimpleLocation("B",true),new Timeslot("2020-05-16 19:00","2020-05-16 20:00"),"003");
        flightEntry1.allocate(new SingleResourceImpl<>(new Plane("01","A",12,2)));
        flightEntry2.allocate(new SingleResourceImpl<>(new Plane("01","A",12,2)));
        flightEntry3.allocate(new SingleResourceImpl<>(new Plane("02","B",12,2)));
        List<FlightEntry> flightEntries = new ArrayList<>();
        flightEntries.add(flightEntry1);
        flightEntries.add(flightEntry2);
        assertEquals(flightEntry1,planeAPIS.findPreEntryPerResource(new Strategy1<Plane>(new Plane("01","A",12,2),flightEntry2,flightEntries)));
        assertEquals(flightEntry1,planeAPIS.findPreEntryPerResource(new Strategy2<Plane>(new Plane("01","A",12,2),flightEntry2,flightEntries)));
        flightEntries.remove(flightEntry1);
        flightEntries.add(flightEntry3);
        assertEquals(null,planeAPIS.findPreEntryPerResource(new Strategy2<Plane>(new Plane("01","A",12,2),flightEntry2,flightEntries)));
        assertEquals(null,planeAPIS.findPreEntryPerResource(new Strategy1<Plane>(new Plane("01","A",12,2),flightEntry2,flightEntries)));
        List<Location> locations = new ArrayList<>();
        locations.add(new SimpleLocation("A",true));
        locations.add(new SimpleLocation("B",true));
        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(new Timeslot("2020-05-16 15:00","2020-05-16 16:00"));
        TrainEntry trainEntry1 = TrainEntryFactory.getEntry(timeslots,locations,"001");
        List<Timeslot> timeslots1 = new ArrayList<>();
        timeslots1.add(new Timeslot("2020-05-16 17:00","2020-05-16 18:00"));
        TrainEntry trainEntry2 = TrainEntryFactory.getEntry(timeslots1,locations,"002");
        TrainEntry trainEntry3 = TrainEntryFactory.getEntry(timeslots,locations,"003");
        List<TrainEntry> trainEntries = new ArrayList<>();
        trainEntries.add(trainEntry2);
        trainEntries.add(trainEntry1);
        List<Carriage> carriages1 = new ArrayList<>();
        carriages1.add(new Carriage("01","B",23,"2000"));
        List<Carriage> carriages2 = new ArrayList<>();
        carriages2.add(new Carriage("01","B",23,"2000"));
        List<Carriage> carriages3 = new ArrayList<>();
        carriages3.add(new Carriage("02","B",23,"2000"));
        trainEntry1.allocate(new MultipleSortedResourceEntryImpl<>(carriages1));
        trainEntry2.allocate(new MultipleSortedResourceEntryImpl<>(carriages2));
        trainEntry3.allocate(new MultipleSortedResourceEntryImpl<>(carriages3));
        PlanningEntryAPIs<Carriage> carriagePlanningEntryAPIs = new PlanningEntryAPIs<Carriage>();
        assertEquals(trainEntry1,carriagePlanningEntryAPIs.findPreEntryPerResource(new Strategy1<Carriage>(new Carriage("01","B",23,"2000"),trainEntry2,trainEntries)));
        assertEquals(trainEntry1,carriagePlanningEntryAPIs.findPreEntryPerResource(new Strategy2<Carriage>(new Carriage("01","B",23,"2000"),trainEntry2,trainEntries)));
        trainEntries.remove(trainEntry1);
        trainEntries.add(trainEntry3);
        assertEquals(null,carriagePlanningEntryAPIs.findPreEntryPerResource(new Strategy1<Carriage>(new Carriage("01","B",23,"2000"),trainEntry2,trainEntries)));
        assertEquals(null,carriagePlanningEntryAPIs.findPreEntryPerResource(new Strategy2<Carriage>(new Carriage("01","B",23,"2000"),trainEntry2,trainEntries)));
        assertFalse(PlanningEntryAPIs.checkResourceExclusiveConflict(trainEntries));
        CourseEntry courseEntry1 = CourseEntryFactory.getEntry(new SimpleLocation("A",false),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"math");
        CourseEntry courseEntry2 = CourseEntryFactory.getEntry(new SimpleLocation("B",false),new Timeslot("2020-05-16 17:00","2020-05-16 18:00"),"english");
        CourseEntry courseEntry3 = CourseEntryFactory.getEntry(new SimpleLocation("A",false),new Timeslot("2020-05-16 19:00","2020-05-16 20:00"),"art");
        Teacher teacher1 = new Teacher("01","a","man","doctor");
        Teacher teacher2 = new Teacher("01","a","man","doctor");
        Teacher teacher3 = new Teacher("03","a","man","doctor");
        courseEntry1.allocate(new SingleResourceImpl<>(teacher1));
        courseEntry2.allocate(new SingleResourceImpl<>(teacher2));
        courseEntry3.allocate(new SingleResourceImpl<>(teacher3));
        List<CourseEntry> courseEntries = new ArrayList<>();
        courseEntries.add(courseEntry2);
        courseEntries.add(courseEntry1);
        PlanningEntryAPIs<Teacher> teacherPlanningEntryAPIs = new PlanningEntryAPIs<Teacher>();
        assertEquals(courseEntry1,teacherPlanningEntryAPIs.findPreEntryPerResource(new Strategy1<Teacher>(teacher2,courseEntry2,courseEntries)));
        assertEquals(courseEntry1,teacherPlanningEntryAPIs.findPreEntryPerResource(new Strategy2<Teacher>(teacher2,courseEntry2,courseEntries)));
        courseEntries.remove(courseEntry1);
        courseEntries.add(courseEntry3);
        assertEquals(null,teacherPlanningEntryAPIs.findPreEntryPerResource(new Strategy1<Teacher>(teacher2,courseEntry2,courseEntries)));
        assertEquals(null,teacherPlanningEntryAPIs.findPreEntryPerResource(new Strategy2<Teacher>(teacher2,courseEntry2,courseEntries)));
    }
}
