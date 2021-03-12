package Location;

import java.util.List;

/**
 * 多个Location组合的接口
 */
public interface MultipleLocationEntry extends LocationInterface{
    /**
     * 得到Location列表
     * @return Location的list
     */
    public List<? extends Location> getLocation();
}
