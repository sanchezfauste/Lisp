package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

/**
 * It is the atomic element of a Lisp list.
 *
 * @author Meritxell Jordana
 * @author Marc Sanchez
 */
public class ConsCell implements SExpression {

    /**
     * Represents the value of cell.
     */
    public final SExpression car;
    /**
     * Points to the next cell of the list or nil if is last.
     */
    public final SExpression cdr;

    /**
     * Constructs a cell.
     *
     * @param car value of cell.
     * @param cdr next cell of the list or nil if is last.
     */
    public ConsCell(SExpression car, SExpression cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    @Override
    public SExpression eval(Environment env) {
        SExpression result = car.eval(env);
        if (result instanceof Function) {
            return ((Function) result).apply(
                    ListOps.getEvaluatedList(cdr, env), env);
        }
        if (result instanceof Special) {
            return ((Special) result).applySpecial(cdr, env);
        }
        throw new EvaluationError("Cannot apply " + result + ".");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ConsCell) {
            return this.car.equals(((ConsCell) o).car)
                    && this.cdr.equals(((ConsCell) o).cdr);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 3 * car.hashCode() + 7 * cdr.hashCode();
    }

    @Override
    public String toString() {
        String result = "(" + car;
        SExpression next = cdr;
        while (!next.equals(Symbol.NIL)) {
            ConsCell cell = (ConsCell) next;
            result += " " + cell.car;
            next = cell.cdr;
        }
        return result + ")";
    }
}
