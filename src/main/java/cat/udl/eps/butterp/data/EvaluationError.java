package cat.udl.eps.butterp.data;

/**
 * Represents an exception that can be thrown during evaluation of list
 * expression.
 *
 * @author Meritxell Jordana
 * @author Marc Sanchez
 */
public class EvaluationError extends RuntimeException {

    /**
     * Constructs a new evaluation error with the specified detail message.
     *
     * @param message the detail message.
     */
    public EvaluationError(String message) {
        super(message);
    }
}
