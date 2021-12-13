package planning;
import representation.*;
import java.lang.*;
import java.util.*;



public interface Action {


    public boolean isApplicable(Map<Variable,Object>map);
    public Map<Variable,Object> successor(Map<Variable,Object>map);
    public int getCost();
}