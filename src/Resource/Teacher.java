package Resource;

import java.util.Objects;

/**
 * 老师资源
 * AF：将该类映射到一个身份证为ID，名字为name，行别为Gender，职称为title的教师
 * RI：四个字符串均不为空
 * rep from exposure:均使用private修饰
 */
public class Teacher {
    private String ID;
    private String name;
    private String Gender;
    private String title;

    /**
     * 构造方法
     * @param ID 身份证
     * @param name 姓名
     * @param gender 性别
     * @param title 职称
     */
    public Teacher(String ID, String name, String gender, String title) {
        this.ID = ID;
        this.name = name;
        Gender = gender;
        this.title = title;
    }

    /**
     * 相等函数
     * @param o 要比较对象
     * @return 认为所有rep均相同Teacher才相同
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(ID, teacher.ID) &&
                Objects.equals(name, teacher.name) &&
                Objects.equals(Gender, teacher.Gender) &&
                Objects.equals(title, teacher.title);
    }

    /**
     * 重写hashCode函数
     * @return 返回hashCode值
     */
    @Override
    public int hashCode() {
        return Objects.hash(ID, name, Gender, title);
    }

    /**
     * 重写
     * @return 返回该教师信息的String
     */
    @Override
    public String toString() {
        return "Teacher{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", Gender='" + Gender + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    /**
     * 获得名字
     * @return 返回教师姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 获得教师身份证号
     * @return ID
     */
    public String getID() {
        return ID;
    }
    /**
     * make sure RI = true;
     */
    private void checkRep()
    {
        if (ID==null)
            assert false;
        if (name==null)
            assert false;
        if (Gender==null)
            assert false;
        if (title==null)
            assert false;
    }
}
