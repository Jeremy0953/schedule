package Board;

import Entry.TrainEntry;
import Comparator.TrainEntryComparator;
import javax.swing.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 火车站站牌
 */
public class StationBoard implements Iterable<TrainEntry>{
    private LocalDateTime time;
    private String stationName;
    private Map<TrainEntry,LocalDateTime> arrivalMap = new HashMap<>();
    private Map<TrainEntry,LocalDateTime> departMap = new HashMap<>();
    private JFrame jFrame;

    /**
     * 火车站站牌构造方法
     * @param time 当前时间
     * @param stationName 火车站名字
     * @param arrival 抵达火车以及时间
     * @param depart 出发火车以及时间
     */
    public StationBoard(LocalDateTime time, String stationName, Map<TrainEntry, LocalDateTime> arrival, Map<TrainEntry, LocalDateTime> depart) {
        this.time = time;
        this.stationName = stationName;
        for(Map.Entry<TrainEntry,LocalDateTime> entry:arrival.entrySet())
        {
            Duration duration = Duration.between(time,entry.getValue());
            if(duration.toHours()<=1&&duration.toHours()>=-1)
            {
                arrivalMap.put(entry.getKey(),entry.getValue());
            }
        }
        for(Map.Entry<TrainEntry,LocalDateTime> entry:depart.entrySet())
        {
            Duration duration = Duration.between(time,entry.getValue());
            if(duration.toHours()<=1&&duration.toHours()>=-1)
            {
                departMap.put(entry.getKey(),entry.getValue());
            }
        }
    }

    /**
     * 可视化
     */
    public void visualize()
    {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String localTime = time.format(pattern);
        jFrame = new JFrame(localTime+" "+stationName);
        //jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Object[] columnNames1 = {"planned arrival time","train ID","railway route","state"};
        Object[][] rowDate1 = new Object[arrivalMap.size()][4];
        int i = 0;
        for (Map.Entry<TrainEntry,LocalDateTime> entry:arrivalMap.entrySet())
        {
            rowDate1[i][0] = entry.getValue();
            rowDate1[i][1] = entry.getKey().getName();
            rowDate1[i][2] = entry.getKey().getLocation().get(0).getName()+"-"+entry.getKey().getLocation().get(entry.getKey().getLocation().size()-1).getName();
            rowDate1[i][3] = entry.getKey().getState();
            i++;
        }
        Arrays.sort(rowDate1,(o1, o2) -> (((LocalDateTime)o1[0]).isBefore((LocalDateTime)o2[0])?-1:1));
        JTable arrivalTable = new JTable(rowDate1,columnNames1);
        Object[] columnNames2 = {"planned depart time","train ID","railway route","state"};
        Object[][] rowDate2 = new Object[departMap.size()][4];
        i = 0;
        for (Map.Entry<TrainEntry,LocalDateTime> entry:departMap.entrySet())
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

    /**
     * 按照所有火车的起始站的出发时间进行迭代
     * @return 返回迭代器
     */
    @Override
    public Iterator<TrainEntry> iterator() {
        List<TrainEntry> ret = new ArrayList<>();
        for (Map.Entry<TrainEntry,LocalDateTime> entry:arrivalMap.entrySet())
        {
            if (!ret.contains(entry.getKey()))
                ret.add(entry.getKey());
        }
        for (Map.Entry<TrainEntry,LocalDateTime> entry:departMap.entrySet())
        {
            if (!ret.contains(entry.getKey()))
                ret.add(entry.getKey());
        }
        Collections.sort(ret,new TrainEntryComparator());
        return ret.iterator();
    }
}
