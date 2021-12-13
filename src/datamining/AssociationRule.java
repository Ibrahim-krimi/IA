package datamining;
import java.util.*;
import representation.*;



public class AssociationRule {

    private Set<BooleanVariable>premise;
    private Set<BooleanVariable>conclusion;
    private float frequence;
    private float confiance;

    public AssociationRule(Set<BooleanVariable>p,Set<BooleanVariable>c,float f,float conf)
    {
        this.premise=p;
        this.conclusion=c;
        this.frequence=f;
        this.confiance=conf;
    }


    public Set<BooleanVariable> getPremise()
    {
        return this.premise;
    }
    public Set<BooleanVariable> getConclusion()
    {
        return this.conclusion;
    }
    public float getFrequency()
    {
        return this.frequence;
    }
    public float getConfidence()
    {
        return this.confiance;
    }
    @Override 
    public String toString()
    {
        String res="Premise : ";
        for(BooleanVariable b : premise)
        {
            res+=""+b.getName()+"\n";
        }
        res+="Conclusion: ";
        for(BooleanVariable c : conclusion)
        {
            res+=""+c.getName()+"\n";
        }
        return res;
    }
}