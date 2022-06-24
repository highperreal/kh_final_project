package kh.teamc.recipebackend.exception;

public class NotEnoughLikeException extends RuntimeException {

    public NotEnoughLikeException() {
    }

    public NotEnoughLikeException(String message) {
        super(message);
    }

    public NotEnoughLikeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughLikeException(Throwable cause) {
        super(cause);
    }
}
