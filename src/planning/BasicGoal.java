package planning;
import representation.*;
import java.lang.*;
import java.util.*;


public class BasicGoal implements Goal {

    //But spécifié
    private Map<Variable,Object>partielle;

    public BasicGoal(Map<Variable,Object>p)
    {
        this.partielle=p;
    }

    @Override
    //Verifier que l'instantiation partielle correponds à celle spécifié en parametre
    public boolean isSatisfiedBy(Map<Variable,Object>map)
    {
        // Si toutes les variables sont contenu verifié leur valeur 
        if(map.keySet().containsAll(this.partielle.keySet())) 
        {
            for(Variable v1 : this.partielle.keySet())
            {
                // Si la valeur d'une variable instan partielle ne correponds pas à celle dans la map le test s'arette 
                if(map.get(v1)!=this.partielle.get(v1)) 
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}