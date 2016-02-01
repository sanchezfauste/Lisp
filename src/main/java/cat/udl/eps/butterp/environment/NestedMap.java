package cat.udl.eps.butterp.environment;

import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;
import java.util.HashMap;
import java.util.Map;

public class NestedMap implements Environment {

    private final Map<Symbol, SExpression> map;

    public NestedMap() {
        map = new HashMap<Symbol, SExpression>();
    }

    @Override
    public void bindGlobal(Symbol symbol, SExpression value) {
        map.put(symbol, value);
    }

    @Override
    public SExpression find(Symbol symbol) {
        if (!map.containsKey(symbol)) {
            throw new EvaluationError("Symbol " + symbol + " not found.");
        }
        return map.get(symbol);
    }

    @Override
    public Environment extend() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void bind(Symbol symbol, SExpression value) {
        throw new UnsupportedOperationException("not implemented yet");
    }

}
