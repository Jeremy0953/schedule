import static org.junit.Assert.*;

import Entry.FlightEntryFactory;
import Entry.TrainEntry;
import Entry.TrainEntryFactory;
import Exceptions.CantCancelException;
import Location.Location;
import Location.SimpleLocation;
import Resource.Carriage;
import Resource.MultipleSortedResourceEntryImpl;
import Resource.Plane;
import Resource.SingleResourceImpl;
import State.*;
import Time.Timeslot;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TrainEntryTest {
    TrainEntry trainEntry;
    @Before
    public void init() throws ParseException {
        List<Location> locations = new ArrayList<>();
        locations.add(new SimpleLocation("A",true));
        locations.add(new SimpleLocation("B",true));
        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(new Timeslot("2020-05-16 15:00","2020-05-16 16:00"));
        trainEntry = TrainEntryFactory.getEntry(timeslots,locations,"001");
    }
    /**
     * 测试getLocation函数，与实际情况进行比较
     */
    @Test
    public void testGetLocation()
    {
        assertEquals(2, trainEntry.getLocation().size());
        assertEquals(new SimpleLocation("A",true), trainEntry.getLocation().get(0));
        assertEquals(new SimpleLocation("B",true), trainEntry.getLocation().get(1));
    }
    /**
     * 测试getResource函数，与先分配之后再进行比较
     */
    @Test
    public void testGetResource()
    {
        List<Carriage> carriages = new ArrayList<>();
        carriages.add(new Carriage("01","A",200,"2000"));
        carriages.add(new Carriage("02","A",200,"2000"));
        trainEntry.allocate(new MultipleSortedResourceEntryImpl<>(carriages));
        assertEquals(new Carriage("01","A",200,"2000"), trainEntry.getResource().get(0));
        assertEquals(new Carriage("02","A",200,"2000"), trainEntry.getResource().get(1));
        trainEntry.setResourceDelegation(new MultipleSortedResourceEntryImpl<>(carriages));
        assertEquals(new Carriage("01","A",200,"2000"), trainEntry.getResource().get(0));
        assertEquals(new Carriage("02","A",200,"2000"), trainEntry.getResource().get(1));
    }
    /**
     * 测试状态转换
     * 测试策略，
     * trainEntry的原始状态：waiting，allocated，running，ended，cancelled
     * 想要转移到的状态：waiting，allocated，running，ended，cancelled
     */
    @Test
    public void testState() throws ParseException {
        List<Carriage> carriages = new ArrayList<>();
        carriages.add(new Carriage("01","A",200,"2000"));
        carriages.add(new Carriage("02","A",200,"2000"));
        trainEntry.allocate(new MultipleSortedResourceEntryImpl<>(carriages));
        assertTrue(trainEntry.getState() instanceof AllocatedState);
        init();
        trainEntry.run();
        assertTrue(trainEntry.getState() instanceof WaitingState);
        trainEntry.end();
        assertTrue(trainEntry.getState() instanceof WaitingState);
        trainEntry.block();
        assertTrue(trainEntry.getState() instanceof WaitingState);
        try {
            trainEntry.cancel();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(trainEntry.getState() instanceof CancelledState);
        init();
        trainEntry.allocate(new MultipleSortedResourceEntryImpl<>(carriages));
        assertTrue(trainEntry.getState() instanceof AllocatedState);
        trainEntry.block();
        assertTrue(trainEntry.getState() instanceof AllocatedState);
        trainEntry.run();
        assertTrue(trainEntry.getState() instanceof RunningState);
        init();
        trainEntry.allocate(new MultipleSortedResourceEntryImpl<>(carriages));
        trainEntry.end();
        assertTrue(trainEntry.getState() instanceof AllocatedState);
        trainEntry.block();
        assertTrue(trainEntry.getState() instanceof AllocatedState);
        try {
            trainEntry.cancel();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(trainEntry.getState() instanceof CancelledState);
        init();
        trainEntry.allocate(new MultipleSortedResourceEntryImpl<>(carriages));
        trainEntry.run();
        trainEntry.allocate(new MultipleSortedResourceEntryImpl<>(carriages));
        assertTrue(trainEntry.getState() instanceof RunningState);
        try {
            trainEntry.cancel();
            fail();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(trainEntry.getState() instanceof RunningState);
        trainEntry.block();
        assertTrue(trainEntry.getState() instanceof BlockedState);
        trainEntry.run();
        assertTrue(trainEntry.getState() instanceof RunningState);
        trainEntry.end();
        assertTrue(trainEntry.getState() instanceof EndedState);
        trainEntry.allocate(new MultipleSortedResourceEntryImpl<>(carriages));
        assertTrue(trainEntry.getState() instanceof EndedState);
        trainEntry.run();
        assertTrue(trainEntry.getState() instanceof EndedState);
        trainEntry.end();
        assertTrue(trainEntry.getState() instanceof EndedState);
        try {
            trainEntry.cancel();
            fail();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(trainEntry.getState() instanceof EndedState);
        trainEntry.block();
        assertTrue(trainEntry.getState() instanceof EndedState);
        init();
        try {
            trainEntry.cancel();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(trainEntry.getState() instanceof CancelledState);
        trainEntry.allocate(new SingleResourceImpl<>(new Plane("01","A",15,2)));
        assertTrue(trainEntry.getState() instanceof CancelledState);
        trainEntry.run();
        assertTrue(trainEntry.getState() instanceof CancelledState);
        trainEntry.end();
        assertTrue(trainEntry.getState() instanceof CancelledState);
        try {
            trainEntry.cancel();
            fail();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(trainEntry.getState() instanceof CancelledState);
        trainEntry.block();
        assertTrue(trainEntry.getState() instanceof CancelledState);
        init();
        trainEntry.allocate(new MultipleSortedResourceEntryImpl<>(carriages));
        trainEntry.run();
        trainEntry.block();
        trainEntry.allocate(new MultipleSortedResourceEntryImpl<>(carriages));
        assertTrue(trainEntry.getState() instanceof BlockedState);
        trainEntry.end();
        assertTrue(trainEntry.getState() instanceof BlockedState);
        trainEntry.run();
        assertTrue(trainEntry.getState() instanceof RunningState);
        trainEntry.block();
        try {
            trainEntry.cancel();
        }
        catch (CantCancelException e)
        {
            System.out.println(e.getMessage());
        }
        assertTrue(trainEntry.getState() instanceof CancelledState);
    }
}
