package datamining;
import java.util.*;
import representation.*;

public abstract class AbstractItemsetMiner implements ItemsetMiner {

    protected BooleanDatabase database;
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());


    public AbstractItemsetMiner(BooleanDatabase database)
    {
        this.database=database;
    }

    @Override
    public BooleanDatabase getDatabase()
    {
        return this.database;
    }
    
 

    //Retourne la fréquence des items en paramètre 
    public float frequency(Set<BooleanVariable>items){

        float frequenceRetour=0f;
        List<Set<BooleanVariable>> listeTransaction=this.database.getTransactions();
        int nombreTransaction=listeTransaction.size();
        //La base de donnée contient les items
        if(this.database.getItems().containsAll(items))
        {       
            //Parcour des items dans la liste des transactions
                for(Set<BooleanVariable> transaction : listeTransaction)
                {
                    if(transaction.containsAll(items)) // test si l'item est contenu dans la transaction
                    {
                        frequenceRetour+=1;
                    }
                }
                frequenceRetour=frequenceRetour/nombreTransaction;
        }
        return frequenceRetour;
    }

}