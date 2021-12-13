package solvers;
import java.util.*;
import representation.*;
public interface VariableHeuristic {

    public Variable best(Set<Variable> variable,Map<Variable, Set<Object>>map);

}