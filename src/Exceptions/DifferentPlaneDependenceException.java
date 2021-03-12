package Exceptions;

public class DifferentPlaneDependenceException extends Exception {
    String plane;
    public DifferentPlaneDependenceException(String s)
    {
        plane = s;
    }
    @Override
    public String getMessage()
    {
        return plane+" has different items.";
    }

}
