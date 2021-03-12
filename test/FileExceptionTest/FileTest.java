package FileExceptionTest;
import static org.junit.Assert.*;

import APP.FlightScheduleApp;
import org.junit.Before;
import org.junit.Test;
import Exceptions.*;

import java.io.IOException;

public class FileTest {
    FlightScheduleApp app;
    @Before
    public void init()
    {
        app = new FlightScheduleApp();
    }
    //测试第一行Flight标签格式错误
    @Test(expected = TagFormatException.class)
    public void test1() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/1.txt");
    }
    //测试第一行日期格式错误
    @Test(expected = TimeFormatException.class)
    public void test2() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/2.txt");
    }
    //测试第一行航班号格式错误
    @Test(expected = FlightIDFormatException.class)
    public void test3() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/3.txt");
    }
    //测试第二行括号错误
    @Test(expected = TagFormatException.class)
    public void test4() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/4.txt");
    }
    //测试第三行起飞地点标签格式错误
    @Test(expected = TagFormatException.class)
    public void test5() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/5.txt");
    }
    //测试第三行起飞地点不合法字符格式错误
    @Test(expected = IllegalWordException.class)
    public void test6() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/6.txt");
    }
    //测试第四行降落地点标签格式错误
    @Test(expected = TagFormatException.class)
    public void test7() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/7.txt");
    }
    //测试第四行降落地点非法字符格式错误
    @Test(expected = IllegalWordException.class)
    public void test8() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/8.txt");
    }
    //测试第五行起飞时间非法标签格式错误
    @Test(expected = TagFormatException.class)
    public void test9() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/9.txt");
    }
    //测试第五行起飞时间格式错误
    @Test(expected = TimeFormatException.class)
    public void test10() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/10.txt");
    }
    //测试第五行起飞时间和首行起飞时间不一致依赖错误
    @Test(expected = FlightDateNotConsistentDependenceException.class)
    public void test11() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/11.txt");
    }
    //测试第五行起飞时间格式错误
    @Test(expected = TimeFormatException.class)
    public void test12() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/12.txt");
    }
    //测试第六行抵达时间标签格式错误
    @Test(expected = TagFormatException.class)
    public void test13() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/13.txt");
    }
    //测试第六行抵达时间格式错误
    @Test(expected = TimeFormatException.class)
    public void test14() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/14.txt");
    }
    //测试第六行抵达时间格式错误
    @Test(expected = TimeFormatException.class)
    public void test15() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/15.txt");
    }
    //测试第六行抵达时间和起飞时间不符合关系错误
    @Test(expected = FlightArrivalTimeDependenceExpection.class)
    public void test16() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/16.txt");
    }
    //测试第七行飞机标签格式错误
    @Test(expected = TagFormatException.class)
    public void test17() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/17.txt");
    }
    //测试第七行飞机编号字母格式错误
    @Test(expected = PlaneIDWordFormatException.class)
    public void test18() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/18.txt");
    }
    //测试第七行飞机编号数字格式错误
    @Test(expected = PlaneIDNumberFormatExpection.class)
    public void test19() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/19.txt");
    }
    //测试第八行括号标签格式错误
    @Test(expected = TagFormatException.class)
    public void test20() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/20.txt");
    }
    //测试第九行飞机类型标签格式错误
    @Test(expected = TagFormatException.class)
    public void test21() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/21.txt");
    }
    //测试第九行飞机类型不合法字符格式错误
    @Test(expected = IllegalWordException.class)
    public void test22() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/22.txt");
    }
    //测试第十行座位标签格式错误
    @Test(expected = TagFormatException.class)
    public void test23() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/23.txt");
    }
    //测试第十行座位数非整数格式错误
    @Test(expected = NotAIntNumberFormatException.class)
    public void test24() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/24.txt");
    }
    //测试第十行座位数超出范围格式错误
    @Test(expected = NumberOutOfBoundaryFormatException.class)
    public void test25() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/25.txt");
    }
    //测试第十一行机龄标签格式错误
    @Test(expected = TagFormatException.class)
    public void test26() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/26.txt");
    }
    //测试第十一行机龄不是小数或整数格式错误
    @Test(expected = NumberFormatException.class)
    public void test27() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/27.txt");
    }
    //测试第十一行机龄不是一位小数格式错误
    @Test(expected = OneDecimalFormatException.class)
    public void test28() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/28.txt");
    }
    //测试第十一行机龄不在范围格式错误
    @Test(expected = NumberOutOfBoundaryFormatException.class)
    public void test29() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/29.txt");
    }
    //测试第十一行机龄以零开头格式错误
    @Test(expected = NumberFormatException.class)
    public void test30() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/30.txt");
    }
    //测试第十二行括号标签格式错误
    @Test(expected = TagFormatException.class)
    public void test31() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/31.txt");
    }
    //测试第十三行括号标签格式错误
    @Test(expected = TagFormatException.class)
    public void test32() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/32.txt");
    }
    //测试两航班标签完全相同错误
    @Test(expected = TagSameException.class)
    public void test33() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/33.txt");
    }
    //测试两相同航班起飞地址不同依赖错误
    @Test(expected = AirportDifferentDependenceException.class)
    public void test34() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/34.txt");
    }
    //测试两相同航班降落地址不同依赖错误
    @Test(expected = AirportDifferentDependenceException.class)
    public void test35() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/35.txt");
    }
    //测试两相同航班起飞时间不同依赖错误
    @Test(expected = TimeDifferentDependenceException.class)
    public void test36() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/36.txt");
    }
    //测试两相同航班降落时间不同依赖错误
    @Test(expected = TimeDifferentDependenceException.class)
    public void test37() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/37.txt");
    }
    //测试两相同飞机属性不同依赖错误
    @Test(expected = DifferentPlaneDependenceException.class)
    public void test38() throws OneDecimalFormatException, NotAIntNumberFormatException, DifferentPlaneDependenceException, FlightIDFormatException, PlaneIDWordFormatException, TimeFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, TagSameException, AirportDifferentDependenceException, IOException, PlaneIDNumberFormatExpection, NumberOutOfBoundaryFormatException, TimeDifferentDependenceException, FlightArrivalTimeDependenceExpection {
        app.readFromFile("test/FileExceptionTest/38.txt");
    }
}
