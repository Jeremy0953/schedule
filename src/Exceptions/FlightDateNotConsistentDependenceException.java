package Exceptions;

public class FlightDateNotConsistentDependenceException extends Exception {
    private String date1;
    private String date2;
    public FlightDateNotConsistentDependenceException(String s1,String s2)
    {
        date1 = s1;
        date2 = s2;
    }
    @Override
    public String getMessage()
    {
        return date1+" and "+date2+" are not consistent";
    }
}
