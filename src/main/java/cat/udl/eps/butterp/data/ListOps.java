package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Useful operations to work with Lisp lists.
 *
 * @author Meritxell Jordana
 * @author Marc Sanchez
 */
public class ListOps {

    /**
     * Creates a list where the value of first cell is car and the next cell is
     * cdr.
     *
     * @param car value of first element of list.
     * @param cdr next element of the list.
     * @return a list.
     */
    public static SExpression cons(SExpression car, SExpression cdr) {
        if (cdr.equals(Symbol.NIL) || cdr instanceof ConsCell) {
            return new ConsCell(car, cdr);
        }
        throw new EvaluationError("CONS second argument should be list or nil.");
    }

    /**
     * Get car value of specified cell.
     *
     * @param sexpr a cell.
     * @return car value of specified cell.
     */
    public static SExpression car(SExpression sexpr) {
        if (sexpr instanceof ConsCell) {
            return ((ConsCell) sexpr).car;
        }
        throw new EvaluationError("CAR needs a list argument.");
    }

    /**
     * Get car value of cell whose position on list has the specified index.
     *
     * @param sexpr a list.
     * @param index index of desired cell.
     * @return car value of cell with specified index.
     */
    public static SExpression getCar(SExpression sexpr, int index) {
        return ListOps.car(ListOps.nth(sexpr, index));
    }

    /**
     * Get cdr value of specified cell.
     *
     * @param sexpr a cell.
     * @return cdr value of specified cell.
     */
    public static SExpression cdr(SExpression sexpr) {
        if (sexpr instanceof ConsCell) {
            return ((ConsCell) sexpr).cdr;
        }
        throw new EvaluationError("CDR needs a list argument.");
    }

    /**
     * Creates a Lisp list of specified elements.
     *
     * @param elems the elements that will contain the list.
     * @return a list with epecified elements.
     */
    public static SExpression list(SExpression... elems) {
        return ListOps.list(Arrays.asList(elems));
    }

    /**
     * Evaluates all the elements of a list using the specified environment and
     * returns a new list with the results.
     *
     * @param evargs list to be evaluated.
     * @param env the environment where the list will be evaluated.
     * @return a list with the result of evaluate the given list.
     */
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

    /**
     * Translate java list of elements into Lisp list.
     *
     * @param elems java list of elements.
     * @return a Lisp list with epecified elements.
     */
    public static SExpression list(List<SExpression> elems) {
        ListIterator<SExpression> li = elems.listIterator(elems.size());
        SExpression root = Symbol.NIL;
        while (li.hasPrevious()) {
            SExpression expr = li.previous();
            root = new ConsCell(expr, root);
        }
        return root;
    }

    /**
     * Calculates the number of elements of a Lisp list.
     *
     * @param sexpr a list.
     * @return length of specified list.
     */
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

    /**
     * Get the cell whose position on list has the specified index.
     *
     * @param sexpr a list.
     * @param n index of desired cell.
     * @return cell with specified index.
     */
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

    /**
     * Checks if all the elements of the list has a car value of specified
     * class.
     *
     * @param params a list.
     * @param klass a class.
     * @return true if all the elements of the list has a car value of specified
     * class. Otherwise returns false.
     */
    public static boolean isListOf(SExpression params, Class<?> klass) {
        if (!ListOps.isList(params)) {
            return false;
        }
        SExpression next = params;
        while (!next.equals(Symbol.NIL)) {
            ConsCell cell = (ConsCell) next;
            if (!klass.isInstance(cell.car)) {
                return false;
            }
            next = cell.cdr;
        }
        return true;
    }

    /**
     * Checks if the specified param is a Lisp list or not.
     *
     * @param params a {@code SExpression}.
     * @return true if the specified param is a Lisp list. Otherwise returns
     * false.
     */
    public static boolean isList(SExpression params) {
        return params.equals(Symbol.NIL) || params instanceof ConsCell;
    }

}
