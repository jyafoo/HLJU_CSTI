package Five;

public class IllegalArgumentException extends Throwable {
    public IllegalArgumentException(){}

    public IllegalArgumentException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "IllegalArgumentException";
    }
}
