package planning;
import representation.*;
import java.lang.*;
import java.util.*;


public interface Goal {

    public boolean isSatisfiedBy(Map<Variable,Object>map);
}