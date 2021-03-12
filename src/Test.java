import APP.CourseCalendarApp;
import APP.FlightScheduleApp;
import APP.TrainScheduleApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import debug.*;

public class Test {
    public static void main(String[] args) throws IOException {
        /*Logger logger = Logger.getLogger("1");
        FileHandler fileHandler = null;
        try{
            fileHandler = new FileHandler("test.txt",false);
        }
        catch (IOException e)
        {
            logger.warning("fileHandler open failed.end.");
            return;
        }
        fileHandler.setFormatter(new SimpleFormatter());
        Locale.setDefault(new Locale("en","EN"));
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false);
        try {
            throw new Exception("test");
        }
        catch (Exception e)
        {
            logger.log(Level.WARNING,"ceshi",e);
            LogRecord infologRecord = new LogRecord(Level.INFO,"info");
            logger.log(infologRecord);
        }
        /*BufferedReader bf = new BufferedReader(new FileReader("test.txt"));
        String temp = bf.readLine();
        String patternString ="((.*)(P|A)M)(.*)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(temp);
        if (matcher.matches()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm:ss a");
            LocalDateTime time = LocalDateTime.parse(matcher.group(1), formatter);
            System.out.println(time);
             }*/

        //FlightScheduleApp app = new FlightScheduleApp();
        //TrainScheduleApp app = new TrainScheduleApp();
        //CourseCalendarApp app = new CourseCalendarApp();
        //app.run();
        /*int i;
        i = EventManager.book(1, 10, 20);
        i = EventManager.book(1, 1, 7);
        i = EventManager.book(1, 10, 22);
        i = EventManager.book(1, 5, 15);
        i = EventManager.book(1, 5, 12);
        i = EventManager.book(1, 7, 10);*/
        /*LowestPrice lowestPrice = new LowestPrice();
        List<Integer> price =new ArrayList<>();
        price.add(2);
        price.add(5);
        List<List<Integer>> special = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(3);
        list1.add(0);
        list1.add(5);
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(10);
        special.add(list1);
        special.add(list2);
        List<Integer> needs = new ArrayList<>();
        needs.add(3);
        needs.add(2);*/
        /*LowestPrice lowestPrice = new LowestPrice();
        List<Integer> price =new ArrayList<>();
        price.add(2);
        price.add(3);
        price.add(4);
        List<List<Integer>> special = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(1);
        list1.add(0);
        list1.add(4);
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(2);
        list2.add(1);
        list2.add(9);
        special.add(list1);
        special.add(list2);
        List<Integer> needs = new ArrayList<>();
        needs.add(1);
        needs.add(2);
        needs.add(1);
        System.out.println(lowestPrice.shoppingOffers(price,special,needs));
*/
    }
}
