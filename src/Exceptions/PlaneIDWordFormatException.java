package Exceptions;

public class PlaneIDWordFormatException extends Exception{
    private String PlaneID;
    public PlaneIDWordFormatException(String s)
    {
        PlaneID = s;
    }
    @Override
    public String getMessage()
    {
        return PlaneID+" is not begin with N or B";
    }
}
