package Location;

/**
 * 单个地点
 * AF：映射为只有一个location的地点组合
 * RI：location！=null
 * rep from exposure：利用private关键字，而且rep为immutable类型的变量
 */
public class SingleChangeableLocationImpl implements SingleChangeableLocation {
    private Location location;

    /**
     * 返回地点
     * @return 返回地点
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     * 设置地点
     * @param locaiton 要设置为的Location
     */
    @Override
    public void setLocaiton(Location locaiton) {
        this.location = locaiton;
        checkRep();
    }

    /**
     * 构造方法
     * @param location 要设置为的location
     */
    public SingleChangeableLocationImpl(Location location) {
        this.location = location;
        checkRep();
    }

    /**
     * make sure RI = true
     */
    private void checkRep()
    {
        if (location==null)
            assert false;
    }
}
