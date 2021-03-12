package Location;

import java.util.Objects;

/**
 * Location接口的基础实现
 * AF：将该类映射到地名为name，shareable可共享的地点
 * RI：name!=null
 * rep from exposure:用private和final关键字
 */
public class SimpleLocation implements Location{
    private final String name;
    private final boolean shareable;
    public SimpleLocation(String name, boolean shareable) {
        this.name = name;
        this.shareable = shareable;
        checkRep();
    }

    /**
     * 将地点信息转换为String
     * @return 返回地点信息字符串
     */
    @Override
    public String toString() {
        return "SimpleLocation{" +
                "name='" + name + '\'' +
                ", shareable=" + shareable +
                '}';
    }

    /**
     * 构造函数
     * @param location 要复制的location对象
     */
    public SimpleLocation(Location location)
    {
        this.name = location.getName();
        this.shareable = location.isShareable();
        checkRep();
    }

    /**
     * 重写equals方法
     * @param o 传入要比较的对象
     * @return 若地名相同就认为是同一个地点
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleLocation)) return false;
        SimpleLocation location = (SimpleLocation) o;
        return Objects.equals(name, location.name);
    }

    /**
     * 重写hashCode 函数
     * @return 返回hashCode值
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * 获得该地点地名
     * @return 地名
     */
    public String getName() {
        return name;
    }

    /**
     * 获取该地点可否共享的状态
     * @return 若可共享返回true否则返回false
     */
    public boolean isShareable() {
        return shareable;
    }
    /**
     * make sure RI = true
     */
    private void checkRep()
    {
        if (name==null)
            assert false;
    }
}
