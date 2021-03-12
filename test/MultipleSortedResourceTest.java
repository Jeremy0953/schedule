import static org.junit.Assert.*;

import Resource.MultipleSortedResourceEntryImpl;
import Resource.Plane;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MultipleSortedResourceTest {
    /**
     * 测试策略
     * size = 0,1,>1
     */
    @Test
    public void test()
    {
        List<Plane> planes = new ArrayList<>();
        MultipleSortedResourceEntryImpl<Plane> resources= new MultipleSortedResourceEntryImpl<>(planes);
        assertEquals(0,resources.getResource().size());
        //size == 0
        planes.add(new Plane("01","A",1,1));
        resources = new MultipleSortedResourceEntryImpl<>(planes);
        assertEquals(1,resources.getResource().size());
        assertEquals(new Plane("01","A",1,1),resources.getResource().get(0));
        //size == 1
        planes.add(new Plane("02","B",1,1));
        resources = new MultipleSortedResourceEntryImpl<>(planes);
        assertEquals(2,resources.getResource().size());
        for (int i = 0;i<planes.size();i++)
        {
            assertEquals(planes.get(i),resources.getResource().get(i));
        }
        //size == 2
    }
}
