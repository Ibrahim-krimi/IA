package planning;
import representation.*;
import java.lang.*;
import java.util.*;

public class BasicAction implements Action {

    private Map<Variable,Object> precondition;
    private Map<Variable,Object> effet;
    private int cout;

    public BasicAction(Map<Variable,Object> p,Map<Variable,Object>e,int c)
    {
        this.precondition=p;
        this.effet=e;
        this.cout=c;
    }

@Override 
public boolean isApplicable(Map<Variable,Object>map)
{
    //Verifier que la précondition corresponds à la map m en parametre
    // (Les deux comportent les meme éléments)
    for(Variable x : this.precondition.keySet())
    {
        if(!map.containsKey(x))
        {
            return false;
        }
        
        if(!map.get(x).equals(this.precondition.get(x))){
            return false;
        }

    }
    return true;
     
}

@Override 
public Map<Variable, Object> successor(Map<Variable, Object>map)
{
    //Map qui va stocker l'effet appliquer sur l'état en parametre
    Map<Variable, Object> RetourMap = new HashMap<>();
    // Si la map m respect les pré condition
    if(isApplicable(map)){
        //Ajout de toute la map m (donc Variable et object)
        RetourMap.putAll(map);
        //Appliquer les effets
        RetourMap.putAll(this.effet);
    }
    return RetourMap;
}

@Override
public int getCost()
{
    return this.cout;
}



}