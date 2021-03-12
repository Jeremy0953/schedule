package Exceptions;

public class TimeFormatException extends Exception {
    private String timeString;
    public TimeFormatException(String time)
    {
        timeString = time;
    }
    @Override
    public String getMessage()
    {
        return "TimeFormatException:"+timeString+" is a illegal time";
    }
}
