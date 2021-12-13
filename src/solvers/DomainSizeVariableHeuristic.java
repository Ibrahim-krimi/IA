package solvers;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import representation.*;


public class DomainSizeVariableHeuristic implements VariableHeuristic{
    
    private boolean prefere;

    public DomainSizeVariableHeuristic(boolean p)
    {
        this.prefere=p;
    }


    @Override
    public Variable best(Set<Variable> variable,Map<Variable, Set<Object>>map)
    {
        //Map qui va stocker les variable associé à leur nombre d'occurance
        Map<Variable,Integer>VarTailleDomaine=new HashMap<>();

        for(Variable vari : variable)
        {
            int tailleDomaineVar=map.get(vari).size();
            VarTailleDomaine.put(vari,tailleDomaineVar);
            
        }
                
    //Si prefere vrai alors chercher la variable ayant le plus grands domaine
    if(this.prefere)
    {
        return getMax(VarTailleDomaine);
    }
    //Sinon le plus petit domaine
    return getMin(VarTailleDomaine);

    }

    //Renvoi la variable ayant la plus grand domaine
    public Variable getMin(Map<Variable,Integer>VarTailleDomaine){
        int min = 100000;
        Variable v = null;
        for(Variable vi : VarTailleDomaine.keySet())
        {
            if(VarTailleDomaine.get(vi)<min)
            {
                min=VarTailleDomaine.get(vi);
                v=vi;
            }
        }
        return v;
    }

    //Renvoi la Variable ayant le plus grand domaine 
    public Variable getMax(Map<Variable,Integer>VarTailleDomaine){
        int max = 0;
        Variable v = null;

        for(Variable vi : VarTailleDomaine.keySet())
        {
            if(VarTailleDomaine.get(vi)>max)
            {
                max=VarTailleDomaine.get(vi);
                v=vi;
            }
        }
        return v;
    }
}