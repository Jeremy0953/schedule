package Location;

public abstract class ComplexLocationDecorator implements Location{
    /**
     * Decorator模式，复杂位置类的装饰器，对简单的Location进行装饰
     */
    private final Location location;

    /**
     * 构造函数
     * @param location 要被装饰的Location类
     */
    public ComplexLocationDecorator(Location location) {
        this.location = location;
    }

    /**
     * 或者名字 委派给location实现
     * @return location的name
     */
    @Override
    public String getName() {
        return location.getName();
    }

    /**
     * 返回是否可共享
     * @return 若可共享返回true，否则false，委派给被装饰的location实现
     */
    @Override
    public boolean isShareable() {
        return location.isShareable();
    }
}
