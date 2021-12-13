package datamining;
import java.util.*;
import representation.*;


public class Apriori extends AbstractItemsetMiner{

    public Apriori(BooleanDatabase database)
    {
        super(database);
    }


    //Frequence pour les item singleton
    public Set<Itemset> frequentSingletons(float frequence){
        //Liste retour items correspondant à la fréquence
        Set<Itemset>listeItemsSingleton=new HashSet<Itemset>();
        Set<BooleanVariable>listeitem=this.database.getItems();

        for(BooleanVariable item : listeitem)
        {
            //Item singleton
            Set<BooleanVariable>items=new HashSet<BooleanVariable>();
            items.add(item);

            //Calcul de la fréquence et si la fréquence est plus grande ou egale alors on l'ajoute
            float f=super.frequency(items);
            if(f>=frequence)
            {
                listeItemsSingleton.add(new Itemset(items,f)); 
            }
            
        }
        return listeItemsSingleton;

        
    }

    //Combine deux items trié pour renvoyer un sous domaine trié de ces deux items (AB,AC=>ABC)
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable>itemFirst,SortedSet<BooleanVariable>itemTwo){

        //Stockage des item sous forme de tableaux pour les parcourirs
        Object[] tabfirst =itemFirst.toArray();
        Object[] tabftwo =itemTwo.toArray();
        //Taille set items(choix du 1 car inutile de tester les deux)
        int size=itemFirst.size();

        //Si items vide retourner null
        if(size==0)
        {
            return null;
        }
        //Verifier que les items sont de meme taille
        if(itemFirst.size()==itemTwo.size())
        {
           //Parcourir les items jusqu'a k-1 pour tester leur égalité
            for(int i=0;i<size-1;i++)
            {
                if(tabfirst[i]!=tabftwo[i])
                {
                    return null;
                }
            }
            //Tester que le dernier items de chaque set ne sont pas égaux entre eux
            if(tabfirst[size-1]!=tabftwo[size-1])
            {
                //Retourner le set combiner en castant les objets
                SortedSet<BooleanVariable> setRetour = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                for(int i=0;i<size;i++)
                {
                    BooleanVariable b1=BooleanVariable.class.cast(tabfirst[i]);
                    setRetour.add(b1);
                }
                setRetour.add(BooleanVariable.class.cast(tabftwo[size-1]));
                return setRetour;

            }
            else {
                return null;
            }
            
        }
        return null;

    }
    //Verifie que les sous ensemble d'items sont contenu dans la collection en parametre
    public static boolean allSubsetsFrequent(Set<BooleanVariable>items, Collection<SortedSet<BooleanVariable>>ensembleItems)
    {

        //Parcour des item
        for(BooleanVariable item : items)
        {
            //Items temporaire pour éviter les modifications concurrante
            Set<BooleanVariable>itemstmp=new HashSet<>(items);
            //Supprimer l'item du set 
            itemstmp.remove(item);
            //Tester si le sous ensemble d'items actuel est présent dans la collection
            if(!ensembleItems.contains(itemstmp))
            {
                return false;
            }

        }
        return true;
    }



    //Extraction les motifs fréquents de la base de donnée par rapport à la fréquence renseigné 
    @Override
    public Set<Itemset> extract(float frequence){
        //Extraire les singletons fréquent puis les ajouter au set de retour
        Set<Itemset>listeItemsSingleton=this.frequentSingletons(frequence);
        Set<Itemset>retour=new HashSet<Itemset>();
        retour.addAll(listeItemsSingleton);

        Set<Itemset> tmp = new HashSet<Itemset>();


        //Liste qui permet de conserver et parcourir les item fréquent ajouté pour developpé de nouveau sous ensemble fréquent
        List<SortedSet<BooleanVariable>>listeItemFrequentFirstLevel=new ArrayList<SortedSet<BooleanVariable>>();
        
        //Parcour des item singletton
        for(Itemset item : listeItemsSingleton)
        {
            //Tri des item 
            SortedSet<BooleanVariable> setTri = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            setTri.addAll(item.getItems());
            //Ajout item trié liste retour 
            listeItemFrequentFirstLevel.add(setTri);
        }

        //Boucle sur la taille des motifs commençant à la taille 2
        for(int i=2;i<=super.database.getItems().size();i++)
        {
            //Permet de stocker les niveaux suivant 
            List<SortedSet<BooleanVariable>>listeItemFrequentNextLevel=new ArrayList<SortedSet<BooleanVariable>>();
            //Parcour des niveau actuel pour créer les niveau (sous ensemble )suivant 
            for(int j=0;j<listeItemFrequentFirstLevel.size()-1;j++)
            {
                for(int k=j+1;k<listeItemFrequentFirstLevel.size();k++)
                {
                    //Combinaison des deux items pour avoir un nouveau sous ensemble
                    SortedSet<BooleanVariable> nouveauSousItem=this.combine(listeItemFrequentFirstLevel.get(j),listeItemFrequentFirstLevel.get(k));
                    //Si la combinaison a réussi et que les éléments sont contenus dans l'ensemble de départ
                    if(nouveauSousItem!=null && this.allSubsetsFrequent(nouveauSousItem,listeItemFrequentFirstLevel))
                    {
                        //Fréquence du nouveau sous ensemble 
                        float newFrequence=super.frequency(nouveauSousItem);
                        //Si la fréquence plus grands on ajoute le sous ensemble à la liste de retour 
                        if(newFrequence>=frequence)
                        {
                            //Ajout du sous ensemble et création de l'item set dans l'ensemble de retour
                            listeItemFrequentNextLevel.add(nouveauSousItem);
                            retour.add(new Itemset(nouveauSousItem,newFrequence));
                        }
                    }
                }
            }
            //Changer la listeItemFrequent par le niveau suivant pour agir sur le niveau suivant 
            listeItemFrequentFirstLevel=listeItemFrequentNextLevel;

        }


        return retour;
    }
}