package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

/**
 * Represents a Lisp function where the given arguments have been previously
 * evaluated.
 *
 * @author Meritxell Jordana
 * @author Marc Sanchez
 */
public abstract class Function implements SExpression {

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    /**
     * Evaluates the function using the specified environment. The given
     * arguments must be evaluated before call this method.
     *
     * @param evargs a list of arguments previously evaluated.
     * @param env environment where the function will be applied.
     * @return the result of applying function.
     */
    public abstract SExpression apply(SExpression evargs, Environment env);

    @Override
    public String toString() {
        return String.format("<function-%x>", hashCode());
    }
}
