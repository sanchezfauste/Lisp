package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

/**
 * Represents a symbol in Lisp.
 *
 * @author Meritxell Jordana
 * @author Marc Sanchez
 */
public class Symbol implements SExpression {

    public static final Symbol TRUE = new Symbol("t");
    public static final Symbol NIL = new Symbol("nil");

    public final String name;

    /**
     * Constructs a symbol represented with the specified name.
     *
     * @param name name that represents the symbol.
     */
    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public SExpression eval(Environment env) {
        return env.find(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Symbol) {
            return this.name.equals(((Symbol) o).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
