import Location.Location;
import org.junit.Test;
import Location.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LocationTest {
    /**
     * 新建SimpleLocation对象和ComplexLocation对象并对其中的get方法进行测试,以及对location组合类进行测试
     */
    @Test
    public void test()
    {
        Location location1 = new SimpleLocation("A",true);
        assertEquals("A",location1.getName());
        assertTrue(location1.isShareable());
        ComplexLocation location2 = new ComplexLocation(location1,15,25);
        assertEquals(15,location2.getLongitude(),0.01);
        assertEquals(25,location2.getLatitude(),0.01);
        SingleChangeableLocationImpl singleChangeableLocation = new SingleChangeableLocationImpl(location1);
        assertEquals(location1,singleChangeableLocation.getLocation());
        Location location3 = new SimpleLocation("B",true);
        List<Location> locations = new ArrayList<>();
        locations.add(location1);
        locations.add(location3);
        MultipleLocationEntryImpl multipleLocationEntry = new MultipleLocationEntryImpl(locations);
        assertEquals(2,multipleLocationEntry.getLocation().size());
        for(int i = 0;i<locations.size();i++)
        {
            assertEquals(locations.get(i),multipleLocationEntry.getLocation().get(i));
        }
    }
}
