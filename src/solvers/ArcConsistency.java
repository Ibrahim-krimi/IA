package solvers;
import representation.*;
import java.lang.*;
import java.util.*;

import javax.swing.SpringLayout.Constraints;


import java.awt.im.spi.*;

public class ArcConsistency {

    private Set<Constraint>contraint;

    public ArcConsistency(Set<Constraint>contraint) throws IllegalArgumentException
    {
        // Test nombre de variable ayant une contraintes
        for(Constraint c : contraint)
        {
            //Si plu de deux variables erreur 
            if(c.getScope().size()>2)
            {
                throw new IllegalArgumentException("Erreur taille supérieur ou égale à deux");
            }
        }
        
        this.contraint=contraint;
        
    }
    
    //Contrainte unaire (Noeud)
    public boolean enforceNodeConsistency(Map<Variable,Set<Object>>map)
    {
        //Parcourir les variable de la map 
        for(Variable x : map.keySet())
        {
            //Initialisation d'un nouveau set domaine qui permettras d'ajouter les valeur du domaine non satisfaisante selon la contraintes
            Set<Object> new_domaine_remove = new HashSet<>();
            Set<Object>domaine = map.get(x); // Recupérer le domaine de la variable x 
            for(Object d : domaine) // Parcourir les valeurs de ce domaine
            {
                // Nouvelle map pour effectuer le test de satisfaction 
                Map<Variable,Object> m = new HashMap<>();
                m.put(x,d);
                for(Constraint c : this.contraint) 
                {
                    //SI contrainte unaire car une variable au sein de la contrainte et que la variable x est celle contenu dans la contrainte
                    if(c.getScope().size()==1 && c.getScope().contains(x))
                    {
                        //Tester si la contrainte n'est pas satisfaite alors ajouter la valeur du domaine pour la supprimer par la suite de la map
                        if(!c.isSatisfiedBy(m))
                        {
                            new_domaine_remove.add(d);
                        }
                    }
                }
                
                
            }
            // supprimer les valeurs des domaines ne satisfaisant pas les contraintes
            map.get(x).removeAll(new_domaine_remove);
                      
            
        }
        //Boucle sur la map pour tester si il y'a un domaine vide
        for(Variable x : map.keySet())
        {
            if(map.get(x).isEmpty()) // Si domaine de x vide retourner faux
            {
                return false;
            }
        }
        //Aucun domaine vide
        return true;
    }


    public boolean revise(Variable v1,Set<Object> domaine1,Variable v2,Set<Object> domaine2)
    {
        boolean del = false;
         // Nouvelle map pour effectuer le test de satisfaction 
         Map<Variable,Object> m = new HashMap<>();
         Set<Object>domaineRemove = new HashSet<>();

        //Parcour du domaine de v
        for(Object d1 : domaine1)
        {
            //Ajout de de la variable v1 et sa valeur
            m.put(v1,d1);
            boolean viable = false;
            //Parcour du domaine de v2
            for(Object d2 : domaine2)
            {
                //Ajout de de la variable v2 et sa valeur
                m.put(v2,d2);
                boolean toutSatisfait = true;
                for(Constraint c : this.contraint)
                {
                    //Test si c'est une contrainte binaire et les variable son contenu dedans
                    if(c.getScope().size()==2 && c.getScope().contains(v1) && c.getScope().contains(v2))
                    {

                        //SI non satisfait 
                        if(!c.isSatisfiedBy(m))
                        {
                            toutSatisfait=false;
                            break;
                        }
                        
                    }
                    

                }
                //SI tout est satisfait alors la valeur du domaine est viable
                if(toutSatisfait) {
                    viable=true;
                    break;
                }
            }
            //Valeur non viable
            if(!viable)
            {
                //Ajouter les domaines qui vont etre supprimer à la liste adequate
                domaineRemove.add(d1);
                del=true;
            }
        }
        //Supprimer toutes les valeurs du domaine non viable
        domaine1.removeAll(domaineRemove);

        return del;
    }


    public boolean ac1(Map<Variable,Set<Object>>map)
    {
        if(!enforceNodeConsistency(map))
        {
            return false;
        }
        boolean change;
        do {
            change=false;
            for(Variable v1 : map.keySet())
            {
                for(Variable v2 : map.keySet())
                {
                    if(!v1.equals(v2))
                    {
                        if(revise(v1,map.get(v1),v2,map.get(v2)))
                        {
                            change=true;
                        }
                    }
                }
            }
        }while(change);

        for(Variable x : map.keySet())
        {
            if(map.get(x).isEmpty())
            {
                return false;
            }
        }
        return true;

        
    }






}