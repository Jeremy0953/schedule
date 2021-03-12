import Entry.TrainEntry;
import Entry.TrainEntryFactory;
import Location.Location;
import Resource.Carriage;
import Resource.MultipleSortedResourceEntryImpl;
import State.AllocatedState;
import State.BlockedState;
import State.CancelledState;
import State.RunningState;
import Time.Timeslot;
import org.junit.Before;
import org.junit.Test;
import Location.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BlockedStateTest {
    public TrainEntry trainEntry;
    public BlockedState blockedState = new BlockedState();
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
        trainEntry.run();
        trainEntry.block();
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
        assertTrue(trainEntry.getState() instanceof BlockedState);
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
        blockedState.run(trainEntry);
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
        blockedState.block(trainEntry);
        assertTrue(trainEntry.getState() instanceof BlockedState);
    }
    /**
     * 测试cancel方法
     * 测试策略
     * 查看调用cancel方法后状态
     */
    @Test
    public void cancelTest()
    {
        blockedState.cancel(trainEntry);
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
        blockedState.end(trainEntry);
        assertTrue(trainEntry.getState() instanceof BlockedState);
    }
}
