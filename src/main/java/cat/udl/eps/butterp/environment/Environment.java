package cat.udl.eps.butterp.environment;

import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;

/**
 * Stores symbol-value associations in Lisp.
 *
 * @author Meritxell Jordana
 * @author Marc Sanchez
 */
public interface Environment {

    /**
     * Associates the specified symbol with specified value on current
     * environment.
     *
     * @param symbol a symbol.
     * @param value a value.
     */
    void bind(Symbol symbol, SExpression value);

    /**
     * Associates the specified symbol with specified value on global
     * environment.
     *
     * @param symbol a symbol.
     * @param value a value.
     */
    void bindGlobal(Symbol symbol, SExpression value);

    /**
     * Get the associated value of specified symbol. If the symbol is not
     * defined on current environment, is looked for in the parent environment
     * and successively until reach global environment.
     *
     * @param symbol a symbol.
     * @return the associated value of specified symbol.
     */
    SExpression find(Symbol symbol);

    /**
     * Get a new environment whose parent is this environment.
     *
     * @return a new environment associated with this environment.
     */
    Environment extend();
}
