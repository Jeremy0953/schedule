package Exceptions;

public class FlightArrivalTimeDependenceExpection extends Exception{
    private String departTime;
    private String arrivalTime;
    public FlightArrivalTimeDependenceExpection(String departTime,String arrivalTime)
    {
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
    }
    @Override
    public String getMessage()
    {
        return "departTime:"+departTime+" arrivalTime:"+arrivalTime+" are not match.";
    }
}
