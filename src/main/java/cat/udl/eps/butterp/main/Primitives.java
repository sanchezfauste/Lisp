package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.ConsCell;
import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.Function;
import cat.udl.eps.butterp.data.Integer;
import cat.udl.eps.butterp.data.ListOps;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Special;
import cat.udl.eps.butterp.data.Symbol;
import cat.udl.eps.butterp.environment.Environment;

public class Primitives {

    public static void loadPrimitives(Environment env) {
        env.bindGlobal(Symbol.NIL, Symbol.NIL);
        env.bindGlobal(Symbol.TRUE, Symbol.TRUE);

        env.bindGlobal(new Symbol("add"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                int sum = 0;
                SExpression next = evargs;
                while (!next.equals(Symbol.NIL)) {
                    ConsCell cell = (ConsCell) next;
                    try {
                        sum += ((Integer) cell.car.eval(env)).value;
                    } catch (ClassCastException ex) {
                        throw new EvaluationError("ADD should get only integer arguments.");
                    }
                    next = cell.cdr;
                }
                return new Integer(sum);
            }
        });

        env.bindGlobal(new Symbol("mult"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                int mult = 1;
                SExpression next = evargs;
                while (!next.equals(Symbol.NIL)) {
                    ConsCell cell = (ConsCell) next;
                    try {
                        mult *= ((Integer) cell.car).value;
                    } catch (ClassCastException ex) {
                        throw new EvaluationError("MULT should get only integer arguments.");
                    }
                    next = cell.cdr;
                }
                return new Integer(mult);
            }
        });

        env.bindGlobal(new Symbol("define"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                if (ListOps.length(args) != 2) {
                    throw new EvaluationError("DEFINE should have two arguments.");
                }
                try {
                    Symbol sym = (Symbol) ListOps.car(args);
                    SExpression value = ListOps.car(ListOps.cdr(args)).eval(env);
                    env.bindGlobal(sym, value);
                } catch (ClassCastException ex) {
                    throw new EvaluationError("DEFINE's first argument should be a symbol.");
                }
                return Symbol.NIL;
            }
        });
        
        env.bindGlobal(new Symbol("quote"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                if (ListOps.length(args) != 1) {
                    throw new EvaluationError("QUOTE should have only one argument.");
                }
                return ListOps.car(args);
            }
        });

        /*

         An example of a predefined Function:

         env.bindGlobal(new Symbol("function"), new Function() {
         @Override
         public SExpression apply(SExpression evargs, Environment env) {
         throw new UnsupportedOperationException("not implemented yet");
         }
         });

         */

        /*

         An example of a predefined Special:

         env.bindGlobal(new Symbol("special"), new Special() {
         @Override
         public SExpression applySpecial(SExpression args, Environment env) {
         throw new UnsupportedOperationException("not implemented yet");
         }
         });

         */
    }
}
