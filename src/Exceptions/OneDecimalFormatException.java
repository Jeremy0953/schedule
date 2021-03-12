package Exceptions;

public class OneDecimalFormatException extends Exception {
    String number;
    public OneDecimalFormatException(String s)
    {
        number = s;
    }
    @Override
    public String getMessage()
    {
        return number+" is not a number with only one decimal.";
    }
}
