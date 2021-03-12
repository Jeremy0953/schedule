package Exceptions;

import Entry.FlightEntry;

public class TagSameException extends Exception {
    public TagSameException(String entry1,String entry2)
    {
        super(entry1+" and "+entry2+" has same tags.");
    }
}
