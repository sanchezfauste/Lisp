package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

/**
 * Represents a Lisp function where the arguments are not evaluated before
 * applying the function.
 *
 * @author Meritxell Jordana
 * @author Marc Sanchez
 */
public abstract class Special implements SExpression {

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    /**
     * Evaluates the function using the specified environment.
     *
     * @param args arguments of function without been evaluated.
     * @param env environment where the function will be applied.
     * @return the result of applying function.
     */
    public abstract SExpression applySpecial(SExpression args, Environment env);

    @Override
    public String toString() {
        return String.format("<special-%x>", hashCode());
    }
}
