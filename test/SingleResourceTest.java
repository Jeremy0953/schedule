import static org.junit.Assert.*;

import Resource.Plane;
import Resource.SingleResource;
import Resource.SingleResourceImpl;
import org.junit.Test;
public class SingleResourceTest {
    /**
     * 测试策略：
     * 新建SingleResource对象并对get函数进行测试
     */
    @Test
    public void test()
    {
        SingleResource<Plane> planeSingleResource = new SingleResourceImpl<>(new Plane("01","747",200,2));
        assertEquals(new Plane("01","747",200,2),planeSingleResource.getResource());
    }
}
