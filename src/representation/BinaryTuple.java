package representation;
import java.util.*;
import java.lang.*;

public class BinaryTuple {
    private Object valeur1;
    private Object valeur2;

    public BinaryTuple(Object valeur1,Object valeur2)
    {
        this.valeur1=valeur1;
        this.valeur2=valeur2;
    }

    public Object getvaleur1()
    {
        return this.valeur1;
    }
    public Object getvaleur2()
    {
        return this.valeur2;
    }
    //Redefinition du equals sur les valeur 
    @Override
    public boolean equals(Object c)
    {
        if(this==c)
        {
            return true;
        }

        else if(c instanceof BinaryTuple)
            {
                // Création de tmp incluant c pour convertir en type BinaryTuple pour la comparaison des nom
                BinaryTuple tmp = (BinaryTuple)c;
                if(tmp.valeur1.equals(this.valeur1) && tmp.valeur2.equals(this.valeur2))
                {
                    return true;
                }
                return false;
            }
        
        return false;
    }

    @Override
    public int hashCode()
    {
        // Utilisation de Objects.hash pour inclure deux objet en paramètre de cette fonction et hasher les deux valeurs 
        return Objects.hash(this.valeur1,this.valeur2);
    }


}