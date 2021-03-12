package Exceptions;

public class FlightIDFormatException extends Exception {
    private String FlightID;
    public FlightIDFormatException(String s)
    {
        FlightID = s;
    }

    @Override
    public String getMessage() {
        return "FlightIDFormatException:" +FlightID;
    }
}
