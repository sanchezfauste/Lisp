package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class ListOps {

    public static SExpression cons(SExpression car, SExpression cdr) {
        if (cdr.equals(Symbol.NIL) || cdr instanceof ConsCell) {
            return new ConsCell(car, cdr);
        }
        throw new EvaluationError("CONS second argument should be list or nil");
    }

    public static SExpression car(SExpression sexpr) {
        if (sexpr instanceof ConsCell) {
            return ((ConsCell) sexpr).car;
        }
        throw new EvaluationError("CAR needs a list argument");
    }

    public static SExpression getCar(SExpression sexpr, int index) {
        return ListOps.car(ListOps.nth(sexpr, index));
    }

    public static SExpression cdr(SExpression sexpr) {
        if (sexpr instanceof ConsCell) {
            return ((ConsCell) sexpr).cdr;
        }
        throw new EvaluationError("CDR needs a list argument");
    }

    public static SExpression getCdr(SExpression sexpr, int index) {
        return ListOps.cdr(ListOps.nth(sexpr, index));
    }

    public static SExpression list(SExpression... elems) {
        return ListOps.list(Arrays.asList(elems));
    }

    public static SExpression getEvaluatedList(SExpression evargs, Environment env) {
        List<SExpression> evaluatedEvargs = new ArrayList<SExpression>();
        SExpression next = evargs;
        while (!next.equals(Symbol.NIL)) {
            ConsCell cell = (ConsCell) next;
            evaluatedEvargs.add(cell.car.eval(env));
            next = cell.cdr;
        }
        return ListOps.list(evaluatedEvargs);
    }

    public static SExpression list(List<SExpression> elems) {
        ListIterator<SExpression> li = elems.listIterator(elems.size());
        SExpression root = Symbol.NIL;
        while (li.hasPrevious()) {
            SExpression expr = li.previous();
            root = new ConsCell(expr, root);
        }
        return root;
    }

    public static int length(SExpression sexpr) {
        int size = 0;
        SExpression next = sexpr;
        while (!next.equals(Symbol.NIL)) {
            ConsCell cell = (ConsCell) next;
            size += 1;
            next = cell.cdr;
        }
        return size;
    }

    public static SExpression nth(SExpression sexpr, int n) {
        SExpression next = sexpr;
        for (int i = 0; i < n; i++) {
            if (next.equals(Symbol.NIL)) {
                throw new IllegalArgumentException();
            }
            ConsCell cell = (ConsCell) next;
            next = cell.cdr;
        }
        return next;
    }

    public static boolean isListOf(SExpression params, Class<?> klass) {
        SExpression next = params;
        while (!next.equals(Symbol.NIL)) {
            ConsCell cell = (ConsCell) next;
            if (klass.isInstance(cell.car)) {
                return false;
            }
            next = cell.cdr;
        }
        return true;
    }

}
