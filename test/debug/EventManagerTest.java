package debug;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

public class EventManagerTest {

    //每次测试完清空数据
    @After
    public void after()
    {
        EventManager.temp.clear();
    }
    //检查当day相同时不同时间对的k重叠情况
    @Test
    public void test_oneday() {
        assertEquals(1,EventManager.book(1, 10, 20)); 	// returns 1
        assertEquals(1,EventManager.book(1, 1, 7)); 	// returns 1
        assertEquals(2,EventManager.book(1, 10, 22)); 	// returns 2
        assertEquals(3,EventManager.book(1, 5, 15)); 	// returns 3
        assertEquals(4,EventManager.book(1, 5, 12)); 	// returns 4
        assertEquals(4,EventManager.book(1, 7, 10)); 	// returns 4
    }

    //检查所有时间对都属于完全包含情况下的k重叠情况
    @Test
    public void test_inside() {
        assertEquals(1,EventManager.book(1, 10, 20)); 	// returns 1
        assertEquals(1,EventManager.book(1, 1, 7)); 	// returns 1
        assertEquals(2,EventManager.book(1, 2, 5)); 	// returns 2
        assertEquals(3,EventManager.book(1, 2, 4)); 	// returns 3
        assertEquals(4,EventManager.book(1, 3, 4)); 	// returns 4
    }

    //检查没有重叠时k重叠情况
    @Test
    public void test_outside() {
        assertEquals(1,EventManager.book(1, 5, 10)); 	// returns 1
        assertEquals(1,EventManager.book(1, 1, 5)); 	// returns 1
        assertEquals(1,EventManager.book(1, 10, 15)); 	// returns 1
        assertEquals(1,EventManager.book(1, 15, 20)); 	// returns 1
        assertEquals(1,EventManager.book(1, 20, 24)); 	// returns 1
    }

    //检查day不同时，开始结束小时相同、开始结束小时不同、的k重叠情况
    @Test
    public void test_differentdays() {
        assertEquals(1,EventManager.book(1, 10, 20)); 	// returns 1
        assertEquals(1,EventManager.book(2, 10, 20)); 	// returns 1
        assertEquals(1,EventManager.book(1, 1, 7)); 	// returns 1
        assertEquals(2,EventManager.book(2, 10, 15)); 	// returns 2
        assertEquals(2,EventManager.book(3, 10, 22)); 	// returns 2
        assertEquals(2,EventManager.book(1, 10, 15)); 	// returns 2
        assertEquals(3,EventManager.book(1, 5, 15)); 	// returns 3
        assertEquals(3,EventManager.book(3, 5, 15)); 	// returns 3
        assertEquals(4,EventManager.book(1, 7, 11)); 	// returns 4
    }

    //检查两个活动同一时间情况下的k重叠情况
    @Test
    public void test_cross() {
        assertEquals(1,EventManager.book(1, 10, 20)); 	// returns 1
        assertEquals(1,EventManager.book(1, 1, 7)); 	// returns 1
        assertEquals(2,EventManager.book(1, 10, 20)); 	// returns 2
        assertEquals(3,EventManager.book(1, 15, 16)); 	// returns 3
        assertEquals(4,EventManager.book(1, 7, 20)); 	// returns 4
    }
}
