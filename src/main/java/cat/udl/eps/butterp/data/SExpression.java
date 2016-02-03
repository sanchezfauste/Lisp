package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

/**
 * Represents a Lisp expression.
 *
 * @author Meritxell Jordana
 * @author Marc Sanchez
 */
public interface SExpression {

    /**
     * Evaluates a Lisp expression using specified environment.
     *
     * @param env the environment where the expression will be evaluated.
     * @return the result of evaluate the expression.
     */
    SExpression eval(Environment env);
}
