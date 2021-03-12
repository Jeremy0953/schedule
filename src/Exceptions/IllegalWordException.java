package Exceptions;

public class IllegalWordException extends Exception {
    String words;
    public IllegalWordException(String s)
    {
        words = s;
    }
    @Override
    public String getMessage()
    {
        return "IllegalWordException:"+words;
    }
}
