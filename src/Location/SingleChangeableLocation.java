package Location;

/**
 * 单个可改动地点的接口
 */
public interface SingleChangeableLocation extends LocationInterface{
    /**
     * 获得地点
     * @return 获得该地点Location
     */
    public Location getLocation();

    /**
     * 设置Location
     * @param locaiton 要设置为的Location
     */
    public void setLocaiton(Location locaiton);
}
