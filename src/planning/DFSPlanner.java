package planning;
import representation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.*;
public class DFSPlanner implements Planner {

    private Map<Variable, Object>etatInitial;
    private Set<Action>action;
    private Goal but;

    public DFSPlanner(Map<Variable, Object>etatInitial,Set<Action>action,Goal but)
    {
        this.etatInitial=etatInitial;
        this.action=action;
        this.but=but;
    }

    @Override
    public  Map<Variable, Object> getInitialState()
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

    public List<Action> DFS(Map<Variable, Object> initialeState,List<Action> listeaction,Set<Map<Variable, Object>> closedliste)
    {
        //L'état corresponds au but retourner le chemin
        if(this.but.isSatisfiedBy(initialeState))
        {
            return listeaction;
        }
        else {
            // Parcourir les actions 
            for(Action a : getActions())
            {
                Map<Variable, Object>next=a.successor(initialeState); // appliquer l'effet de l'action si elle est satisfaisante pour l'etat initiale ce qui nous renvoi le nouvel état 
                if(next!=null && !closedliste.contains(next)) // l'etat next n'est pas contenu dans dans la closedlist
                {
                    listeaction.add(a);   //ajouter l'action menant à l'etat next ainsi que l'état a la closed list
                    closedliste.add(next);
                    List<Action> subplan =DFS(next,listeaction,closedliste); // appels recusif avec le nouvelle etat next et les liste mis à jours 
                    if(subplan!=null) // Si subplan est satisfaisant 
                    {
                        return subplan;
                    }
                    else {
                        listeaction.remove(a); // retirer l'action 
                    }
                }
            }
            return null; // aucune solution
        }
    }
    @Override
    public List<Action> plan()
    {
        
        List<Action> listeaction=new ArrayList<Action>();
        Set<Map<Variable, Object>> closedliste=new HashSet<>();
        closedliste.add(this.etatInitial); // rajout etat initial dans la closedlist
        return DFS(this.etatInitial,listeaction,closedliste);
       

    }

}