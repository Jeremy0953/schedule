import static org.junit.Assert.*;

import Resource.Teacher;
import org.junit.Test;
public class TeacherTest {
    /**
     * 测试策略：新建Teacher对象并且测试各个get函数
     */
    @Test
    public void test()
    {
        Teacher teacher = new Teacher("001","yang","man","doctor");
        assertEquals("001",teacher.getID());
        assertEquals("yang",teacher.getName());
    }
}
