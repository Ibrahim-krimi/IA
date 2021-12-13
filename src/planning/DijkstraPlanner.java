package planning;

import java.util.*;
import representation.*;



public class DijkstraPlanner implements Planner{

    private Map<Variable, Object>etatInitial;
    private Set<Action>action;
    private Goal but;

    public DijkstraPlanner(Map<Variable, Object>etatInitial,Set<Action>action,Goal but)
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
        //Initialisation 
        Map<Map<Variable,Object>,Map<Variable,Object>>father=new HashMap<>();
        father.put(this.etatInitial,null); // pere de l'état initial est null car le départ n'a aucun père 
        Map<Map<Variable,Object>,Action>plan=new HashMap<>();
        List<Map<Variable,Object>> goals=new ArrayList<>();
        Map<Map<Variable, Object>, Double> distance = new HashMap<>();
        //Retour solution
        return dijkstra(plan,distance,father,goals);
    }

    //implemter
    public List<Action> dijkstra(Map<Map<Variable,Object>,Action>plan,Map<Map<Variable,Object>,Double>distance,Map<Map<Variable,Object>,Map<Variable,Object>>father,
    List<Map<Variable,Object>> goals)
    {
        // Création de l'open list avec l'état initial
        LinkedList<Map<Variable, Object>> openListe=new LinkedList<Map<Variable,Object>>();
        openListe.add(this.etatInitial);
        distance.put(this.etatInitial,0.0);

        while(!openListe.isEmpty())
        {
            Map<Variable, Object>instantiation=Fonction.argmin(distance,openListe);
            openListe.remove(instantiation);

            if(this.but.isSatisfiedBy(instantiation)){
                goals.add(instantiation);

            }
            for(Action a : getActions())
            {
                // action applicable nous retourn l'état suivant 
                Map<Variable, Object>next=a.successor(instantiation); 

                if(next!=null) // Au cas ou successor retourn null car action non applicable
                {
                    //Si l'état next n'est pas deja contenu dans la liste distance
                    if(!distance.containsKey(next))
                    {
                        distance.put(next,Double.POSITIVE_INFINITY);
                    }
                    
                    if(distance.get(next)>distance.get(instantiation)+a.getCost())
                    {
                        //Ajout de next avec sa distance réelle 
                        distance.put(next,distance.get(instantiation)+a.getCost());
                        father.put(next,instantiation);//Ajout next et son père
                        plan.put(next,a);   //Ajout action menant next
                        openListe.add(next);
                    }
                }
                
               
            }
        }
        //Aucun but trouvé
        if(goals.isEmpty())
        {
            return null;
        }
        return this.getdijkstraplan(father,plan,goals,distance);
        

    }

    
    //Retourn le plan d'action trié
    public List<Action> getdijkstraplan(Map<Map<Variable,Object>,Map<Variable,Object>>father,Map<Map<Variable,Object>,Action>plan,List<Map<Variable,Object>>goals,
    Map<Map<Variable,Object>,Double>distance)
    {
        List<Action>trieListe=new ArrayList<Action>();
        //Sortir but avec min distance
        Map<Variable,Object>goal = Fonction.argmin(distance,goals);
        //Construction chemin
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