package APP;

import API.PlanningEntryAPIs;
import API.Strategy1;
import Board.StationBoard;
import Exceptions.ResourceConflictException;
import Entry.TrainEntry;
import Entry.TrainEntryFactory;
import Exceptions.CantCancelException;
import Exceptions.LocationCantDeleteException;
import Exceptions.ResourceCantDeleteException;
import Location.Location;
import Location.SimpleLocation;
import Resource.Carriage;
import Resource.MultipleSortedResourceEntryImpl;
import State.CancelledState;
import State.EndedState;
import State.EntryState;
import State.WaitingState;
import Time.Timeslot;

import java.io.BufferedReader;
import java.io.File;
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
 * 高铁应用
 */
public class TrainScheduleApp {
    private Set<Carriage> resources = new HashSet<>();
    private Set<SimpleLocation> locations = new HashSet<>();
    private List<TrainEntry> entries = new ArrayList<>();
    private int ret;
    private Logger logger;
    private FileHandler fileHandler;
    public TrainScheduleApp()
    {
        logger = Logger.getLogger("TrainScheduleApp");
        fileHandler = null;
        try{
            fileHandler = new FileHandler("logs/TrainLogs.txt");
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
     * 菜单，获取用户选项
     * @return
     */
    private int menu()
    {
        System.out.println("menu:\n" +
                "1:print the carriages.\n"+
                "2:add a carriage.\n"+
                "3:delete a carriage.\n"+
                "4:print the stations.\n"+
                "5:add a station.\n"+
                "6:delete a station.\n"+
                "7:print the entries.\n"+
                "8:add a train entry.\n"+
                "9:cancel one train entry.\n" +
                "10:allocate carriages for one train entry.\n"+
                "11:run one train entry.\n"+
                "12:finish one train entry.\n"+
                "13:check state of one train entry.\n"+
                "14:check resource conflict.\n" +
                "15:check entries used certain resource.\n"+
                "16:show one station board.\n"+
                "17:block one train entry.\n"+
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
     *运行程序
     */
    public void run() {
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
                        System.out.println(iterator.next());
                    }
                    logger.info("print the trains.");
                    break;
                }
                case 2: {
                    int number;
                    LocalDateTime year;
                    System.out.println("input carriage ID:");
                    String ID = in.next();
                    System.out.println("input carriage type:");
                    String type = in.next();
                    System.out.println("input carriage seats number:");
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
                    System.out.println("input year of carriage:");
                    String yearString = in.next();
                    try {
                        resources.add(new Carriage(ID, type, number, yearString));
                    }
                    catch (DateTimeParseException e)
                    {
                        logger.log(Level.WARNING,"year format wrong.Solution:skip this choice and choose again.",e);
                    }
                    break;
                }
                case 3: {
                    System.out.println("input ID of the carriage you want to delete.");
                    String ID = in.next();
                    Iterator iterator = resources.iterator();
                    try
                    {
                        while (iterator.hasNext())
                        {
                            Carriage temp = (Carriage)iterator.next();
                            if (temp.getID().equals(ID))
                            {
                                for (int i = 0;i<entries.size();i++)
                                {
                                    if (!(entries.get(i).getState() instanceof WaitingState))
                                    {
                                        if (entries.get(i).getResource().contains(temp))
                                        {
                                            EntryState state = entries.get(i).getState();
                                            if (!(state instanceof CancelledState ||state instanceof EndedState))
                                            {
                                                throw new ResourceCantDeleteException("this carriage can't be deleted.");
                                            }
                                        }
                                    }
                                }
                                logger.info("delete a carriage.");
                                resources.remove(temp);
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
                    System.out.println("input station name:");
                    String name = in.next();
                    SimpleLocation station = new SimpleLocation(name,true);
                    locations.add(station);
                    logger.info("add an station.");
                    break;
                }
                case 6:{
                    System.out.println("delete which station?");
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
                                logger.info("remove a station.");
                            }
                        }
                    }
                    catch (LocationCantDeleteException e)
                    {
                        System.out.println(e.getMessage());
                        logger.log(Level.WARNING,"Exception:Station can't be removed.Solution:choose again." ,e);
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
                    in = new Scanner(System.in);
                    System.out.println("input stations(XXX-XXX-XXX-XXX)separated by '-'");
                    String input = in.nextLine();
                    String[] stations = input.split("-");
                    List<Location> locationsList = new ArrayList<>();
                    for(int i = 0;i<stations.length;i++)
                    {
                        SimpleLocation temp = new SimpleLocation(stations[i],true);
                        if (!locations.contains(temp))
                            locations.add(temp);
                        locationsList.add(temp);
                    }
                    List<Timeslot> timeslotsList = new ArrayList<>();
                    while (true) {
                        System.out.println("input the timeslot of the train:(yyyy-MM-dd HH:mm,yyyy-MM-dd HH:mm)separated by ','(number must be even");
                        String totalStrings = in.nextLine();
                        String[] timeStrings = totalStrings.split(",");
                        try {
                            for (int i = 0; i < timeStrings.length; i = i + 2) {
                                Timeslot tempTimeslot = new Timeslot(timeStrings[i], timeStrings[i + 1]);
                                timeslotsList.add(tempTimeslot);
                            }
                            break;
                        }
                        catch (DateTimeParseException e)
                        {
                            System.out.println("format error,input again.");
                            logger.log(Level.WARNING,"Exception:wrong time format.Solution:input time again.",e);
                        }
                    }
                    System.out.println("input train ID:");
                    String name = in.next();
                    TrainEntry entry = TrainEntryFactory.getEntry(timeslotsList,locationsList,name);
                    entries.add(entry);
                    logger.info("add an entry.");
                    break;
                }
                case 9:{
                    System.out.println("input the Entry ID you want to cancel.");
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
                        System.out.println(e.getMessage());;
                        logger.log(Level.WARNING,e.getMessage()+"Solution:Skip this choice and choose anagin.",e);
                    }
                    break;
                }
                case 10:{
                    if (resources.isEmpty())
                    {
                        System.out.println("there is no carriages.");
                        break;
                    }
                    boolean flag = true;
                    System.out.println("input the the Entry you want to allocate.");
                    String entryID = in.next();
                    System.out.println("input the carriages you want to allocate.(input by one sort,separated by ',')");
                    in = new Scanner(System.in);
                    String []carriageIDs = in.nextLine().split(",");
                    Carriage temp = null;
                    List<Carriage> carriages = new ArrayList<>();
                    for (int i = 0;i<carriageIDs.length;i++)
                    {
                        Iterator iterator = resources.iterator();
                        while (iterator.hasNext())
                        {
                            temp = (Carriage)iterator.next();
                            if (temp.getID().equals(carriageIDs[i]))
                            {
                                break;
                            }
                        }
                        if (temp.getID().equals(carriageIDs[i]))
                        {
                            carriages.add(temp);
                        }
                        else{
                            System.out.println("there is no such carriage.");
                            logger.warning("input a wrong carriage id.");
                            flag = false;
                            break;
                        }
                    }
                    if (!flag)
                        break;
                    TrainEntry trainEntry = null;
                    for (int i = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(entryID))
                        {
                            trainEntry = entries.get(i);
                            break;
                        }
                    }
                    if (trainEntry==null)
                    {
                        System.out.println("there is no such train entry.");
                        logger.warning("input a wrong train id.");
                        break;
                    }
                    try
                    {
                        trainEntry.allocate(new MultipleSortedResourceEntryImpl<Carriage>(carriages));
                        if (PlanningEntryAPIs.checkResourceExclusiveConflict(entries))
                            throw new ResourceConflictException("there exist resource conflict can't allocate.");
                        logger.info("allocate resource.");
                    }
                    catch (ResourceConflictException e)
                    {
                        trainEntry.setResourceDelegation(null);
                        trainEntry.setState(new WaitingState());
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
                            entries.get(i).run();
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
                            entries.get(i).end();
                    }
                    logger.info("end an entry.");
                    break;
                }
                case 13:{
                    System.out.println("input the ID of the entry.");
                    String ID = in.next();
                    TrainEntry trainEntry = null;
                    for (int i  = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(ID))
                        {
                            trainEntry = entries.get(i);
                            break;
                        }
                    }
                    if (trainEntry==null)
                    {
                        System.out.println("there is no such entry.");
                        logger.warning("input a wrong entry id");
                        break;
                    }
                    System.out.println(trainEntry.getState());
                    logger.info("check a entry state.");
                    break;
                }
                case 14:{
                    System.out.println(PlanningEntryAPIs.checkResourceExclusiveConflict(entries));
                    logger.info("check resource conflict.");
                    break;
                }
                case 15:{
                    PlanningEntryAPIs<Carriage> API = new PlanningEntryAPIs<>();
                    System.out.println("input the carriage ID");
                    String carriageID = in.next();
                    Iterator iterator = resources.iterator();
                    Carriage carriage = null;
                    while (iterator.hasNext())
                    {
                        carriage = (Carriage)iterator.next();
                        if (carriage.getID().equals(carriageID))
                            break;
                    }
                    if (carriage == null)
                    {
                        System.out.println("there is no such carriage.");
                        logger.warning("input a wrong carriage id");
                        break;
                    }
                    System.out.println("input the entry id");
                    String entryID = in.next();
                    TrainEntry trainEntry = null;
                    for (int i  = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(entryID))
                        {
                            trainEntry = entries.get(i);
                            break;
                        }
                    }
                    if (trainEntry==null)
                    {
                        System.out.println("there is no such entry.");
                        logger.warning("input a wrong entry id");
                        break;
                    }
                    if (!trainEntry.getResource().contains(carriage))
                    {
                        System.out.println("the carriage doesn't match the entry.");
                        logger.warning("resource doesn't match entry");
                        break;
                    }
                    Strategy1<Carriage> strategy1= new Strategy1<>(carriage,trainEntry,entries);
                    TrainEntry ret = (TrainEntry) API.findPreEntryPerResource(strategy1);
                    System.out.println(ret);
                    logger.info("check info entry.");
                    break;
                }
                case 16:{
                    System.out.println("input the station name:");
                    String locationName = in.next();
                    SimpleLocation location = new SimpleLocation(locationName,true);
                    if (!locations.contains(location))
                    {
                        System.out.println("there is no such station.");
                        logger.warning("input a wrong station");
                        break;
                    }
                    Map<TrainEntry,LocalDateTime> arriveMap = new HashMap<>();
                    Map<TrainEntry,LocalDateTime> departMap = new HashMap<>();
                    for (int i = 0;i<entries.size();i++)
                    {
                        TrainEntry temp = entries.get(i);
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
                    StationBoard board = new StationBoard(LocalDateTime.now(),locationName,arriveMap,departMap);
                    board.visualize();
                    logger.info("view a board.");
                    break;
                }
                case 17:{
                    System.out.println("input the ID of entry.");
                    String ID = in.next();
                    TrainEntry entry = null;
                    for (int i  = 0;i<entries.size();i++)
                    {
                        if (entries.get(i).getName().equals(ID))
                        {
                            entry = entries.get(i);
                            break;
                        }
                    }
                    if (entry==null)
                    {
                        System.out.println("there is no such entry.");
                        logger.warning("input a wrong entry.");
                        break;
                    }
                    entry.block();
                    logger.info("block a entry.");
                    break;
                }
                case 0:{
                    logger.info("end");
                    fileHandler.close();
                    return;
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
                        bf = new BufferedReader(new FileReader("logs/TrainLogs.txt"));
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
}
