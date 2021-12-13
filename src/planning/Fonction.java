package planning;
import java.util.*;
import representation.*;

//Class permetant l'utilsiation de méthode static utile au différents planner

public class Fonction {



     //Retoune l'état ayantle cout le plus faible
     public static Map<Variable,Object>argmin(Map<Map<Variable,Object>,Double>distance,List<Map<Variable, Object>> liste)
     {
         Map<Variable,Object>EtatMin=new HashMap<>();
         Double min=Double.POSITIVE_INFINITY;
         for(Map<Variable,Object>etat : liste)
         {
             if(distance.containsKey(etat))
             {
                 if(min>distance.get(etat))
                 {
                     min=distance.get(etat);
                     EtatMin=etat;
                 }
                 
             }
         }         
 
         return EtatMin;
     }
     //Retourn le plan d'action trié
    public static List<Action> getbfsplan(Map<Map<Variable,Object>,Map<Variable,Object>>father,Map<Map<Variable,Object>,Action>plan,Map<Variable,Object>goal)
    {
        List<Action>trieListe=new ArrayList<Action>();
        while(goal!=null)
        {
            //Action non null
            if(plan.get(goal)!=null){
            //AJouter l'action qui nous a mener à goal
            trieListe.add(plan.get(goal));
            }
           
            goal=father.get(goal);  // Recuperer le père du goal
        }
        Collections.reverse(trieListe); // Inverser le plan grace à la methode reverse
        return  trieListe;
    }
}