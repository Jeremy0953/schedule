import static org.junit.Assert.*;

import Resource.Plane;
import org.junit.Test;
public class PlaneTest {
    /**
     * 测试策略，新建Plane对象并对各个get方法进行测试
     */
    @Test
    public void test()
    {
        Plane plane = new Plane("01","747",150,2.5);
        assertEquals("01",plane.getID());
        assertEquals("747",plane.getType());
        assertEquals(150,plane.getNumOfSeats());
        assertEquals(2.5,plane.getOld(),0.01);
    }
}
