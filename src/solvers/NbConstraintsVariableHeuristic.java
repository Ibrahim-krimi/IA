package solvers;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import representation.*;

public class NbConstraintsVariableHeuristic implements VariableHeuristic {

private Set<Constraint> contrainte;
private boolean prefere;

public NbConstraintsVariableHeuristic(Set<Constraint>contrainte,boolean prefere)
{
    this.contrainte=contrainte;
    this.prefere=prefere;
}

@Override
public Variable best(Set<Variable> variable,Map<Variable, Set<Object>>map){

    //Map qui va stocker les variable associé à leur nombre d'occurance
    Map<Variable,Integer>NombreOccurance=new HashMap<>();
    for(Variable vari : variable)
    {
        //Recuperer les variables dans la map
        for(Constraint c : this.contrainte)
        {
            if(c.getScope().contains(vari))
            {
                //Si deja dans la liste on incremente sa valeur d'apparition
                if(NombreOccurance.containsKey(vari))
                {
                    int nombreApparitionVar=NombreOccurance.get(vari);
                    nombreApparitionVar++;
                    NombreOccurance.put(vari, nombreApparitionVar);
                }
                //Sinon on le rajoute avec une apparition de 1
                else {
                    NombreOccurance.put(vari,1);
                }
            }
        }
    }
    
    //Si prefere vrai alors chercher la variable ayant le plus d'occurance
    if(this.prefere)
    {
        return getMax(NombreOccurance);
    }
    //Sinon le moin d'occurance 
    return getMin(NombreOccurance);


}


//Renvoi la variable ayant la plus petit récurrance
public Variable getMin(Map<Variable,Integer>NombreOccurance){
        int min = 100000;
        Variable v = null;
        for(Variable vi : NombreOccurance.keySet())
        {
            if(NombreOccurance.get(vi)<min)
            {
                min=NombreOccurance.get(vi);
                v=vi;
            }
        }
        return v;
    



}
//Renvoi la Variable ayant la plus grosse récurance 
public Variable getMax(Map<Variable,Integer>NombreOccurance){
        
        int max = 0;
        Variable v = null;
    
        for(Variable vi : NombreOccurance.keySet())
        {
            if(NombreOccurance.get(vi)>max)
            {
                max=NombreOccurance.get(vi);
                v=vi;
            }
        }
        return v;
    


}
    
}