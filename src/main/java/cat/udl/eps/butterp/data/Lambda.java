package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public class Lambda extends Function {
    
    private final SExpression params;
    private final SExpression body;
    private final Environment definitionEnv;

    public Lambda(SExpression params, SExpression body, Environment definitionEnv) {
        this.params = params;
        this.body = body;
        this.definitionEnv = definitionEnv;
    }

    @Override
    public SExpression apply(SExpression evargs, Environment callingEnv) {
        int numberOfParams = ListOps.length(params);
        if (numberOfParams != ListOps.length(evargs)) {
            throw new EvaluationError("Incorrect number of args in the call.");
        }
        Environment env = definitionEnv.extend();
        for (int i = 0; i < numberOfParams; i++) {
            env.bind((Symbol) ListOps.car(ListOps.nth(params, i)),
                    ListOps.car(ListOps.nth(evargs, i)));
        }
        return body.eval(env);
    }
}
