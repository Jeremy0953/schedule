package Exceptions;

public class AirportDifferentDependenceException extends Exception {
    private String flight;
    private String airport1;
    private String airport2;
    public AirportDifferentDependenceException(String flight,String airport1,String airport2)
    {
        this.flight = flight;
        this.airport1 = airport1;
        this.airport2 = airport2;
    }
    @Override
    public String getMessage() {
        return flight + "has airports conflict:" + airport1 + " " + airport2;
    }
}
