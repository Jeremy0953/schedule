package Exceptions;

public class CantCancelException extends Exception{
    public CantCancelException (String str)
    {
        super(str);
    }
}
