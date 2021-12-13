package planning;
import java.util.*;
import representation.*;




public class AStarPlanner implements Planner{

    private Map<Variable, Object>etatInitial;
    private Set<Action>action;
    private Goal but; 
    private Heuristic heuristic;

    public AStarPlanner(Map<Variable, Object>etatInitial,Set<Action>action,Goal but,Heuristic h)
    {
        this.etatInitial=etatInitial;
        this.action=action;
        this.but=but;
        this.heuristic=h;
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

    public Heuristic getHeuristic()
    {
        return this.heuristic;
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
         Map<Map<Variable,Object>,Double> value = new HashMap<>();
         Double valueHeuristic=Double.valueOf(this.heuristic.estimate(this.etatInitial)).doubleValue();
         value.put(this.etatInitial,valueHeuristic);
         return Astar(plan, father, distance, value);
    }


    public List<Action> Astar(Map<Map<Variable,Object>,Action>plan,Map<Map<Variable,Object>,Map<Variable,Object>>father,
    Map<Map<Variable, Object>, Double> distance,Map<Map<Variable,Object>,Double> value)
    {
        // Création de l'open list avec l'état initial
        LinkedList<Map<Variable, Object>> openListe=new LinkedList<Map<Variable,Object>>();
        openListe.add(this.etatInitial);
        //Init distance etat inital
        distance.put(this.etatInitial,0.0);
        //Tant que des éléments sont present dans la liste des ouverts
        while(!openListe.isEmpty())
        {
            Map<Variable, Object>instantiation=Fonction.argmin(value,openListe);
            //SI le but est satisfait par l'état instanciation retourner plan
            if(this.but.isSatisfiedBy(instantiation)){
                return Fonction.getbfsplan(father,plan,instantiation);
            }
            else {
                openListe.remove(instantiation);
                //Parcour des actions 
                for(Action a : getActions())
                {
                    Map<Variable, Object> next = a.successor(instantiation);
                    if(next!=null)
                    {
                        if(!distance.containsKey(next))
                        {
                            distance.put(next,Double.POSITIVE_INFINITY);
                        }
                        if(distance.get(next)>distance.get(instantiation)+a.getCost()) 
                        {
                            //Ajout de next avec sa distance réelle 
                            distance.put(next,distance.get(instantiation)+a.getCost());
                            value.put(next,distance.get(next)+heuristic.estimate(next));
                            father.put(next,instantiation);//Ajout next et son père
                            plan.put(next,a);   //Ajout action menant next
                            openListe.add(next);
                        }
                    }
                }
            }


        }
        return null;
    }

}
