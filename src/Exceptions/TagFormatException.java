package Exceptions;

public class TagFormatException extends Exception {
    private String tag;
    public TagFormatException(String tag) {
        this.tag = tag;
    }

    @Override
    public String getMessage() {
        return "TagFormatException: "+tag;
    }
}
