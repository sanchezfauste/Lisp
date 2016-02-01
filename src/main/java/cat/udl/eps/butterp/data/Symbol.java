package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Symbol implements SExpression {

    public static final Symbol TRUE = new Symbol("t");
    public static final Symbol NIL = new Symbol("nil");

    public final String name; // Si el definiu privat caldrà un getter

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
