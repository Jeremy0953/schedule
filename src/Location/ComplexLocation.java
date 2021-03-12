package Location;

public class ComplexLocation extends ComplexLocationDecorator {
    /**
     * 带有经纬度的地点
     * AF：将该类映射到经纬度分别为longitude和latitude的Location上
     * RI:-180<=longitude<=180,-90<=latitude<=90
     * rep from exposure:利用private和final进行修饰
     */
    private final double longitude;
    private final double latitude;

    /**
     * 构造方法
     * @param location 对基础的Location类进行装饰
     * @param longitude 经度，要求在[-180，180]范围内
     * @param latitude 纬度，要求在[-90,90]范围内
     */
    public ComplexLocation(Location location, double longitude, double latitude) {
        super(location);
        this.longitude = longitude;
        this.latitude = latitude;
        checkRep();
    }

    /**
     * 获得经度
     * @return 返回经度
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * 获得纬度
     * @return 返回纬度
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * make sure RI = true;
     */
    private void checkRep()
    {
        if (!(longitude>=-180&&longitude<=180))
            assert false;
        if (!(latitude>=-90&&latitude<=90))
            assert false;
    }
}
