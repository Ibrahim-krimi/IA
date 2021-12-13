package representation;
import java.lang.*;
import java.util.*;

public class Implication implements Constraint{

    private BooleanVariable boolVar1;
    private BooleanVariable boolVar2;
    private boolean valVar1;
    private boolean valVar2;

    public Implication(BooleanVariable i1,boolean vali1,BooleanVariable i2,boolean vali2)

    {
        this.boolVar1=i1;
        this.boolVar2=i2;
        this.valVar1=vali1;
        this.valVar2=vali2;
        
    }
    public Variable getV1()
    {
        return this.boolVar1;

    }
    public Variable getV2()
    {
        return this.boolVar2;

    }
    @Override
    public Set<Variable>getScope()
    {
        Set<Variable> setVariable = new HashSet<>(); 
        setVariable.add(boolVar1);
        setVariable.add(boolVar2);
        return setVariable;
    } 
    //Avoir tel état pour boolVar1 implique d'avoir tel état pour boolVar2, (si la valeur de boolVar1 n'est celle renseigné le test passe quel que soit la valeur de boolVar2).
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object>verifMap)
    {
        //Test si les variable sont dans la map
        if(verifMap.containsKey(boolVar1) && verifMap.containsKey(boolVar2))
        {
            //Recuper en testant si les boolean attribué au variable sont identique à ceux en parametre
            boolean a= verifMap.get(boolVar1).equals(valVar1);
            boolean b =verifMap.get(boolVar2).equals(valVar2);
            
        //Retourner la formule de l'implication !p V q =  p=>q
          return !a || b;
        }
        
        throw new IllegalArgumentException("Les variables ne sont pas contenu dans la map");

    }


}