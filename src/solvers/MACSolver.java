package solvers;
import representation.*;
import java.lang.*;
import java.util.*;
import java.awt.im.spi.*;



public class MACSolver extends AbstractSolver{

    public MACSolver(Set<Variable>variable,Set<Constraint>contraint)
    {
       super(variable,contraint);

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
            map.put(vi,domainefinal);
        }

        return MAC(N,v,map);
    }

    public Map<Variable,Object> MAC(Map<Variable,Object>I,LinkedList<Variable>v,Map<Variable,Set<Object>>map)
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
                    Map<Variable,Object> R = MAC(N,v,map);
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