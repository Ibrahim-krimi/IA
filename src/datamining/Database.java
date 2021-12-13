package datamining;
import java.util.*;
import representation.*;


public class Database {

    //Ligne de la base associé au nom des variables  
    private Set<Variable>variable;
    //Liste instance qui representerass les lignes de transactions
    private List<Map<Variable,Object>>listeInstanceVar;
    
    public Database(Set<Variable>variable)
    {
        this.variable=variable;
        this.listeInstanceVar=new ArrayList<Map<Variable,Object>>();
    }

    //Ajout d'une instance à la base de donnée
    public void add(Map<Variable,Object>instance)
    {
        this.listeInstanceVar.add(instance);
    }

    //Retourne le set des variables
    public Set<Variable> getVariables()
    {
        return this.variable;
    }

    //Retourne la liste des instance des variables
    public List<Map<Variable,Object>> getInstances()
    {
        return this.listeInstanceVar;
    }

    //Retourn table des items et leur affectation
    public Map<Variable, Map<Object, BooleanVariable>> itemTable()
    {
        Map<Variable, Map<Object, BooleanVariable>> mapRetour = new HashMap<Variable, Map<Object, BooleanVariable>>();
        //Parcour des variables de la base 
        for(Variable var : getVariables())
        {
            Map<Object, BooleanVariable> itemAffectation = new HashMap<Object, BooleanVariable>();
            //Parcour des valeurs du domaine de la variable
            for(Object valeur : var.getDomain())
            {
                //Verifier que c'est un boolean
                if(valeur instanceof Boolean)
                {
                    //Si valeur faux la variable est null (n'apparait pas)
                    if(valeur.equals(false))
                    {
                        itemAffectation.put(valeur,null);
                    }
                    else {
                        itemAffectation.put(valeur,new BooleanVariable(var.getName()));
                    }
                }
                //Pour les valeurs n'étant pas des boolean
                else {
                    //Création de la variable avec un nom spécifique à sa valeur(car une variable peut avoir plusieurs domaine mais le même nom) 
                    BooleanVariable booleanvar=new BooleanVariable(var.getName()+"="+valeur);
                    itemAffectation.put(valeur,booleanvar);
                }
                
            }
            //Ajout de la variable 
            mapRetour.put(var,itemAffectation);
        }
        return mapRetour;

    }

    //Construit et retourne une BooleanDatabase à partir d'une database
    public BooleanDatabase propositionalize()
    {
        Map<Variable, Map<Object, BooleanVariable>>itemTable = this.itemTable();
        Set<BooleanVariable>items=new HashSet<BooleanVariable>();
        
        //Création des items qui seront contenu dans la base 
        for(Variable v : itemTable.keySet())
        {
            Map<Object, BooleanVariable> map =itemTable.get(v);
            for(Object key : map.keySet())
            {
                BooleanVariable boolVar= map.get(key);
                //Si la variable n'est pas null l'ajouter au set items
                if(boolVar!=null )
                {
                    //
                    items.add(boolVar);
                }
            }

        }
        //Création de la base avec le set d'items créés
        BooleanDatabase boolDataBase = new BooleanDatabase(items);

        //Creation transaction
        for(int i=0;i<this.listeInstanceVar.size();i++)
        {
            //Map contenant la transacCourrante
            Map<Variable,Object>elementTransac = this.listeInstanceVar.get(i);
            //Permet de stocker une transaction qui seras ajouter à la liste des transac de la base
            Set<BooleanVariable>itemsTransac=new HashSet<BooleanVariable>();
            //Parcour des variable de la transac courrant pour extraire les boolean variable de l'itemtable
            for(Variable varTransac : elementTransac.keySet())
            {
                //Extraction de la bolleanVar présente dans l'item table
                BooleanVariable transacBool=itemTable.get(varTransac).get(elementTransac.get(varTransac));
                //L'ajouter seulement si elle n'est pas null
                if(transacBool!=null)
                {
                    itemsTransac.add(transacBool);
                }
            }
            boolDataBase.add(itemsTransac);
        }

        return boolDataBase;
    }

}