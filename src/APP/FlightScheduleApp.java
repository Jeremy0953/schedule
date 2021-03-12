package APP;

import API.PlanningEntryAPIs;
import API.Strategy1;
import Board.AirportBoard;
import Entry.FlightEntry;
import Entry.FlightEntryFactory;
import Exceptions.ResourceConflictException;
import Exceptions.*;
import Location.SimpleLocation;
import Resource.Plane;
import Resource.SingleResource;
import Resource.SingleResourceImpl;
import State.CancelledState;
import State.EndedState;
import State.EntryState;
import State.WaitingState;
import Time.Timeslot;

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
 * 航班应用
 */
public class FlightScheduleApp {
    private Set<SingleResource<Plane>> resources = new HashSet<>();
    private Set<SimpleLocation> locations = new HashSet<>();
    private List<FlightEntry> entries = new ArrayList<>();
    private DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter patternSecond = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private int ret;
    private Logger logger = Logger.getLogger("FlightScheduleApp");
    private FileHandler fileHandler = null;
    /**
     * 菜单
     * @return 返回用户选项
     */
    private int menu()
    {
        System.out.println("menu:\n" +
                "1:print the planes.\n"+
                "2:add a plane.\n"+
                "3:delete a plane.\n"+
                "4:print the airports.\n"+
                "5:add an airport.\n"+
                "6:delete an airport.\n"+
                "7:print the entries.\n"+
                "8:add a flight entry.\n"+
                "9:cancel one flight entry.\n" +
                "10:allocate a plane for one flight entry.\n"+
                "11:run one flight entry.\n"+
                "12:finish one flight entry.\n"+
                "13:check state of one flight entry.\n"+
                "14:check resource conflict.\n" +
                "15:check entries used certain resource.\n"+
                "16:show one airport board.\n"+
                "17:read from file.\n"+
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
     * 运行
     */
    public void run(){
        int answer = -1;
        Scanner in = new Scanner(System.in);
        try{
            fileHandler = new FileHandler("logs/FlightLogs.txt");
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
        while (answer!=0)
        {
            answer = menu();
            switch (answer)
            {
                case 1: {
                    Iterator iterator = resources.iterator();
                    while (iterator.hasNext())
                    {
                        System.out.println(((SingleResource<Plane>)iterator.next()).getResource());
                    }
                    logger.info("print the planes.");
                    break;
                }
                case 2: {
                    int number;
                    double age;
                    System.out.println("input plane ID:");
                    String ID = in.next();
                    System.out.println("input plane type:");
                    String type = in.next();
                    System.out.println("input plane seats number:");
                    while(true) {
                        try{
                            number = in.nextInt();
                            if(number>0)
                                break;
                        }
                        catch (InputMismatchException e)
                        {
                            System.out.println("input a integer please");
                            logger.log(Level.WARNING,"interger format wrong.Solution:input again.",e);
                            in = new Scanner(System.in);
                        }
                    }
                    System.out.println("input plane age:");
                    while (true){
                        try{
                            age = in.nextDouble();
                            if (age>0)
                                break;
                        }
                        catch (InputMismatchException e)
                        {
                            System.out.println("input a double please.");
                            logger.log(Level.WARNING,"double format wrong.Solution:input again.",e);
                            in = new Scanner(System.in);
                        }
                    }
                    resources.add(new SingleResourceImpl<Plane>(new Plane(ID, type, number, age)));
                    logger.info("add a plane.");
                    break;
                }
                case 3: {
                    System.out.println("input ID of the plane you want to delete.");
                    String ID = in.next();
                    Iterator iterator = resources.iterator();
                    try
                    {
                        while (iterator.hasNext())
                        {
                            SingleResource<Plane> temp = (SingleResource<Plane>)iterator.next();
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
                                                throw new ResourceCantDeleteException("this plane can't be deleted.");
                                            }
                                        }
                                    }
                                }
                                resources.remove(temp);
                                logger.info("delete a plane.");
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
                    logger.info("print the airports.");
                    break;
                }
                case 5:{
                    System.out.println("input airport name:");
                    String name = in.next();
                    SimpleLocation airport = new SimpleLocation(name,true);
                    locations.add(airport);
                    logger.info("add an airport.");
                    break;
                }
                case 6:{
                    System.out.println("delete which airport?");
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
                                    if (entries.get(i).getLocation().contains(temp))
                                    {
                                        EntryState state = entries.get(i).getState();
                                        if (!(state instanceof CancelledState||state instanceof EndedState))
                                        {
                                            throw new LocationCantDeleteException("this location can't be deleted.");
                                        }
                                    }
                                }
                                locations.remove(temp);
                                logger.info("remove an airport.");
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
                    System.out.println("input the airport of departure");
                    String departureAirport = in.next();
                    Iterator iterator = locations.iterator();
                    SimpleLocation depart = null;
                    while (iterator.hasNext())
                    {
                        depart = (SimpleLocation)iterator.next();
                        if (depart.getName().equals(departureAirport))
                            break;
                    }
                    if (depart==null||!depart.getName().equals(departureAirport))
                    {
                        depart = new SimpleLocation(departureAirport,true);
                        locations.add(depart);
                    }
                    System.out.println("input the airport of arrival");
                    String arrivalAirport = in.next();
                    iterator = locations.iterator();
                    SimpleLocation arrival = null;
                    while (iterator.hasNext())
                    {
                        arrival = (SimpleLocation)iterator.next();
                        if (arrival.getName().equals(arrivalAirport))
                            break;
                    }
                    if (arrival==null||!arrival.getName().equals(arrivalAirport))
                    {
                        arrival = new SimpleLocation(arrivalAirport,true);
                        locations.add(arrival);
                    }
                    Timeslot timeslot;
                    while(true) {
                        System.out.println("input the time of depart time:(yyyy-MM-dd HH:mm)");
                        in.nextLine();
                        String departTime = in.nextLine();
                        System.out.println("input the time of arrive time:(yyyy-MM-dd HH:mm)");
                        String arriveTime = in.nextLine();
                        try{
                            timeslot = new Timeslot(departTime, arriveTime);
                            break;
                        }
                        catch (DateTimeParseException e)
                        {
                            System.out.println("wrong format,input again");
                            logger.log(Level.WARNING,"Exception:wrong time format.Solution:input time again.",e);
                        }
                    }
                    System.out.println("input flight ID:");
                    String name = in.next();
                    FlightEntry entry = FlightEntryFactory.getEntry(depart,arrival,timeslot,name);
                    entries.add(entry);
                    logger.info("add an entry.");
                    break;
                }
                case 9:{
                    System.out.println("input the Entry ID you want to cancel.");
                    String ID = in.next();
                    System.out.println("input the time of depart time:(yyyy-MM-dd)");
                    String date = in.next();
                    try
                    {
                        for (int i = 0;i<entries.size();i++)
                        {
                            if (entries.get(i).getName().equals(ID)&&entries.get(i).getTimeList().get(0).getBeginTime().format(datePattern).equals(date))
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
                    System.out.println("input the Resource.Resource.Plane you want to allocate.");
                    String planeID = in.next();
                    System.out.println("input the time of depart time:(yyyy-MM-dd)");
                    String date = in.next();
                    Iterator iterator = resources.iterator();
                    Plane plane = null;
                    FlightEntry flightEntry = null;
                    while (iterator.hasNext())
                    {
                        plane = ((SingleResource<Plane>)iterator.next()).getResource();
                        if (plane.getID().equals(planeID))
                            break;
                    }
                    if (plane==null||!plane.getID().equals(planeID))
                    {
                        System.out.println("there is no such plane.");
                        logger.warning("input a wrong plane id.");
                        break;
                    }
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(entryID)&&entries.get(i).getTimeList().get(0).getBeginTime().format(datePattern).equals(date))
                            flightEntry = entries.get(i);
                    }
                    if (flightEntry==null)
                    {
                        System.out.println("there is no such flight entry.");
                        logger.warning("input a wrong flight id.");
                        break;
                    }
                    flightEntry.allocate(new SingleResourceImpl<Plane>(plane));
                    try
                    {
                        if (PlanningEntryAPIs.checkResourceExclusiveConflict(entries))
                        {
                            throw new ResourceConflictException("there exist resource conflict can't allocate.");
                        }
                        logger.info("allocate plane.");
                    }
                    catch (ResourceConflictException e)
                    {
                        flightEntry.setResourceDelegation(null);
                        flightEntry.setState(new WaitingState());
                        System.out.println(e.getMessage());
                        logger.log(Level.WARNING,e.getMessage()+"Solution:skip this choice.",e);
                    }
                    break;
                }
                case 11:{
                    System.out.println("input the ID of entry.");
                    String ID = in.next();
                    System.out.println("input the time of depart time:(yyyy-MM-dd)");
                    String date = in.next();
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(ID)&&entries.get(i).getTimeList().get(0).getBeginTime().format(datePattern).equals(date))
                            entries.get(i).run();
                    }
                    logger.info("run an entry.");
                    break;
                }
                case 12:{
                    System.out.println("input the ID of the entry.");
                    String ID = in.next();
                    System.out.println("input the time of depart time:(yyyy-MM-dd)");
                    String date = in.next();
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(ID)&&entries.get(i).getTimeList().get(0).getBeginTime().format(datePattern).equals(date))
                            entries.get(i).end();
                    }
                    logger.info("end an entry.");
                    break;
                }
                case 13:{
                    System.out.println("input the ID of the entry.");
                    String ID = in.next();
                    FlightEntry flightEntry = null;
                    System.out.println("input the time of depart time:(yyyy-MM-dd)");
                    String date = in.next();
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(ID)&&entries.get(i).getTimeList().get(0).getBeginTime().format(datePattern).equals(date))
                            flightEntry = entries.get(i);
                    }
                    if (flightEntry==null)
                    {
                        System.out.println("there is no such entry.");
                        logger.warning("input a wrong entry id");
                        break;
                    }
                    System.out.println(flightEntry.getState());
                    logger.info("check a entry state.");
                    break;
                }
                case 14:{
                    System.out.println(PlanningEntryAPIs.checkResourceExclusiveConflict(entries));
                    logger.info("check resource conflict.");
                    break;
                }
                case 15:{
                    PlanningEntryAPIs<Plane> API = new PlanningEntryAPIs<>();
                    System.out.println("input the plane ID");
                    String planeID = in.next();
                    Iterator iterator = resources.iterator();
                    Plane plane = null;
                    while (iterator.hasNext())
                    {
                        plane = ((SingleResource<Plane>)iterator.next()).getResource();
                        if (plane.getID().equals(planeID))
                            break;
                    }
                    if (plane == null)
                    {
                        System.out.println("there is no such plane.");
                        logger.warning("input a wrong plane id");
                        break;
                    }
                    System.out.println("input the entry id");
                    String entryID = in.next();
                    FlightEntry flightEntry = null;
                    System.out.println("input the time of depart time:(yyyy-MM-dd)");
                    String date = in.next();
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(entryID)&&entries.get(i).getTimeList().get(0).getBeginTime().format(datePattern).equals(date))
                            flightEntry = entries.get(i);
                    }
                    if (flightEntry==null)
                    {
                        System.out.println("there is no such flight.");
                        logger.warning("input a wrong flight id");
                        break;
                    }
                    if (!flightEntry.getResource().getID().equals(planeID))
                    {
                        System.out.println("the plane doesn't match the entry.");
                        logger.warning("resource doesn't match entry");
                        break;
                    }
                    Strategy1<Plane> strategy1= new Strategy1<>(plane,flightEntry,entries);
                    FlightEntry ret = (FlightEntry) API.findPreEntryPerResource(strategy1);
                    System.out.println(ret);
                    logger.info("check pre entry.");
                    break;
                }
                /*case 16:{
                    System.out.println("input the airport name:");
                    String locationName = in.next();
                    SimpleLocation location = new SimpleLocation(locationName,true);
                    if (!locations.contains(location))
                    {
                        System.out.println("there is no such airport.");
                        break;
                    }
                    PlanningEntryCollection<FlightEntry> departList = new PlanningEntryCollection<>();
                    PlanningEntryCollection<FlightEntry> arriveList = new PlanningEntryCollection<>();
                    for (int i = 0;i<entries.size();i++)
                    {
                        if(((FlightEntry)entries.get(i)).getLocation().get(0).equals(location))
                        {
                            departList.addEntry(entries.get(i));
                        }
                        if(((FlightEntry)entries.get(i)).getLocation().get(1).equals(location))
                        {
                            arriveList.addEntry(entries.get(i));
                        }
                    }
                    AirportBoard board = new AirportBoard(LocalDateTime.now(),locationName,arriveList,departList);
                    board.visualize();
                    break;
                }*/
                case 16:{
                    System.out.println("input the airport name:");
                    String locationName = in.next();
                    SimpleLocation location = new SimpleLocation(locationName,true);
                    if (!locations.contains(location))
                    {
                        System.out.println("there is no such airport.");
                        logger.warning("input a wrong location");
                        break;
                    }
                    Map<FlightEntry,LocalDateTime> arriveMap = new HashMap<>();
                    Map<FlightEntry,LocalDateTime> departMap = new HashMap<>();
                    for (int i = 0;i<entries.size();i++)
                    {
                        FlightEntry temp = entries.get(i);
                        for (int j = 0;j<temp.getLocation().size();j++)
                        {
                            if (temp.getLocation().get(j).getName().equals(locationName))
                            {
                                if (j==0)
                                {
                                    departMap.put(temp,temp.getTimeList().get(0).getBeginTime());
                                }
                                else if (j==temp.getLocation().size()-1)
                                {
                                    arriveMap.put(temp,temp.getTimeList().get(j-1).getEndTime());
                                }
                                else
                                {
                                    departMap.put(temp,temp.getTimeList().get(j).getBeginTime());
                                    arriveMap.put(temp,temp.getTimeList().get(j-1).getEndTime());
                                }
                            }
                        }
                    }
                    AirportBoard board = new AirportBoard(LocalDateTime.now(),locationName,arriveMap,departMap);
                    board.visualize();
                    logger.info("view the board of "+locationName);
                    break;
                }
                case 0:{
                    logger.info("end");
                    fileHandler.close();
                    return;
                }
                case 17:{
                    while(true)
                    {
                        System.out.println("input the file path");
                        try {
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            readFromFile(in.next());
                            logger.info("open a file.");
                            break;
                        }
                        catch (IOException e)
                        {
                            System.out.println("open failed,please change  a path");
                            logger.log(Level.WARNING,"file opened failed.Solution:change a file path.",e);
                        } catch (TagSameException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (DifferentPlaneDependenceException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (PlaneIDWordFormatException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (NumberOutOfBoundaryFormatException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (TimeFormatException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (FlightDateNotConsistentDependenceException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (AirportDifferentDependenceException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (NotAIntNumberFormatException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (OneDecimalFormatException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (IllegalWordException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (PlaneIDNumberFormatExpection e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (FlightIDFormatException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (TimeDifferentDependenceException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (TagFormatException e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        } catch (FlightArrivalTimeDependenceExpection e) {
                            System.out.println(e.getMessage());
                            System.out.println("open failed,please change a path");
                            entries.clear();
                            resources.clear();
                            locations.clear();
                            logger.log(Level.WARNING,e.getMessage()+"Solution:change a file path.",e);
                        }
                    }
                    break;
                }
                case 18:
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
                        bf = new BufferedReader(new FileReader("logs/FlightLogs.txt"));
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
                default:
                    throw new IllegalStateException("Unexpected value: " + answer);
            }

        }
    }

    /**
     * 读取文件
     * @param filePath 文件路径
     * @throws IOException 若文件读取错误则抛出
     * */
    public void readFromFile(String filePath) throws IOException, TagSameException, TimeFormatException, FlightIDFormatException, TagFormatException, IllegalWordException, FlightDateNotConsistentDependenceException, PlaneIDWordFormatException, PlaneIDNumberFormatExpection, NotAIntNumberFormatException, NumberOutOfBoundaryFormatException, OneDecimalFormatException, AirportDifferentDependenceException, TimeDifferentDependenceException, DifferentPlaneDependenceException,FlightArrivalTimeDependenceExpection {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String str;
        String [] pattern = new String[13];
        pattern[0] = "Flight:(\\d{4}-\\d{2}-\\d{2}),([A-Z]{2}\\d{2,4})";
        pattern[1] = "\\{";
        pattern[2] = "DepartureAirport:(\\w+)";
        pattern[3] = "ArrivalAirport:(\\w+)";
        pattern[4] = "DepatureTime:((\\d{4}-\\d{2}-\\d{2}) \\d{2}:\\d{2})";
        pattern[5] = "ArrivalTime:(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2})";
        pattern[6] = "Plane:((N|B)\\d{4})";
        pattern[7] = "\\{";
        pattern[8] = "Type:(\\w+)";
        pattern[9] = "Seats:(([5-9]\\d)|([1-5]\\d{2})|(600))";
        pattern[10] = "Age:(([12]?\\d(\\.\\d)?)|(30(\\.0)?))";
        pattern[11] = "\\}";
        pattern[12] = "\\}";
        while((str = reader.readLine())!=null)
        {
            String Flightname = null;
            String date = null;
            LocalDateTime departTime;
            LocalDateTime arrivalTime = null;
            String departureAirport;
            String arrivalAirport;
            String planeName;
            String planeType;
            int seatsNumber;
            double planeAges = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            Pattern r = Pattern.compile(pattern[0]);
            Matcher matcher = r.matcher(str);
            if (matcher.matches())
            {
                date = matcher.group(1);
                Flightname = matcher.group(2);
            }
            else
            {
                Pattern pattern1 = Pattern.compile("Flight:(.*),(.*)");
                Matcher matcher1 = pattern1.matcher(str);
                if (matcher1.matches())
                {
                    if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}",matcher1.group(1)))
                    {
                        throw new TimeFormatException(matcher1.group(1));
                    }
                    if (!Pattern.matches("[A-Z]{2}\\d{2,4}",matcher1.group(2)))
                    {
                        throw new FlightIDFormatException(matcher1.group(2));
                    }
                }
                else
                {
                    throw new TagFormatException("Flight");
                }
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            if (!Pattern.matches(pattern[1],str))
            {
                throw new TagFormatException("'{'");
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            r = Pattern.compile(pattern[2]);
            matcher = r.matcher(str);
            if (matcher.matches())
                departureAirport = matcher.group(1);
            else
            {
                r = Pattern.compile("DepartureAirport:(.*)");
                matcher = r.matcher(str);
                if (!matcher.matches())
                    throw new TagFormatException("DepartureAirport");
                else
                    throw new IllegalWordException(matcher.group(1));
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            r = Pattern.compile(pattern[3]);
            matcher = r.matcher(str);
            if (matcher.matches())
                arrivalAirport = matcher.group(1);
            else
            {
                r = Pattern.compile("ArrivalAirport:(.*)");
                matcher = r.matcher(str);
                if (!matcher.matches())
                    throw new TagFormatException("ArrivalAirport");
                else
                    throw new IllegalWordException(matcher.group(1));
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            r = Pattern.compile(pattern[4]);
            matcher = r.matcher(str);
            if (matcher.matches())
            {
                try {
                    departTime = LocalDateTime.parse(matcher.group(1), this.pattern);
                    if (!matcher.group(2).equals(date)) {
                        throw new FlightDateNotConsistentDependenceException(matcher.group(2),date);
                    }
                }
                catch(DateTimeParseException e)
                {
                    throw new TimeFormatException(matcher.group(2));
                }
            }
            else
            {
                r = Pattern.compile("DepatureTime:(.*)");
                matcher = r.matcher(str);
                if (!matcher.matches())
                    throw new TagFormatException("DepatureTime");
                else{
                    throw new TimeFormatException(matcher.group(1));
                }
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            r = Pattern.compile(pattern[5]);
            matcher = r.matcher(str);
            if (matcher.matches()) {
                try
                {
                    arrivalTime = LocalDateTime.parse(matcher.group(1), this.pattern);
                    LocalDateTime arrivalTimeMinus = arrivalTime.minusDays(1);
                if (!(arrivalTime.format(formatter).equals(date) || arrivalTimeMinus.format(formatter).equals(date))) {
                    throw new FlightArrivalTimeDependenceExpection(date,(arrivalTime.format(formatter)));
                }
                }
                catch (DateTimeParseException e)
                {
                    throw new TimeFormatException(matcher.group(1));
                }
            }
            else{
                r = Pattern.compile("ArrivalTime:(.*)");
                matcher = r.matcher(str);
                if (!matcher.matches())
                    throw new TagFormatException("ArrivalTime");
                else{
                    throw new TimeFormatException(matcher.group(1));
                }
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            r = Pattern.compile(pattern[6]);
            matcher = r.matcher(str);
            if (matcher.matches())
                planeName = matcher.group(1);
            else
            {
                r = Pattern.compile("Plane:((.)(.*))");
                matcher = r.matcher(str);
                if (matcher.matches())
                {
                    if (!Pattern.matches("N|B",matcher.group(2)))
                        throw new PlaneIDWordFormatException(matcher.group(1));
                    if (!Pattern.matches("\\d{4}",matcher.group(3)))
                        throw new PlaneIDNumberFormatExpection(matcher.group(1));
                }
                else{
                    throw new TagFormatException("Plane");
                }
                break;
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            if (!Pattern.matches(pattern[7],str))
            {
                throw new TagFormatException("'{'");
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            r = Pattern.compile(pattern[8]);
            matcher = r.matcher(str);
            if (matcher.matches())
                planeType = matcher.group(1);
            else {
                r = Pattern.compile("Type:(.*)");
                matcher = r.matcher(str);
                if (!matcher.matches())
                    throw new TagFormatException("Type");
                else
                    throw new IllegalWordException(matcher.group(1));
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            r = Pattern.compile(pattern[9]);
            matcher = r.matcher(str);
            if (matcher.matches())
                try
                {
                    seatsNumber = Integer.parseInt(matcher.group(1));
                }
                catch (NumberFormatException e)
                {
                    throw new NumberFormatException("seats not a int number.");
                }
            else {
                r = Pattern.compile("Seats:(.*)");
                matcher = r.matcher(str);
                if (!matcher.matches())
                    throw new TagFormatException("Seats");
                else
                {
                    if (!Pattern.matches("\\d*",matcher.group(1)))
                        throw new NotAIntNumberFormatException(matcher.group(1));
                    else{
                        throw new NumberOutOfBoundaryFormatException(matcher.group(1));
                    }
                }
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            r = Pattern.compile(pattern[10]);
            matcher = r.matcher(str);
            if (matcher.matches())
                try {
                    planeAges = Double.parseDouble(matcher.group(1));
                }
                catch(NumberFormatException e)
                {
                    throw new NumberFormatException("age not a double number");
                }
            else {
                r = Pattern.compile("Age:(.*)");
                matcher = r.matcher(str);
                if (!matcher.matches())
                    throw new TagFormatException("Age");
                else{
                    r = Pattern.compile("(\\d+)\\.?(\\d*)");
                    matcher = r.matcher(matcher.group(1));
                    if (!matcher.matches())
                        throw new NumberFormatException("age is not a number with one or zero point");
                    else
                    {
                        if (str.contains("."))
                        {
                            if (str.length()-1-str.indexOf(".")>1)
                                throw new OneDecimalFormatException(matcher.group(0));
                        }
                        if (matcher.group(1).charAt(0) == '0')
                            throw new NumberFormatException("the number is begin with 0");
                        else
                            throw new NumberOutOfBoundaryFormatException(matcher.group(0));
                    }
                }
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            if (!Pattern.matches(pattern[11],str))
            {
                throw new TagFormatException("'}'");
            }
            str = reader.readLine();
            if (str==null)
            {
                reader.close();
                return;
            }
            while (str.equals(""))
            {
                str = reader.readLine();
                if (str==null)
                {
                    reader.close();
                    return;
                }
            }
            if (!Pattern.matches(pattern[12],str))
            {
                throw new TagFormatException("'}'");
            }
            Iterator iterator = locations.iterator();
            SimpleLocation depart = null;
            while (iterator.hasNext())
            {
                depart = (SimpleLocation)iterator.next();
                if (depart.getName().equals(departureAirport))
                    break;
            }
            if (depart==null||!depart.getName().equals(departureAirport))
            {
                depart = new SimpleLocation(departureAirport,true);
                locations.add(depart);
            }
            iterator = locations.iterator();
            SimpleLocation arrival = null;
            while (iterator.hasNext())
            {
                arrival = (SimpleLocation)iterator.next();
                if (arrival.getName().equals(arrivalAirport))
                    break;
            }
            if (arrival==null||!arrival.getName().equals(arrivalAirport))
            {
                arrival = new SimpleLocation(arrivalAirport,true);
                locations.add(arrival);
            }
            FlightEntry sameEntry = null;
            for (int i = 0;i<entries.size();i++) {
                if (entries.get(i).getName().equals(Flightname))
                {
                    sameEntry = entries.get(i);
                }
                else {
                    String samePattern = "([A-Z]{2})(0*)(([1-9][0-9]*))?";
                    r = Pattern.compile(samePattern);
                    Matcher matcher1 = r.matcher(entries.get(i).getName());
                    Matcher matcher2 = r.matcher(Flightname);
                    if (matcher1.matches() && matcher2.matches()) {
                        if (matcher1.group(1).equals(matcher2.group(1)) &&
                                matcher1.group(3).equals(matcher2.group(3)))
                            sameEntry = entries.get(i);
                        else {
                            continue;
                        }
                    }
                }
                if (sameEntry!=null)
                {
                    if (sameEntry.getTimeList().get(0).getBeginTime().format(formatter).equals(departTime.format(formatter)))
                    {
                        throw new TagSameException(sameEntry.getName(),Flightname);
                    }
                }
                if (sameEntry != null) {
                    if (!sameEntry.getLocation().get(0).equals(depart))
                    {
                        throw new AirportDifferentDependenceException(Flightname,departureAirport,sameEntry.getLocation().get(0).toString());
                    }
                    if (!sameEntry.getLocation().get(1).equals(arrival))
                    {
                        throw new AirportDifferentDependenceException(Flightname,arrivalAirport,sameEntry.getLocation().get(1).toString());
                    }
                    if (!(sameEntry.getTimeList().get(0).getBeginTime().getHour() == departTime.getHour() &&
                            sameEntry.getTimeList().get(0).getBeginTime().getMinute() == departTime.getMinute()))
                    {
                        throw new TimeDifferentDependenceException(Flightname,departTime,sameEntry.getTimeList().get(0).getBeginTime());
                    }
                    if (!(sameEntry.getTimeList().get(0).getEndTime().getHour() == arrivalTime.getHour() &&
                            sameEntry.getTimeList().get(0).getEndTime().getMinute() == arrivalTime.getMinute()))
                    {
                        throw new TimeDifferentDependenceException(Flightname,arrivalTime,sameEntry.getTimeList().get(0).getEndTime());
                    }
                    break;
                }
            }
            FlightEntry flightEntry = FlightEntryFactory.getEntry(depart,arrival,new Timeslot(departTime,arrivalTime),Flightname);
            SingleResource<Plane> plane = null;
            Iterator iterator1 = resources.iterator();
            while(iterator1.hasNext())
            {
                plane = (SingleResource<Plane>)iterator1.next();
                if (plane.getResource().getID().equals(planeName))
                {
                    if (plane.getResource().getType().equals(planeType)&&plane.getResource().getNumOfSeats()==seatsNumber&&plane.getResource().getOld()==planeAges)
                    {
                        break;
                    }
                    else{
                        throw new DifferentPlaneDependenceException(planeName);
                    }
                }
            }
            if (plane==null||!(plane.getResource().getID().equals(planeName)&&plane.getResource().getType().equals(planeType)&&plane.getResource().getNumOfSeats()==seatsNumber&&plane.getResource().getOld()==planeAges))
            {
                plane = new SingleResourceImpl<>(new Plane(planeName,planeType,seatsNumber,planeAges));
                resources.add(plane);
            }
            flightEntry.allocate(plane);
            entries.add(flightEntry);
        }
        reader.close();
    }
}
