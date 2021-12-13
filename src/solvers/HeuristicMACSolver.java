package solvers;
import java.util.*;

import representation.*;


public class HeuristicMACSolver extends AbstractSolver {

    private VariableHeuristic heuristicVariable;
    private ValueHeuristic heuristcValeur;

    public HeuristicMACSolver(Set<Variable>variable,Set<Constraint>contraint,VariableHeuristic varHeuristic,ValueHeuristic valHeuristic)
    {
        super(variable,contraint);
        this.heuristicVariable=varHeuristic;
        this.heuristcValeur=valHeuristic;
    }

    
    @Override
    public Map<Variable,Object>solve()
    {
         // une hashmap vide qui permettras de stocker la solution 
         Map<Variable,Object> N = new HashMap<>();
         //linkedliste ordonné qui copie la liste de variable
         LinkedList<Variable>v = new LinkedList<>(this.variable);
         Map<Variable,Set<Object>> map = new HashMap<>();
        for(Variable vi : v)
        {
            Set<Object> domaine = vi.getDomain();
            Set<Object> domainefinal = new HashSet<>();
    
            for(Object d : domaine)
            {
                domainefinal.add(d);
            }
            // Appliquer la methode ordering pour melanger les domaines des variables
            List<Object> listeDomaine=this.heuristcValeur.ordering(vi,domainefinal);
            domainefinal.removeAll(listeDomaine);
            domainefinal.addAll(listeDomaine);
            map.put(vi,domainefinal);
        }

        //best Variable
        // Créer une nouvelle linkedlist stockant les variable trié 
        LinkedList<Variable>ListBest = new LinkedList<>();
        Set<Variable>listeVar= new HashSet<>(v);
        
        //PArcourir la linkedList pour extraire la meilleur variable et la supprimer par la suite ce elle-ci
        for(int i=0;i<v.size();i++)
        {
            //Sortir la meilleur variable
            Variable Meilleur = this.heuristicVariable.best(listeVar,map);
            listeVar.remove(Meilleur); // Supprimer de la liste pour ne plus retomber dessus
            ListBest.add(Meilleur); // L'ajouter a la liste final
        }



        return MACHEURISTIC(N,ListBest,map);
    }


    public Map<Variable,Object> MACHEURISTIC(Map<Variable,Object>I,LinkedList<Variable>v,Map<Variable,Set<Object>>map)
    {

        
        if(v.isEmpty())
        {
            return I;
        }
        else {
            ArcConsistency arc = new ArcConsistency(this.contraint);
            if(!arc.ac1(map))
            {
                return null;
            }
            Variable vi = v.pop();
            Set<Object> domaine = map.get(vi);
            for(Object d : domaine)
            {
                // Nouvelle hashmap contenant la solution vide ou en cours de remplissage
                Map<Variable,Object> N = new HashMap<>(I);
                N.put(vi,d);
                if(this.isConsistent(N))
                {
                    Map<Variable,Object> R = MACHEURISTIC(N,v,map);
                    if(!(R==null))
                    {
                        return R;
                    }

                }
            }
            v.push(vi);

        }
        return null;
    }


}