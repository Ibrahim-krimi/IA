package solvers;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import representation.*;

public interface ValueHeuristic
{
    public List<Object> ordering(Variable v1,Set<Object>domaine);
}