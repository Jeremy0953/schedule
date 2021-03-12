import static org.junit.Assert.*;

import Entry.FlightEntry;
import Entry.FlightEntryFactory;
import Exceptions.CantCancelException;
import Location.SimpleLocation;
import Resource.SingleResourceImpl;
import State.*;
import Time.Timeslot;
import org.junit.Before;
import org.junit.Test;
import Resource.*;

import java.text.ParseException;

public class FlightEntryTest {
    FlightEntry flightEntry;
    @Before
    public void init() throws ParseException {
        flightEntry = FlightEntryFactory.getEntry(new SimpleLocation("A",true),new SimpleLocation("B",true),new Timeslot("2020-05-16 15:00","2020-05-16 16:00"),"001");
    }
    /**
     * 测试getLocation函数，与实际情况进行比较
     */
    @Test
    public void testGetLocation()
    {
        assertEquals(2,flightEntry.getLocation().size());
        assertEquals(new SimpleLocation("A",true),flightEntry.getLocation().get(0));
        assertEquals(new SimpleLocation("B",true),flightEntry.getLocation().get(1));
    }
    /**
     * 测试getResource函数，与先分配之后再进行比较
     */
    @Test
    public void testGetResource()
    {
        flightEntry.allocate(new SingleResourceImpl<>(new Plane("01","A",15,2)));
        assertEquals(new Plane("01","A",15,2),flightEntry.getResource());
        flightEntry.setResourceDelegation(new SingleResourceImpl<>(new Plane("02","B",15,2)));
        assertEquals(new Plane("02","B",15,2),flightEntry.getResource());
    }
    /**
     * 测试状态转换
     * 测试策略，
     * flightEntry的原始状态：waiting，allocated，running，ended，cancelled
     * 想要转移到的状态：waiting，allocated，running，ended，cancelled
     */
    @Test
    public void testState() throws ParseException {
        flightEntry.allocate(new SingleResourceImpl<>(new Plane("01","A",15,2)));
        assertTrue(flightEntry.getState() instanceof AllocatedState);
        init();
        flightEntry.run();
        assertTrue(flightEntry.getState() instanceof WaitingState);
        flightEntry.end();
        assertTrue(flightEntry.getState() instanceof WaitingState);
        try
        {
            flightEntry.cancel();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(flightEntry.getState() instanceof CancelledState);
        init();
        flightEntry.allocate(new SingleResourceImpl<>(new Plane("01","A",15,2)));
        flightEntry.allocate(new SingleResourceImpl<>(new Plane("02","A",15,2)));
        assertTrue(flightEntry.getState() instanceof AllocatedState);
        assertNotEquals(new Plane("02","A",15,2),flightEntry.getResource());
        flightEntry.run();
        assertTrue(flightEntry.getState() instanceof RunningState);
        init();
        flightEntry.allocate(new SingleResourceImpl<>(new Plane("01","A",15,2)));
        flightEntry.end();
        assertTrue(flightEntry.getState() instanceof AllocatedState);
        try
        {
            flightEntry.cancel();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(flightEntry.getState() instanceof CancelledState);
        init();
        flightEntry.allocate(new SingleResourceImpl<>(new Plane("01","A",15,2)));
        flightEntry.run();
        flightEntry.allocate(new SingleResourceImpl<>(new Plane("01","A",15,2)));
        assertTrue(flightEntry.getState() instanceof RunningState);
        try
        {
            flightEntry.cancel();
            fail();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(flightEntry.getState() instanceof RunningState);
        flightEntry.end();
        assertTrue(flightEntry.getState() instanceof EndedState);
        flightEntry.allocate(new SingleResourceImpl<>(new Plane("01","A",15,2)));
        assertTrue(flightEntry.getState() instanceof EndedState);
        flightEntry.run();
        assertTrue(flightEntry.getState() instanceof EndedState);
        flightEntry.end();
        assertTrue(flightEntry.getState() instanceof EndedState);
        try
        {
            flightEntry.cancel();
            fail();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(flightEntry.getState() instanceof EndedState);
        init();
        try
        {
            flightEntry.cancel();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(flightEntry.getState() instanceof CancelledState);
        flightEntry.allocate(new SingleResourceImpl<>(new Plane("01","A",15,2)));
        assertTrue(flightEntry.getState() instanceof CancelledState);
        flightEntry.run();
        assertTrue(flightEntry.getState() instanceof CancelledState);
        flightEntry.end();
        assertTrue(flightEntry.getState() instanceof CancelledState);
        try
        {
            flightEntry.cancel();
            fail();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(flightEntry.getState() instanceof CancelledState);
    }
}
