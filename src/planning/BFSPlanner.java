package planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import java.util.*;
import representation.*;


public class BFSPlanner implements Planner {

    private Map<Variable, Object>etatInitial;
    private Set<Action>action;
    private Goal but;

    public BFSPlanner(Map<Variable, Object>etatInitial,Set<Action>action,Goal but)
    {
        this.etatInitial=etatInitial;
        this.action=action;
        this.but=but;
    }
    @Override
    public Map<Variable, Object> getInitialState()
    {
        return this.etatInitial;
    }

    @Override
    public Set<Action> getActions(){
        return this.action;
    }

    @Override
    public Goal getGoal(){
        return this.but;
    }

    @Override
    public List<Action>plan()
    {
        Map<Map<Variable,Object>,Map<Variable,Object>>father=new HashMap<>();
        father.put(this.etatInitial,null); // pere de l'état initial est null car le départ n'a aucun père 
        Map<Map<Variable,Object>,Action>plan=new HashMap<>();
        return BFS(father,plan);
    }

    public List<Action>BFS(Map<Map<Variable,Object>,Map<Variable,Object>>father,Map<Map<Variable,Object>,Action>plan)
    {
        // Création de la closed liste et l'open list avec l'état initial
        List<Map<Variable, Object>> closedliste=new ArrayList<Map<Variable, Object>>();
        closedliste.add(this.etatInitial);
        LinkedList<Map<Variable, Object>> openListe=new LinkedList<Map<Variable,Object>>();
        openListe.add(this.etatInitial);

        //Si satisfaisant de l'état initial alors retourner une liste d'action vide car le but est le meme que l'état initial
        if(this.but.isSatisfiedBy(this.etatInitial))
        {
            return new ArrayList<>();
        }

        while(!openListe.isEmpty()) // Tant que openList pas vide sortir des états et appliquer des actions
        {
            Map<Variable, Object>instaciation = new HashMap<>();
            instaciation=openListe.poll(); // sortir et supprimer element openlist
            closedliste.add(instaciation); // ajouter l'élément à la closedlist
            for(Action a : getActions()) // parcour des actions
            {
                Map<Variable, Object>next=a.successor(instaciation); // action applicable nous retourn l'élément suivant 
                if(!closedliste.contains(next) && !openListe.contains(next))
                {
                    father.put(next,instaciation);// ajouter next et son père 
                    plan.put(next,a); // ajout de next et l'action menant à celui-ci
                    if(this.but.isSatisfiedBy(next)) // SI le but est le même que l'état next alors solution trouvé
                    {
                        return getbfsplan(father, plan, next);
                    }
                    else {
                        openListe.add(next); // sinon ajout next à l'open pour lui appliquer des actions
                    }
                }
            }
        }
        return null;



    }

    //Retourn le plan d'action trié
    public List<Action> getbfsplan(Map<Map<Variable,Object>,Map<Variable,Object>>father,Map<Map<Variable,Object>,Action>plan,Map<Variable,Object>goal)
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