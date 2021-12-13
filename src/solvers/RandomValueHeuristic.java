package solvers;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import representation.*;

public class RandomValueHeuristic implements ValueHeuristic {

    private Random rand;
    public RandomValueHeuristic(Random rand)
    {
        this.rand=rand;
    }

    //Mélange les valeur des domaine aléatoirement au sein d'une liste
    @Override
    public List<Object> ordering(Variable v1,Set<Object>domaine) {
        if(domaine.isEmpty())
        {
            return null;
        }
        //Object permettant la permutation 
        Object tmp1;
        //Liste ajoutant le domaine actuelle pour le modifier 
        List<Object>ListeDomaine = new ArrayList<Object>();
        ListeDomaine.addAll(domaine);
        //Récupération de la taille 
        int taille=domaine.size();

        for(int i=0;i<taille;i++)
        {
            //Tirage d'un indice aleatoire entre le minimum i et la taille max
            int indicePermutte=this.rand.nextInt(taille - i ) + i;
            tmp1=ListeDomaine.get(i);
            //Modifie lobjet à l'indce permute par l'objet à l'indice i
            ListeDomaine.set(i,ListeDomaine.get(indicePermutte));
            //Modifie l'objet à l'indice tmp1 par celui à l'indice permutte
            ListeDomaine.set(indicePermutte,tmp1);

        }
        return ListeDomaine;
    }

}