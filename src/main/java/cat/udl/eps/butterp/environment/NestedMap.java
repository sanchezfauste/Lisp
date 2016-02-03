package cat.udl.eps.butterp.environment;

import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of environment using map.
 *
 * @author Meritxell Jordana
 * @author Marc Sanchez
 */
public class NestedMap implements Environment {

    private final Map<Symbol, SExpression> map;
    // Parent environment.
    private final NestedMap parent;

    /**
     * Creates a new environment with any associated symbol.
     */
    public NestedMap() {
        map = new HashMap<Symbol, SExpression>();
        parent = null;
    }

    private NestedMap(NestedMap parent) {
        map = new HashMap<Symbol, SExpression>();
        this.parent = parent;
    }

    @Override
    public void bindGlobal(Symbol symbol, SExpression value) {
        NestedMap global = (parent == null) ? this : parent;
        while (global.parent != null) {
            global = global.parent;
        }
        global.bind(symbol, value);
    }

    @Override
    public SExpression find(Symbol symbol) {
        NestedMap env = this;
        while (env != null) {
            if (env.map.containsKey(symbol)) {
                return env.map.get(symbol);
            }
            env = env.parent;
        }
        throw new EvaluationError("Symbol " + symbol + " not found.");
    }

    @Override
    public Environment extend() {
        return new NestedMap(this);
    }

    @Override
    public void bind(Symbol symbol, SExpression value) {
        map.put(symbol, value);
    }

}
