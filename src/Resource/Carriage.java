package Resource;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Carriage {
    /**
     * AF：将该类映射到一个车厢号为ID，车厢类型为type,座位数为seats，生产年份为year的车厢
     * RI：seats>0
     * rep from exposure: 有些rep使用immutable类型变量，然后mutable类型的用private，final修饰
     */
    private String ID;
    private String type;
    private int seats;
    private final Year year;

    /**
     * 构造函数
     * @param ID
     * @param type
     * @param seats
     * @param year Year类型
     */
    public Carriage(String ID, String type, int seats, Year year) {
        this.ID = ID;
        this.type = type;
        this.seats = seats;
        this.year = year;
        checkRep();
    }

    /**
     * 构造函数
     * @param ID
     * @param type
     * @param seats
     * @param year String类型
     */
    public Carriage(String ID, String type, int seats, String year) {
        this.ID = ID;
        this.type = type;
        this.seats = seats;
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy");
        this.year = Year.parse(year,pattern);
        checkRep();
    }

    /**
     * 获取车厢号
     * @return 车厢ID
     */
    public String getID() {
        return ID;
    }

    /**
     * equals函数,将ID作为车厢的唯一标识，车厢ID相同的视为相同车厢
     * @param o 车厢
     * @return 若ID相同返回true否则false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carriage)) return false;
        Carriage carriage = (Carriage) o;
        return Objects.equals(ID, carriage.ID);
    }

    /**
     * 重写hashCode
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    /**
     * 车厢信息
     * @return 返回字符串形式的车厢各种信息
     */
    @Override
    public String toString() {
        return "Resource.Resource.Carriage{" +
                "ID='" + ID + '\'' +
                ", type='" + type + '\'' +
                ", seats=" + seats +
                ", year=" + year +
                '}';
    }
    /**
     * 保证RI
     */
    private void checkRep()
    {
        if (seats<=0)
            assert false;
    }
}
