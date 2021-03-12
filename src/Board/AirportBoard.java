package Board;

import Comparator.FlightComparator;
import Entry.FlightEntry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;

/**
 * 机场显示牌
 */
public class AirportBoard implements Iterable<FlightEntry>{
    private LocalDateTime time;
    private String airportName;
    private Map<FlightEntry,LocalDateTime> arrivalMap = new HashMap<>();
    private Map<FlightEntry,LocalDateTime> departMap = new HashMap<>();
    private JFrame jFrame;
    /*public AirportBoard(LocalDateTime time, String airportName, PlanningEntryCollection<FlightEntry> arrivalList, PlanningEntryCollection<FlightEntry> departList) {
        this.time = time;
        this.airportName = airportName;
        for (int i = 0;i<arrivalList.size();i++)
        {
            Duration duration = Duration.between(arrivalList.get(i).getTimeList().get(0).getEndTime(),time);
            if (duration.toHours()>=-1&&duration.toHours()<=1)
            {
                this.arrivalList.add(arrivalList.get(i));
            }
        }
        for (int i = 0;i<departList.size();i++)
        {
            Duration duration = Duration.between(departList.get(i).getTimeList().get(0).getBeginTime(),time);
            if (duration.toHours()>=-1&&duration.toHours()<=1)
            {
                this.departList.add(departList.get(i));
            }
        }
        Collections.sort(this.arrivalList,new ArrivalFlightComparator());
        Collections.sort(this.departList,new FlightComparator());
    }*/

    /**
     * 构造方法
     * @param time 当前时间
     * @param airportName 机场名字
     * @param arrival 到达航班以及该航班到达时间
     * @param depart  出发航班及其时间
     */
    public AirportBoard(LocalDateTime time, String airportName, Map<FlightEntry, LocalDateTime> arrival, Map<FlightEntry, LocalDateTime> depart) {
        this.time = time;
        this.airportName = airportName;
        for(Map.Entry<FlightEntry,LocalDateTime> entry:arrival.entrySet())
        {
            Duration duration = Duration.between(time,entry.getValue());
            if(duration.toHours()<=1&&duration.toHours()>=-1)
            {
                arrivalMap.put(entry.getKey(),entry.getValue());
            }
        }
        for(Map.Entry<FlightEntry,LocalDateTime> entry:depart.entrySet())
        {
            Duration duration = Duration.between(time,entry.getValue());
            if(duration.toHours()<=1&&duration.toHours()>=-1)
            {
                departMap.put(entry.getKey(),entry.getValue());
            }
        }
    }
    /*public void visualize()
    {

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String localTime = time.format(pattern);
        jFrame = new JFrame(localTime+" "+airportName);
        //jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Object[] columnNames1 = {"planned arrival time","flight ID","air route","state"};
        Object[][] rowDate1 = new Object[arrivalList.size()][4];
        for (int i = 0;i<arrivalList.size();i++)
        {
            rowDate1[i][0] = arrivalList.get(i).getTimeList().get(0).getEndTime().format(pattern);
            rowDate1[i][1] = arrivalList.get(i).getName();
            rowDate1[i][2] = arrivalList.get(i).getLocation().get(0).getName()+"-"+arrivalList.get(i).getLocation().get(1).getName();
            rowDate1[i][3] = arrivalList.get(i).getState();
        }
        JTable arrivalTable = new JTable(rowDate1,columnNames1);
        Object[] columnNames2 = {"planned depart time","flight ID","air route","state"};
        Object[][] rowDate2 = new Object[departList.size()][4];
        for (int i = 0;i<departList.size();i++)
        {
            rowDate2[i][0] = departList.get(i).getTimeList().get(0).getBeginTime().format(pattern);
            rowDate2[i][1] = departList.get(i).getName();
            rowDate2[i][2] = departList.get(i).getLocation().get(0).getName()+"-"+departList.get(i).getLocation().get(1).getName();
            rowDate2[i][3] = departList.get(i).getState();
        }
        JTable departTable = new JTable(rowDate2,columnNames2);
        Box vBox = Box.createVerticalBox();
        vBox.add(arrivalTable.getTableHeader());
        vBox.add(arrivalTable);
        vBox.add(departTable.getTableHeader());
        vBox.add(departTable);
        jFrame.setContentPane(vBox);
        jFrame.pack();
        jFrame.setSize(600,900);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }*/

    /**
     * 可视化
     */
    public void visualize()
    {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String localTime = time.format(pattern);
        jFrame = new JFrame(localTime+" "+airportName);
        //jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Object[] columnNames1 = {"planned arrival time","flight ID","airway","state"};
        Object[][] rowDate1 = new Object[arrivalMap.size()][4];
        int i = 0;
        for (Map.Entry<FlightEntry,LocalDateTime> entry:arrivalMap.entrySet())
        {
            rowDate1[i][0] = entry.getValue();
            rowDate1[i][1] = entry.getKey().getName();
            rowDate1[i][2] = entry.getKey().getLocation().get(0).getName()+"-"+entry.getKey().getLocation().get(entry.getKey().getLocation().size()-1).getName();
            rowDate1[i][3] = entry.getKey().getState();
            i++;
        }
        Arrays.sort(rowDate1,(o1, o2) -> (((LocalDateTime)o1[0]).isBefore((LocalDateTime)o2[0])?-1:1));
        JTable arrivalTable = new JTable(rowDate1,columnNames1);
        Object[] columnNames2 = {"planned depart time","flight ID","airway","state"};
        Object[][] rowDate2 = new Object[departMap.size()][4];
        i = 0;
        for (Map.Entry<FlightEntry,LocalDateTime> entry:departMap.entrySet())
        {
            rowDate2[i][0] = entry.getValue();
            rowDate2[i][1] = entry.getKey().getName();
            rowDate2[i][2] = entry.getKey().getLocation().get(0).getName()+"-"+entry.getKey().getLocation().get(entry.getKey().getLocation().size()-1).getName();
            rowDate2[i][3] = entry.getKey().getState();
            i++;
        }
        Arrays.sort(rowDate2,(o1, o2) -> (((LocalDateTime)o1[0]).isBefore((LocalDateTime)o2[0])?-1:1));
        JTable departTable = new JTable(rowDate2,columnNames2);
        Box vBox = Box.createVerticalBox();
        vBox.add(arrivalTable.getTableHeader());
        vBox.add(arrivalTable);
        vBox.add(departTable.getTableHeader());
        vBox.add(departTable);
        jFrame.setContentPane(vBox);
        jFrame.pack();
        jFrame.setSize(600,900);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    /*@Override
    public Iterator<FlightEntry> iterator() {
        List<FlightEntry> ret = new ArrayList<>();
        for (int i  = 0;i<arrivalList.size();i++)
        {
            ret.add(arrivalList.get(i));
        }
        for (int i = 0;i<departList.size();i++)
        {
            ret.add(departList.get(i));
        }
        Collections.sort(ret,new FlightComparator());
        return ret.iterator();
    }*/

    /**
     * 按照起飞时间顺序进行迭代
     * @return 迭代器
     */
    @Override
    public Iterator<FlightEntry> iterator() {
        List<FlightEntry> ret = new ArrayList<>();
        for (Map.Entry<FlightEntry,LocalDateTime> entry:arrivalMap.entrySet())
        {
            if (!ret.contains(entry.getKey()))
                ret.add(entry.getKey());
        }
        for (Map.Entry<FlightEntry,LocalDateTime> entry:departMap.entrySet())
        {
            if (!ret.contains(entry.getKey()))
                ret.add(entry.getKey());
        }
        Collections.sort(ret,new FlightComparator());
        return ret.iterator();
    }
}
