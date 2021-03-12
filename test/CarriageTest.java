import static org.junit.Assert.*;

import Resource.Carriage;
import org.junit.Test;
public class CarriageTest {
    /**
     * 测试策略
     * 新建一个Carriage对象并且测试get函数
     */
    @Test
    public void test()
    {
        Carriage carriage = new Carriage("01","A",200,"2000");
        assertEquals("01",carriage.getID());
    }
}
