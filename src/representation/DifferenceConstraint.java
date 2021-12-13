package representation;
import java.util.*;
import java.lang.*;
import java.io.*;

//Contraintes de différence
public class DifferenceConstraint implements Constraint{

    private Variable v1;
    private Variable v2;

    public DifferenceConstraint(Variable v1,Variable v2)
    {
        this.v1=v1;
        this.v2=v2;
    }

    public Variable getV1()
    {
        return this.v1;

    }
    public Variable getV2()
    {
        return this.v2;

    }

    @Override
    //Retourne les variable affecté par la contrainte
    public Set<Variable>getScope()
    {
        Set<Variable> setVariable = new HashSet<>(); 
        setVariable.add(v1);
        setVariable.add(v2);
        return setVariable;
    }

    @Override
    //Verifie que la contraite est satisfaite par la map en parametre 
    public boolean isSatisfiedBy(Map<Variable,Object>verifMap)
    {
        //Test si les variables sont contenu dans la map
        if(verifMap.containsKey(v1) && verifMap.containsKey(v2))
        {
            // Retourner si la valeur corresponds à la différence des deux
            return (!verifMap.get(v1).equals(verifMap.get(v2)));
           
            
        }
       throw new IllegalArgumentException("Les variables ne sont pas inclus dans la map");
    }
}