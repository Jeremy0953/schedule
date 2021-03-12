package Exceptions;

public class NumberOutOfBoundaryFormatException extends Exception {
    String s;
    public NumberOutOfBoundaryFormatException(String s1)
    {
        s = s1;
    }
    @Override
    public String getMessage()
    {
        return s+" is out of boundary.";
    }
}
