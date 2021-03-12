package Exceptions;

public class NotAIntNumberFormatException extends Exception {
    String string;
    public NotAIntNumberFormatException(String s)
    {
        string = s;
    }
    @Override
    public String getMessage()
    {
        return string+" is not a int number";
    }
}
