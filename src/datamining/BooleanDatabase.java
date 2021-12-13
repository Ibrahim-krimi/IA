package datamining;
import java.util.Set;
import java.util.*;
import representation.*;

public class BooleanDatabase {

    //Ensemble d'items 
    private Set<BooleanVariable>items;
    //Liste des transactions
    private List<Set<BooleanVariable>>listeTransaction;
    public BooleanDatabase(Set<BooleanVariable>items)
    {
        this.items=items;
        this.listeTransaction=new ArrayList<Set<BooleanVariable>>();
    }

    public void add(Set<BooleanVariable>items)
    {
        this.listeTransaction.add(items);
    }

    public Set<BooleanVariable> getItems()
    {
        return this.items;
    }

    public List<Set<BooleanVariable>> getTransactions()
    {
        return this.listeTransaction;
    }



}