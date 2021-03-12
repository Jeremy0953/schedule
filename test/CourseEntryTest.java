import static org.junit.Assert.*;

import Entry.CourseEntry;
import Entry.CourseEntryFactory;
import Exceptions.CantCancelException;
import Location.SimpleLocation;
import Resource.SingleResourceImpl;
import Resource.Teacher;
import State.*;
import Time.Timeslot;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

public class CourseEntryTest {
    CourseEntry courseEntry;
    @Before
    public void init() throws ParseException {
        courseEntry = CourseEntryFactory.getEntry(new SimpleLocation("A",false),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"001");
    }
    /**
     * 测试getLocation函数，与实际情况进行比较
     */
    @Test
    public void testGetLocation()
    {
        assertEquals(new SimpleLocation("A",true), courseEntry.getLocation());
    }
    /**
     * 测试getResource函数，与先分配之后再进行比较
     */
    @Test
    public void testGetResource()
    {
        courseEntry.allocate(new SingleResourceImpl<>(new Teacher("01","A","man","doctor")));
        assertEquals(new Teacher("01","A","man","doctor"), courseEntry.getResource());
        courseEntry.setResourceDelegation(new SingleResourceImpl<>(new Teacher("02","A","man","doctor")));
        assertEquals(new Teacher("02","A","man","doctor"), courseEntry.getResource());
    }
    /**
     * 测试状态转换
     * 测试策略，
     * flightEntry的原始状态：waiting，allocated，running，ended，cancelled
     * 想要转移到的状态：waiting，allocated，running，ended，cancelled
     */
    @Test
    public void testState() throws ParseException {
        courseEntry.allocate(new SingleResourceImpl<>(new Teacher("01","A","man","doctor")));
        assertTrue(courseEntry.getState() instanceof AllocatedState);
        init();
        courseEntry.run();
        assertTrue(courseEntry.getState() instanceof WaitingState);
        courseEntry.end();
        assertTrue(courseEntry.getState() instanceof WaitingState);
        try {
            courseEntry.cancel();
        }
        catch (CantCancelException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(courseEntry.getState() instanceof CancelledState);
        init();
        courseEntry.allocate(new SingleResourceImpl<>(new Teacher("01","A","man","doctor")));
        courseEntry.allocate(new SingleResourceImpl<>(new Teacher("02","A","man","doctor")));
        assertTrue(courseEntry.getState() instanceof AllocatedState);
        assertNotEquals(new Teacher("02","A","man","doctor"), courseEntry.getResource());
        courseEntry.run();
        assertTrue(courseEntry.getState() instanceof RunningState);
        init();
        courseEntry.allocate(new SingleResourceImpl<>(new Teacher("02","A","man","doctor")));
        courseEntry.end();
        assertTrue(courseEntry.getState() instanceof AllocatedState);
        try {
            courseEntry.cancel();
        }
        catch (CantCancelException e) { System.out.println(e.getMessage());}
        assertTrue(courseEntry.getState() instanceof CancelledState);
        init();
        courseEntry.allocate(new SingleResourceImpl<>(new Teacher("02","A","man","doctor")));
        courseEntry.run();
        courseEntry.allocate(new SingleResourceImpl<>(new Teacher("02","A","man","doctor")));
        assertTrue(courseEntry.getState() instanceof RunningState);
        try {
            courseEntry.cancel();
            fail();
        }
        catch (CantCancelException e) { System.out.println(e.getMessage());}
        assertTrue(courseEntry.getState() instanceof RunningState);
        courseEntry.end();
        assertTrue(courseEntry.getState() instanceof EndedState);
        courseEntry.allocate(new SingleResourceImpl<>(new Teacher("02","A","man","doctor")));
        assertTrue(courseEntry.getState() instanceof EndedState);
        courseEntry.run();
        assertTrue(courseEntry.getState() instanceof EndedState);
        courseEntry.end();
        assertTrue(courseEntry.getState() instanceof EndedState);
        try {
            courseEntry.cancel();
            fail();
        }
        catch (CantCancelException e) {System.out.println(e.getMessage()); }
        assertTrue(courseEntry.getState() instanceof EndedState);
        init();
        try {
            courseEntry.cancel();
        }
        catch (CantCancelException e) {System.out.println(e.getMessage()); }
        assertTrue(courseEntry.getState() instanceof CancelledState);
        courseEntry.allocate(new SingleResourceImpl<>(new Teacher("02","A","man","doctor")));
        assertTrue(courseEntry.getState() instanceof CancelledState);
        courseEntry.run();
        assertTrue(courseEntry.getState() instanceof CancelledState);
        courseEntry.end();
        assertTrue(courseEntry.getState() instanceof CancelledState);
        try {
            courseEntry.cancel();
            fail();
        }
        catch (CantCancelException e) { System.out.println(e.getMessage());}
        assertTrue(courseEntry.getState() instanceof CancelledState);
    }
}
