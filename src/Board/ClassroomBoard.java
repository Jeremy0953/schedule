package Board;

import Comparator.CourseEntryComparator;
import Entry.CourseEntry;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 教室公示牌
 */
public class ClassroomBoard implements Iterable<CourseEntry> {
    private LocalDateTime time;
    private String classroomName;
    private List<CourseEntry> courseEntryList = new ArrayList<>();
    JFrame jFrame;

    /**
     * 构造方法
     * @param time 当前时间
     * @param name 教室名
     * @param entries 所有该教室的上课计划项
     */
    public ClassroomBoard(LocalDateTime time,String name,List<CourseEntry> entries)
    {
        this.time = time;
        classroomName = name;
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0;i<entries.size();i++)
        {
            if (entries.get(i).getTimeList().get(0).getBeginTime().format(pattern).equals(time.format(pattern)))
                courseEntryList.add(entries.get(i));
        }
        Collections.sort(courseEntryList,new CourseEntryComparator());
    }

    /**
     * 可视化
     */
    public void visualize()
    {

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String localTime = time.format(pattern);
        jFrame = new JFrame(localTime+" "+classroomName);
        //jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        Object[] columnNames = {"time","course name","teacher","state"};
        Object[][] rowDate = new Object[courseEntryList.size()][4];
        for (int i = 0;i<courseEntryList.size();i++)
        {
            rowDate[i][0] = courseEntryList.get(i).getTimeList().get(0).getBeginTime().format(pattern)+"-"+courseEntryList.get(i).getTimeList().get(0).getEndTime().format(pattern);
            rowDate[i][1] = courseEntryList.get(i).getName();
            if (courseEntryList.get(i).getResource()!=null)
                rowDate[i][2] = courseEntryList.get(i).getResource().getName();
            else
                rowDate[i][2] = "";
            rowDate[i][3] = courseEntryList.get(i).getState();
        }
        JTable jTable = new JTable(rowDate,columnNames);
        panel.add(jTable.getTableHeader(),BorderLayout.NORTH);
        panel.add(jTable,BorderLayout.CENTER);
        jFrame.setContentPane(panel);
        jFrame.pack();
        jFrame.setSize(600,900);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    /**
     * 按上课时间迭代
     * @return 迭代器
     */
    @Override
    public Iterator<CourseEntry> iterator() {
        return courseEntryList.iterator();
    }
}
