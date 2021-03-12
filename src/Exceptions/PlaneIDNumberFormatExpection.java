package Exceptions;

public class PlaneIDNumberFormatExpection extends Exception{
    private String PlaneID;
    public PlaneIDNumberFormatExpection(String s)
    {
        PlaneID = s;
    }
    @Override
    public String getMessage()
    {
        return PlaneID+" is not end with four numbers.";
    }
}
