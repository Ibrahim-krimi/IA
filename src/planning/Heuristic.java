package planning;
import java.util.*;
import representation.*;

public interface Heuristic {

    public float estimate(Map<Variable, Object>etat);
}