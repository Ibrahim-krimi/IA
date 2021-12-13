package datamining;
import java.util.*;
import java.io.*;
import representation.*;


public class Itemset{
    private Set<BooleanVariable> items;
    private float frequence;
    
    public Itemset(Set<BooleanVariable>items,float f)
    {
        this.items=items;
        this.frequence=f;

        
    }


    public Set<BooleanVariable> getItems()
    {
        return this.items;
    }

    public float getFrequency()
    {
        return this.frequence;
    }

}