package Resource;

import java.util.Objects;

public class Plane {
    /**
     * AF:将该类映射到一个飞机号为ID，飞机类型为Type，飞机座位数为numOfSeats，飞机机龄为old的一个飞机上
     * RI:numOfSeats>0,old>0
     * rep from exposure:用private关键字修饰rep
     */

    private String ID;
    private String type;
    private int numOfSeats;
    private double old;

    /**
     * 构造函数
     * @param ID 飞机ID
     * @param type 飞机类型
     * @param numOfSeats 飞机座位数
     * @param old 飞机大小
     */
    public Plane(String ID, String type, int numOfSeats, double old) {
        this.ID = ID;
        this.type = type;
        this.numOfSeats = numOfSeats;
        this.old = old;
        checkRep();
    }

    /**
     * 重写equals函数
     * @param o 比较对象
     * @return 若两个飞机的ID，type，numOfseats，old都分别相同则认为这两个飞机相同
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plane)) return false;
        Plane plane = (Plane) o;
        return numOfSeats == plane.numOfSeats &&
                Double.compare(plane.old, old) == 0 &&
                Objects.equals(ID, plane.ID) &&
                Objects.equals(type, plane.type);
    }

    /**
     * 重写hashCode函数
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(ID, type, numOfSeats, old);
    }

    /**
     * 重写toString函数
     * @return 返回飞机的相关信息
     */
    @Override
    public String toString() {
        return "Plane{" +
                "ID='" + ID + '\'' +
                '}';
    }

    /**
     * 获得飞机ID
     * @return 返回飞机ID
     */
    public String getID() {
        return ID;
    }

    /**
     * 获取飞机类型
     * @return 返回type
     */
    public String getType() {
        return type;
    }

    /**
     * 获取飞机座位数量
     * @return 返回飞机座位数量(numOfSeats>0)
     */
    public int getNumOfSeats() {
        return numOfSeats;
    }

    /**
     * 获取飞机机龄
     * @return 返回old (old>0)
     */
    public double getOld() {
        return old;
    }

    /**
     * 保证RI
     */
    private void checkRep()
    {
        if (numOfSeats<=0)
            assert false;
        if (old<=0)
            assert false;
    }
}
