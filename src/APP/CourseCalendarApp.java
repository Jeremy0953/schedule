package APP;

import API.PlanningEntryAPIs;
import API.Strategy1;
import Board.ClassroomBoard;
import Entry.CourseEntry;
import Entry.CourseEntryFactory;
import Entry.FlightEntry;
import Exceptions.*;
import Location.Location;
import Location.SimpleLocation;
import Resource.SingleResource;
import Resource.SingleResourceImpl;
import Resource.Teacher;
import State.CancelledState;
import State.EndedState;
import State.EntryState;
import State.WaitingState;
import Time.Timeslot;
import javafx.scene.chart.ScatterChart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 课程应用
 */
public class CourseCalendarApp {
    private Set<SingleResource<Teacher>> resources = new HashSet<>();
    private Set<SimpleLocation> locations = new HashSet<>();
    private List<CourseEntry> entries = new ArrayList<>();
    private Logger logger;
    private FileHandler fileHandler;
    int ret;
    public CourseCalendarApp()
    {
        logger = Logger.getLogger("CourseScheduleApp");
        fileHandler = null;
        try{
            fileHandler = new FileHandler("logs/CourseLogs.txt");
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
    }
    /**
     * 菜单
     * @return 用户选项
     */
    private int menu()
    {
        System.out.println("menu:\n" +
                "1:print the teachers.\n"+
                "2:add a teacher.\n"+
                "3:delete a teacher.\n"+
                "4:print the classrooms.\n"+
                "5:add a classroom.\n"+
                "6:delete a classroom.\n"+
                "7:print the entries.\n"+
                "8:add a course entry.\n"+
                "9:cancel one course entry.\n" +
                "10:allocate a teacher for one course entry.\n"+
                "11:run one course entry.\n"+
                "12:finish one course entry.\n"+
                "13:check state of one course entry.\n"+
                "14:check resource conflict.\n" +
                "15:check entries used certain resource.\n"+
                "16:show one classroom board.\n"+
                "17:change classroom for a course.\n"+
                "18:check logs.\n"+
                "0:quit.");
        while(true) {
            System.out.println("input your choice number:");
            try{
                Scanner scanner = new Scanner(System.in);
                ret = scanner.nextInt();
                while (!(ret >= 0 && ret <= 18)) {
                    System.out.println("input error,please input again.");
                    ret = scanner.nextInt();
                }
                break;
            }
            catch (InputMismatchException e)
            {
                System.out.println("input a integer please");
            }
        }
        return ret;
    }

    /**
     * 运行应用
     */
    public void run(){
        int answer = -1;
        Scanner in = new Scanner(System.in);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter patternSecond = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (answer!=0)
        {
            answer = menu();
            switch (answer)
            {
                case 1: {
                    Iterator iterator = resources.iterator();
                    while (iterator.hasNext())
                    {
                        System.out.println(((SingleResource<Teacher>)iterator.next()).getResource());
                    }
                    logger.info("print the teachers.");
                    break;
                }
                case 2: {
                    System.out.println("input teacher ID:");
                    String ID = in.next();
                    System.out.println("input teacher name:");
                    String name = in.next();
                    System.out.println("input teacher gender:");
                    String gender = in.next();
                    System.out.println("input teacher title:");
                    String title = in.next();
                    resources.add(new SingleResourceImpl<Teacher>(new Teacher(ID, name, gender, title)));
                    logger.info("add a teacher.");
                    break;
                }
                case 3: {
                    System.out.println("input ID of the teacher you want to delete.");
                    String ID = in.next();
                    Iterator iterator = resources.iterator();
                    try
                    {
                        while (iterator.hasNext())
                        {
                            SingleResource<Teacher> temp = (SingleResource<Teacher>)iterator.next();
                            if (temp.getResource().getID().equals(ID))
                            {
                                for (int i = 0;i<entries.size();i++)
                                {
                                    if (!(entries.get(i).getState() instanceof WaitingState))
                                    {
                                        if (entries.get(i).getResource().getID().equals(ID))
                                        {
                                            EntryState state = entries.get(i).getState();
                                            if (!(state instanceof CancelledState ||state instanceof EndedState))
                                            {
                                                throw new ResourceCantDeleteException("this teacher can't be deleted.");
                                            }
                                        }
                                    }
                                }
                                resources.remove(temp);
                                logger.info("delete a teacher.");
                            }
                        }
                    }
                    catch (ResourceCantDeleteException e)
                    {
                        System.out.println(e.getMessage());
                        logger.log(Level.WARNING,"Exception:resource can't be deleted.Solution:skip this choice and choose again.",e);
                    }
                    break;
                }
                case 4:{
                    Iterator iterator = locations.iterator();
                    while (iterator.hasNext())
                    {
                        System.out.println(iterator.next());
                    }
                    logger.info("print the classrooms.");
                    break;
                }
                case 5:{
                    System.out.println("input classroom name:");
                    String name = in.next();
                    SimpleLocation classroom = new SimpleLocation(name,true);
                    locations.add(classroom);
                    logger.info("add an classroom.");
                    break;
                }
                case 6:{
                    System.out.println("delete which classroom?");
                    String name = in.next();
                    Iterator iterator = locations.iterator();
                    try
                    {
                        while (iterator.hasNext())
                        {
                            SimpleLocation temp = (SimpleLocation)iterator.next();
                            if (temp.getName().equals(name))
                            {
                                for (int i = 0;i<entries.size();i++)
                                {
                                    if (entries.get(i).getLocation().equals(temp))
                                    {
                                        EntryState state = entries.get(i).getState();
                                        if (!(state instanceof CancelledState||state instanceof EndedState))
                                        {
                                            throw new LocationCantDeleteException("this location can't be deleted.");
                                        }
                                    }
                                }
                                locations.remove(temp);
                                logger.info("remove a classroom.");
                            }
                        }
                    }
                    catch (LocationCantDeleteException e)
                    {
                        System.out.println(e.getMessage());
                        logger.log(Level.WARNING,"Exception:Airport can't be removed.Solution:choose again.",e);
                    }
                    break;
                }
                case 7:{
                    for (int i = 0;i<entries.size();i++)
                    {
                        System.out.println(entries.get(i));
                    }
                    logger.info("print entries.");
                    break;
                }
                case 8:{
                    System.out.println("input the classroom");
                    String classroomName = in.next();
                    Iterator iterator = locations.iterator();
                    SimpleLocation classroom = null;
                    while (iterator.hasNext())
                    {
                        classroom = (SimpleLocation)iterator.next();
                        if (classroom.getName().equals(classroomName))
                            break;
                    }
                    if (classroom==null||!classroom.getName().equals(classroomName))
                    {
                        classroom = new SimpleLocation(classroomName,true);
                        locations.add(classroom);
                    }
                    Timeslot timeslot;
                    while (true) {
                        System.out.println("input the time of begin time:(yyyy-MM-dd HH:mm)");
                        in.nextLine();
                        String beginTime = in.nextLine();
                        System.out.println("input the time of end time:(yyyy-MM-dd HH:mm)");
                        String endTime = in.nextLine();
                        try {
                            timeslot = new Timeslot(beginTime, endTime);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("wrong format,please try again");
                            logger.log(Level.WARNING,"Exception:wrong time format.Solution:input time again.",e);
                        }
                    }
                    System.out.println("input course name:");
                    String name = in.next();
                    CourseEntry entry = CourseEntryFactory.getEntry(classroom,timeslot,name);
                    entries.add(entry);
                    try {
                        if (PlanningEntryAPIs.checkLocationConflict(entries))
                            throw new LocationConflictException("there exist location conflict.");
                        logger.info("add a entry.");
                    }
                    catch (LocationConflictException e)
                    {
                        System.out.println(e.getMessage());
                        logger.log(Level.WARNING,e.getMessage()+"Solution:skip",e);
                        entries.remove(entry);
                    }
                    break;
                }
                case 9:{
                    System.out.println("input the Entry name you want to cancel.");
                    String ID = in.next();
                    try
                    {
                        for (int i = 0;i<entries.size();i++)
                        {
                            if (entries.get(i).getName().equals(ID))
                            {
                                entries.get(i).cancel();
                                logger.info("cancel an entry.");
                            }
                        }
                    }
                    catch (CantCancelException e)
                    {
                        System.out.println(e.getMessage());
                        logger.log(Level.WARNING,e.getMessage()+"Solution:Skip this choice and choose anagin.",e);
                    }
                    break;
                }
                case 10:{
                    System.out.println("input the the Entry you want to allocate.");
                    String entryID = in.next();
                    System.out.println("input the teacher you want to allocate.");
                    String tarcherName = in.next();
                    Iterator iterator = resources.iterator();
                    Teacher teacher = null;
                    while (iterator.hasNext())
                    {
                        teacher = ((SingleResource<Teacher>)iterator.next()).getResource();
                        if (teacher.getName().equals(tarcherName))
                            break;
                    }
                    if (teacher==null||!teacher.getName().equals(tarcherName))
                    {
                        System.out.println("there is no such teacher.");
                        logger.warning("input a wrong teacher id.");
                        break;
                    }
                    CourseEntry courseEntry = null;
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(entryID))
                        {
                            courseEntry = entries.get(i);
                            break;
                        }
                    }
                    if (courseEntry==null)
                    {
                        System.out.println("there is no such Course entry.");
                        logger.warning("input a wrong course id.");
                        break;
                    }
                    courseEntry.allocate(new SingleResourceImpl<Teacher>(teacher));
                    try {
                        if (PlanningEntryAPIs.checkResourceExclusiveConflict(entries))
                            throw new ResourceConflictException("there exist resource conflict can't allocate.");
                        logger.info("allocate plane.");
                    }
                    catch (ResourceConflictException e)
                    {
                        courseEntry.allocate(null);
                        courseEntry.setState(new WaitingState());
                        System.out.println(e.getMessage());
                        logger.log(Level.WARNING,e.getMessage()+"Solution:skip this choice.",e);
                    }
                    break;
                }
                case 11:{
                    System.out.println("input the ID of entry.");
                    String ID = in.next();
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(ID))
                            entries.get(i).run();;
                    }
                    logger.info("run an entry.");
                    break;
                }
                case 12:{
                    System.out.println("input the ID of the entry.");
                    String ID = in.next();
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(ID))
                            entries.get(i).end();;
                    }
                    logger.info("end an entry.");
                    break;
                }
                case 13:{
                    System.out.println("input the ID of the entry.");
                    String ID = in.next();
                    CourseEntry courseEntry = null;
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(ID))
                        {
                            courseEntry = entries.get(i);
                            break;
                        }
                    }
                    if (courseEntry==null)
                    {
                        System.out.println("there is no such entry.");
                        logger.warning("input a wrong entry id");
                        break;
                    }
                    System.out.println(courseEntry.getState());
                    logger.info("check a entry state.");
                    break;
                }
                case 14:{
                    System.out.println(PlanningEntryAPIs.checkResourceExclusiveConflict(entries));
                    logger.info("check resource conflict.");
                    break;
                }
                case 15:{
                    PlanningEntryAPIs<Teacher> API = new PlanningEntryAPIs<>();
                    System.out.println("input the tearcher ID");
                    String teacherID = in.next();
                    Iterator iterator = resources.iterator();
                    Teacher teacher = null;
                    while (iterator.hasNext())
                    {
                        teacher = ((SingleResource<Teacher>)iterator.next()).getResource();
                        if (teacher.getID().equals(teacherID))
                            break;
                    }
                    if (teacher == null)
                    {
                        System.out.println("there is no such teacher.");
                        logger.warning("input a wrong teacher id");
                        break;
                    }
                    System.out.println("input the entry id");
                    String entryID = in.next();
                    CourseEntry courseEntry = null;
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(entryID))
                        {
                            courseEntry = entries.get(i);
                            break;
                        }
                    }
                    if (courseEntry==null)
                    {
                        System.out.println("there is no such course.");
                        logger.warning("input a wrong course id");
                        break;
                    }
                    if (!courseEntry.getResource().getID().equals(teacherID))
                    {
                        System.out.println("the teacher doesn't match the entry.");
                        logger.warning("resource doesn't match entry");
                        break;
                    }
                    Strategy1<Teacher> strategy1= new Strategy1<>(teacher,courseEntry,entries);
                    FlightEntry ret = (FlightEntry) API.findPreEntryPerResource(strategy1);
                    System.out.println(ret);
                    logger.info("check pre entry.");
                    break;
                }
                case 16:{
                    System.out.println("input the location name:");
                    String locationName = in.next();
                    SimpleLocation location = new SimpleLocation(locationName,true);
                    if (!locations.contains(location))
                    {
                        System.out.println("there is no such location.");
                        logger.warning("input a wrong location");
                        break;
                    }
                    List<CourseEntry> list = new ArrayList<>();
                    for (int i = 0;i<entries.size();i++)
                    {
                        if(((CourseEntry)entries.get(i)).getLocation().equals(location))
                        {
                            list.add(entries.get(i));
                        }
                    }
                    ClassroomBoard board = new ClassroomBoard(LocalDateTime.now(),locationName,list);
                    board.visualize();
                    logger.info("view the board of "+locationName);
                    break;
                }
                case 17:{
                    System.out.println("input the entry id");
                    String entryID = in.next();
                    CourseEntry courseEntry = null;
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(entryID))
                        {
                            courseEntry = entries.get(i);
                            break;
                        }
                    }
                    if (courseEntry==null)
                    {
                        System.out.println("there is no such course.");
                        logger.warning("input a wrong course id");
                        break;
                    }
                    System.out.println("input the classroom");
                    String classroomName = in.next();
                    Iterator iterator = locations.iterator();
                    SimpleLocation classroom = null;
                    while (iterator.hasNext())
                    {
                        classroom = (SimpleLocation)iterator.next();
                        if (classroom.getName().equals(classroomName))
                            break;
                    }
                    if (classroom==null||!classroom.getName().equals(classroomName))
                    {
                        classroom = new SimpleLocation(classroomName,false);
                        locations.add(classroom);
                    }
                    Location location = courseEntry.getLocation();
                    courseEntry.setLocaiton(classroom);
                    try{
                        if (PlanningEntryAPIs.checkLocationConflict(entries))
                            throw  new LocationConflictException("there exists location conflict.");
                        logger.info("set location");
                    }
                    catch (LocationConflictException e)
                    {
                        courseEntry.setLocaiton(location);
                        System.out.println(e.getMessage());
                        logger.log(Level.WARNING,e.getMessage()+"Solution:skip",e);
                    }
                    break;
                }
                case 0:{
                    logger.info("end.");
                    fileHandler.close();
                    return;
                }
                case 18:{
                    fileHandler.flush();
                    System.out.println("input a time (yyyy-MM-dd HH:mm),check logs during 10 minutes.");
                    in = new Scanner(System.in);
                    LocalDateTime timeChecked;
                    try {
                        timeChecked = LocalDateTime.parse(in.nextLine(),pattern);
                    }
                    catch (DateTimeParseException e)
                    {
                        System.out.println("time format wrong.");
                        break;
                    }
                    BufferedReader bf = null;
                    try {
                        bf = new BufferedReader(new FileReader("logs/CourseLogs.txt"));
                        String temp = bf.readLine();
                        String patternString = "((.*?)(P|A)M)(.*)";
                        while (temp!=null) {
                            Pattern pattern1 = Pattern.compile(patternString);
                            Matcher matcher = pattern1.matcher(temp);
                            if (matcher.matches()) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm:ss a");
                                LocalDateTime time = LocalDateTime.parse(matcher.group(1), formatter);
                                Duration duration = Duration.between(time,timeChecked);
                                if (duration.toMinutes()<=5&&duration.toMinutes()>=-5)
                                {
                                    System.out.println(time.format(patternSecond));
                                    temp = bf.readLine();
                                    pattern1 = Pattern.compile("[A-Z]+\\:(.*)");
                                    matcher = pattern1.matcher(temp);
                                    if (matcher.matches())
                                    {
                                        System.out.println(matcher.group(1));
                                    }
                                }
                                temp = bf.readLine();
                            }
                            else
                                temp = bf.readLine();
                        }
                        bf.close();
                    }
                    catch (IOException e)
                    {
                        System.out.println("log file open failed.");
                        break;
                    }
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + answer);
            }

        }
    }

}
