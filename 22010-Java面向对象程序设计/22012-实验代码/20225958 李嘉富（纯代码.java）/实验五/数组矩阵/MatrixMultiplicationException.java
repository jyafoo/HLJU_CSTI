package Five;

public class MatrixMultiplicationException extends Throwable {
    public MatrixMultiplicationException() {
    }

    public MatrixMultiplicationException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "MatrixMultiplicationException";
    }
}
