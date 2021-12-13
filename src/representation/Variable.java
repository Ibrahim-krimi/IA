package representation;
import java.util.*;
import java.lang.*;

public class Variable {

    private String nom;
    private Set<Object> domaine;

    public Variable(String nom,Set<Object> d)
    {
        this.nom=nom;
        this.domaine=d;
    }

    public String getName()
    {
        return this.nom;
    }
    public Set<Object> getDomain()
    {
        return this.domaine;
    }

    //Redefinition du equals par rapport au nom des variables
    @Override
    public boolean equals(Object c) 
    {
        if(this==c)
        {
            return true;
        }

        else if(c instanceof Variable)
            {
                // Création de tmp incluant c pour convertir en type Variable pour la comparaison des nom
                Variable tmp = (Variable)c;
                if(tmp.getName().equals(this.getName()))
                {
                    return true;
                }
            }
        
        return false;
    }
    
    @Override
    public int hashCode()
    {
        return this.nom.hashCode();
    }

    
}