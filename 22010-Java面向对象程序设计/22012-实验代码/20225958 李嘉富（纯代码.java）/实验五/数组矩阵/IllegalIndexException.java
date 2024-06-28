package Five;

public class IllegalIndexException extends Throwable{
    public IllegalIndexException() {

    }

    public IllegalIndexException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "IllegalIndexException";
    }
}
