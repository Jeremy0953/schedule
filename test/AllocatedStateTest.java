import static org.junit.Assert.*;

import Entry.TrainEntry;
import Entry.TrainEntryFactory;
import Location.Location;
import Resource.Carriage;
import Resource.MultipleSortedResourceEntryImpl;
import State.RunningState;
import org.junit.Test;
import Location.SimpleLocation;
import State.AllocatedState;
import State.CancelledState;
import State.WaitingState;
import Time.Timeslot;
import org.junit.Before;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AllocatedStateTest {
    public TrainEntry trainEntry;
    public AllocatedState allocatedState = new AllocatedState();
    /**
     * 每次测试前初始化
     * @throws ParseException
     */
    @Before
    public void init() throws ParseException {
        List<Location> locations = new ArrayList<>();
        locations.add(new SimpleLocation("A",true));
        locations.add(new SimpleLocation("B",true));
        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(new Timeslot("2020-05-02 16:00","2020-05-02 17:00"));
        trainEntry = TrainEntryFactory.getEntry(timeslots,locations,"001");
        List<Carriage> carriages = new ArrayList<>();
        carriages.add(new Carriage("01","high",200,"2000"));
        carriages.add(new Carriage("02","high",200,"1999"));
        trainEntry.allocate(new MultipleSortedResourceEntryImpl(carriages));
    }
    /**
     * 测试allocate方法
     * 测试策略
     * 查看调用allocate方法后状态
     */
    @Test
    public void testAllocate()
    {
        List<Carriage> carriages = new ArrayList<>();
        carriages.add(new Carriage("03","high",200,"2000"));
        carriages.add(new Carriage("02","high",200,"1999"));
        trainEntry.allocate(new MultipleSortedResourceEntryImpl(carriages));
        assertTrue(trainEntry.getState() instanceof AllocatedState);
        assertNotEquals(carriages.get(0), trainEntry.getResource().get(0));
    }
    /**
     * 测试run方法
     * 测试策略
     * 查看调用run方法之后状态
     */
    @Test
    public void runTest()
    {
        allocatedState.run(trainEntry);
        assertTrue(trainEntry.getState() instanceof RunningState);
    }
    /**
     * 测试block方法
     * 测试策略
     * 查看调用block后状态
     */
    @Test
    public void blockTest()
    {
        allocatedState.block(trainEntry);
        assertTrue(trainEntry.getState() instanceof AllocatedState);
    }
    /**
     * 测试cancel方法
     * 测试策略
     * 查看调用cancel方法后状态
     */
    @Test
    public void cancelTest()
    {
        allocatedState.cancel(trainEntry);
        assertTrue(trainEntry.getState() instanceof CancelledState);
    }
    /**
     * 测试end方法
     * 测试策略
     * 查看调用end方法后状态
     */
    @Test
    public void endTest()
    {
        allocatedState.end(trainEntry);
        assertTrue(trainEntry.getState() instanceof AllocatedState);
    }
}
