package examples;
import java.util.*;
import java.lang.*;
import representation.*;


public class HouseExample {
    private int largeur;
    private int longueur;
    private Set<String>NbPieceEau;
    private Set<String>NbAutrePiece;
    private Set<Variable>variables;
    private Set<Constraint>contraintes;

    public HouseExample(int largeur,int longueur,Set<String>NbPieceEau,Set<String>NbAutrePiece)
    {
        this.largeur=largeur;
        this.longueur=longueur;
        this.NbPieceEau=NbPieceEau;
        this.NbAutrePiece=NbAutrePiece;
        this.variables=new HashSet<Variable>();
        this.contraintes=new HashSet<Constraint>();

    }


    public int getLargeur()
    {
        return this.largeur;
    }
    public int getLongueur()
    {
        return this.longueur;
    }
    public Set<String>getPieceEau()
    {
        return this.NbPieceEau;
    }
    public Set<String>getAutrePiece()
    {
        return this.NbAutrePiece;
    }
    public Set<Variable> getVariables()
    {
        return this.variables;
    }
    public Set<Constraint> getConstraints()
    {
        return this.contraintes;
    } 
    //Ajoute une variable au set variables 
    public void addVariable(Variable var)
    {
        this.variables.add(var);
    }
    //Retourn le domaine des variables selon la valeur des pièces de la maison
    public Set<Object> getDomain()
    {
        Set<Object>domaine = new HashSet<Object>();
        for(String domEau : getPieceEau())
        {
            domaine.add(domEau);
        }
        for(String domAutre : getAutrePiece())
        {
            domaine.add(domAutre);
        }
        return domaine;
    }
    //AJoute une contrainte au set contraintes
    public void addContrainte(Constraint contrainte)
    {
        this.contraintes.add(contrainte);
    }

    public boolean adjacent(int i,int j,Variable[][] tableau,Variable var)
    {
     
      boolean valide=false;
      if(i!=0)
      {
        valide = valide || tableau[i-1][j].equals(var);
        if(j!=0)
        {
          valide = valide || tableau[i-1][j-1].equals(var);
        }
        if(j<this.longueur-1)
        {
          valide = valide || tableau[i-1][j+1].equals(var);
        }
      }
      if(i<this.largeur-1)
      {
        valide = valide || tableau[i+1][j].equals(var);
    
      }
      if(j!=0)
      {
        valide = valide || tableau[i][j-1].equals(var);
        if(i<this.largeur-1)
        {
          valide = valide || tableau[i+1][j-1].equals(var);
        }
        
      }
      if(j<this.longueur-1)
      {
        valide = valide || tableau[i][j+1].equals(var);
        if(i<largeur-1)
        {
          valide = valide || tableau[i+1][j+1].equals(var);
        }
      }
    
    
      return valide;
    }



}