package src.datamining;
import java.util.*;
import src.representation.*;


public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

    protected BooleanDatabase database;

    public AbstractAssociationRuleMiner(BooleanDatabase database){
        this.database=database;
    }

    @Override
    public BooleanDatabase getDatabase(){
        return this.database;
    }

    //Retourne la fequence de l'item correspondant à un itemset frequent en parametre
    public static float frequency(Set<BooleanVariable>items,Set<Itemset>frequent)
    {
        for(Itemset i : frequent) 
        {
            if(i.getItems().equals(items))
            {
                return i.getFrequency();
            }
        }   
        return -1.0f;
    }

    //Calcul de la confiance (frequence(premise,conclusion)/frequence(premise))
    public static float confidence(Set<BooleanVariable>premise,Set<BooleanVariable>conclusion,Set<Itemset>itemset)
    {
        //Ensemble permettant de concatener la premise et conclusion
        Set<BooleanVariable> ensemble= new HashSet<BooleanVariable>();
        //Ajout des éléments de la premises et de la conclusion
        ensemble.addAll(premise);
        ensemble.addAll(conclusion);
        //Calcul de la confiance 
        float frequence=frequency(ensemble,itemset);
        return frequence/frequency(premise, itemset);
    

    }
   
}