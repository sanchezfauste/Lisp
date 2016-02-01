package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;
import java.util.ArrayList;
import java.util.List;

public class ConsCell implements SExpression {

    public final SExpression car; // Si el definiu privat caldrà un getter
    public final SExpression cdr; // Si el definiu privat caldrà un getter

    public ConsCell(SExpression car, SExpression cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    @Override
    public SExpression eval(Environment env) {
        SExpression result = car.eval(env);
        if (result instanceof Function) {
            List<SExpression> evargs = new ArrayList<SExpression>();
            SExpression next = cdr;
            while (!next.equals(Symbol.NIL)) {
                ConsCell cell = (ConsCell) next;
                evargs.add(cell.car.eval(env));
                next = cell.cdr;
            }
            return ((Function) result).apply(ListOps.list(evargs), env);
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
