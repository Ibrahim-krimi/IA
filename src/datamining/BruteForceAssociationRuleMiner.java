package datamining;
import java.util.*;
import representation.*;


public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner{

    public BruteForceAssociationRuleMiner(BooleanDatabase database)
    {
        super(database);
    }

    //Retourn les sous ensemble de items en parametre sauf le vide et lui même
    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable>items)
    {
        //Stock les sous ensemble de l'items en paramètre 
        Set<Set<BooleanVariable>> sousEnsemble= new HashSet<Set<BooleanVariable>>();
        //Permet de parcourir les items du niveau actuel
        List<SortedSet<BooleanVariable>>listParcourFirst=new ArrayList<SortedSet<BooleanVariable>>(); 

        //Ajout du premier sous ensemble
        for(BooleanVariable var : items)
        {
            //Non ajout de l'ensemble vide
            if(var!=null)
            {
                // AJout de l'élément 
                SortedSet<BooleanVariable>firstVar =new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                firstVar.add(var); 
                // AJout de cette élément à la liste de parcour
                listParcourFirst.add(firstVar);
                // Ajout de l'élement ( un sous ensemble de items ) au set de retour 
                sousEnsemble.add(firstVar);
            }
        }
        for(int nbitem=0;nbitem<items.size()-2;nbitem++) //parcour des items pour effectuer des combinaisons 
        {
            List<SortedSet<BooleanVariable>>listParcourNext=new ArrayList<SortedSet<BooleanVariable>>(); // Stock sous ensemble du niveau suivant 
            for(int i=0;i<listParcourFirst.size()-1;i++)
            {     
                for(int j=i+1;j<listParcourFirst.size();j++)
                {
                    //Deux set permettant de les combiner pour acquérire des sous ensembles
                    SortedSet<BooleanVariable>firstCombine=new TreeSet<>(listParcourFirst.get(i)); 
                    SortedSet<BooleanVariable>secondCombine=new TreeSet<>(listParcourFirst.get(j)); 
                    
                    //Combine les deux item recupérer 
                    SortedSet<BooleanVariable> combineElements=Apriori.combine(firstCombine,secondCombine);
                    //Si combinaison réussi
                    if(combineElements!=null)
                    {
                        //On l'ajoute au niveau suivant pour itérer dessus lors de l'itération de la premiere boucle for
                        listParcourNext.add(combineElements);
                        //Ajout de ce sous ensemble resolvant de la combinaison dans le sous ensemble de retour
                        sousEnsemble.add(combineElements);
                    }
                }
            }
            //Passer au niveau suivant 
            listParcourFirst=listParcourNext;
        }
        //Supprimer l'items contenant tous les item
        sousEnsemble.remove(items);
        return sousEnsemble;
    }



    //Extrait les règles d'associations fréquentes
    @Override
    public Set<AssociationRule> extract(float frequence,float confiance){

        //Stocker les motifs féquents pour itérer uniquement dessus
        Set<Itemset> frequentMotif= new Apriori(this.database).extract(frequence);
        
        //Set retournant les regles d'associations 
        Set<AssociationRule> reglesAssoc = new HashSet<AssociationRule>();

        //Parcourir les motifs fréquents
        for(Itemset item : frequentMotif)
        {
            //Création des premises contenant tout les sous ensemble de l'item set courrant
            Set<Set<BooleanVariable>> allpremiseCurrent = this.allCandidatePremises(item.getItems());

            //Parcour des premises
            for(Set<BooleanVariable> premise : allpremiseCurrent)
            {
                //Permet de stocker les conclusions courrante
                Set<BooleanVariable> conclusion = new HashSet<BooleanVariable>();
                
                //Parcour des elements dans le set pour ajouter dans la conclusion ceux qui n'apparaissent pas dans la premise
                for(BooleanVariable testConclusion : item.getItems())
                {
                    //Si non contenu dans la premise ajouter dans conclusion
                    if(!premise.contains(testConclusion))
                    {
                        conclusion.add(testConclusion);
                    }
                }
                //Verifier que la confiance est supérieur ou égale à celle renseigné en parametre pour créer une regle 
                float confianceCurrant=super.confidence(premise,conclusion,frequentMotif);
                if(confianceCurrant>=confiance)
                {
                    //Retour de la Fequence du set 
                    float frequenceItem=super.frequency(item.getItems(),frequentMotif);
                    //Création d'une regle et ajout à la liste de retour des regles
                    AssociationRule regle = new AssociationRule(premise,conclusion,frequenceItem,confianceCurrant);
                    reglesAssoc.add(regle);
                }
            }

        }

        
        return reglesAssoc;
    }
}