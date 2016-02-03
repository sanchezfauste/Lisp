package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.ConsCell;
import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.Function;
import cat.udl.eps.butterp.data.Integer;
import cat.udl.eps.butterp.data.Lambda;
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
                        sum += ((Integer) cell.car).value;
                    } catch (ClassCastException ex) {
                        throw new EvaluationError("ADD should get only integer"
                                + " arguments.");
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
                        throw new EvaluationError("MULT should get only integer"
                                + " arguments.");
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
                    Symbol sym = (Symbol) ListOps.getCar(args, 0);
                    SExpression value = ListOps.getCar(args, 1).eval(env);
                    env.bindGlobal(sym, value);
                } catch (ClassCastException ex) {
                    throw new EvaluationError("DEFINE's first argument should"
                            + " be a symbol.");
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

        env.bindGlobal(new Symbol("cons"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("CONS needs two arguments.");
                }
                return ListOps.cons(ListOps.getCar(evargs, 0),
                        ListOps.getCar(evargs, 1));
            }
        });

        env.bindGlobal(new Symbol("car"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                return ListOps.car(ListOps.car(evargs));
            }
        });

        env.bindGlobal(new Symbol("cdr"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                return ListOps.cdr(ListOps.car(evargs));
            }
        });

        env.bindGlobal(new Symbol("list"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                return evargs;
            }
        });

        env.bindGlobal(new Symbol("eq"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("EQ needs two arguments.");
                }
                return ListOps.getCar(evargs, 0).equals(
                        ListOps.getCar(evargs, 1)) ? Symbol.TRUE : Symbol.NIL;
            }
        });

        env.bindGlobal(new Symbol("if"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                if (ListOps.length(args) != 3) {
                    throw new EvaluationError("IF needs condition, then and"
                            + " else parts.");
                }
                return !ListOps.getCar(args, 0).eval(env).equals(Symbol.NIL)
                        ? ListOps.getCar(args, 1).eval(env)
                        : ListOps.getCar(args, 2).eval(env);
            }
        });

        env.bindGlobal(new Symbol("lambda"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                if (ListOps.length(args) != 2) {
                    throw new EvaluationError("LAMBDA needs two args.");
                }
                if (!ListOps.isListOf(ListOps.getCar(args, 0), Symbol.class)) {
                    throw new EvaluationError("LAMBDA first param should be a"
                            + " list of symbols.");
                }
                return new Lambda(ListOps.getCar(args, 0),
                        ListOps.getCar(args, 1), env);
            }
        });

        env.bindGlobal(new Symbol("eval"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 1) {
                    throw new EvaluationError("EVAL should get only one argument.");
                }
                return ListOps.car(evargs).eval(env);
            }
        });

        env.bindGlobal(new Symbol("apply"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.length(evargs) != 2) {
                    throw new EvaluationError("APPLY should get two arguments.");
                }
                if (!ListOps.isListOf(ListOps.nth(evargs, 1), ConsCell.class)) {
                    throw new EvaluationError("APPLY second param should be a"
                            + " list.");
                }
                try {
                    Function fun = (Function) ListOps.getCar(evargs, 0).eval(env);
                    return fun.apply(ListOps.getCar(evargs, 1), env);
                } catch (ClassCastException ex) {
                    throw new EvaluationError("APPLY first param should be a"
                            + " function.");
                }
            }
        });
    }
}
