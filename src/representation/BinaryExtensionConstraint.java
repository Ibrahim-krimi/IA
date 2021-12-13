package representation;
import java.util.*;
import java.lang.*;

//Contraintes portant sur un ou des couples de valeurs spécifiques qui sont l'unique possibiltié de satisfaire cette contrainte.

public class BinaryExtensionConstraint implements Constraint{
    private Variable var1;
    private Variable var2;
    private Set<BinaryTuple>tuple;

    public BinaryExtensionConstraint(Variable var1,Variable var2)
    {
        this.var1=var1;
        this.var2=var2;
        //Instance satisfaite par aucun couple de valeur car tuple vide
        this.tuple=new HashSet<>();
    }
    public Variable getV1()
    {
        return this.var1;

    }
    public Variable getV2()
    {
        return this.var2;

    }
    //Création et ajout d'un binarytuple au set permettant de définir les valeurs satisfaisante
    public void addTuple(Object value1,Object value2)
    {
        BinaryTuple b = new BinaryTuple(value1, value2);
        this.tuple.add(b);
    }
    //Retourne les variables affectées par la contrainte
    @Override
    public Set<Variable>getScope()
    {
        Set<Variable> setVariable = new HashSet<>();
        setVariable.add(var1);
        setVariable.add(var2);
        return setVariable;
    }

    @Override
    //Verifie que la map possède deux valeur de variable qui satisfait(egale) à au moin un couple de valeur présent dans la liste de tuple
    public boolean isSatisfiedBy(Map<Variable,Object>verifMap)
    {
        //Test si les variables sont dans la map
        if(verifMap.containsKey(var1) && verifMap.containsKey(var2))
        {
            //Parcours du tuple pour tester si les valeurs correspondent au moin à un couple de valeur
            for(BinaryTuple tmp : this.tuple)
            {
                // test que le couple correspondant au valeur des variables d'un couple de la map
                if(verifMap.get(var1).equals(tmp.getvaleur1()) && verifMap.get(var2).equals(tmp.getvaleur2()))
                {
                    return true;
                }
            }
            return false;
        }
        throw new IllegalArgumentException("Les variables ne sont pas contenu dans la map");
    }
}