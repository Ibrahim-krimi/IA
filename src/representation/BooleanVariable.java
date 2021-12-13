package representation;
import java.lang.*;
import java.util.*;


public class BooleanVariable extends Variable {

    private static Set<Object> domaine = new HashSet<>();

    static{
        domaine.add(true);
        domaine.add(false);
    }
    public BooleanVariable(String nom)   
    {
        super(nom,BooleanVariable.domaine);
    }
    

    

}