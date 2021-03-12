package Exceptions;

import java.time.LocalDateTime;

public class TimeDifferentDependenceException extends Exception{
    private String flight;
    private LocalDateTime time1;
    private LocalDateTime time2;
    public TimeDifferentDependenceException(String flight,LocalDateTime time1,LocalDateTime time2)
    {
        this.flight = flight;
        this.time1 = time1;
        this.time2 = time2;
    }
    @Override
    public String getMessage() {
        return flight + "has time conflict:" + time1 + " " + time2;
    }
}
