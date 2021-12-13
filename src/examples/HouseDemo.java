package examples;
import java.util.*;
import java.lang.*;
import representation.*;
import solvers.*;
import planning.*;
import datamining.*;


public class HouseDemo {


 public static void main(String[] args) {

    /******** Dimension de la maison ********/
    /**************************************/
    final int largeur=3;
    final int longueur=3;

    //*******************Création des Pièces d'eau ********************/
    /******************************************************************/
    Set<String>NbPieceEau=new HashSet<String>();
    NbPieceEau.add("Salle de bain");
    NbPieceEau.add("Salle de bain 2");
    NbPieceEau.add("Toilette");
    NbPieceEau.add("Cuisine");
    //Affichage//
    System.out.println("\n\n--------- Pièces d'eau --------- ");
    for(String eau : NbPieceEau )
    {
      System.out.println(eau+" , ");
    }
    System.out.println(" ------------------\n");

    

    //*****************Création autre pièce****************************/
    /******************************************************************/
    Set<String>NbAutrePiece = new HashSet<String>();
    NbAutrePiece.add("Chambre");
    NbAutrePiece.add("Chambre 2");
    NbAutrePiece.add("Chambre 3");
    NbAutrePiece.add("Salon");
    NbAutrePiece.add("Mezzanine");
    NbAutrePiece.add("Chambre 4");
    NbAutrePiece.add("Chambre 5");




    
   



    
    System.out.println("------------Autres pièces --------- ");
    for(String autre : NbAutrePiece )
    {
      System.out.println(autre+" , ");
    }
    System.out.println(" ---------------------- \n");


    //*****************Création d'une maison largeurxlongueur(3x3)***************//
    /******************************************************************/
    HouseExample house= new HouseExample(largeur,longueur,NbPieceEau,NbAutrePiece);

    //Récupération du domaine des variables étant toute les valeurs de pièce possible
    Set<Object>domaine=house.getDomain();
    

    Variable[][] tabVar = new Variable[largeur][longueur];

    //*******************Création variables associé à leur domaine ***********************//
    /******************************************************************/
    for(int i=1;i<=house.getLargeur();i++)
    {
      for(int j=1;j<=house.getLongueur();j++)
      {
        //Création et ajout des variables pièce avec leur domaine dans la maison ainsi que dans le tableau de variable
        house.addVariable(new Variable("Pièce "+i+","+j,domaine));
        tabVar[i-1][j-1]=new Variable("Pièce "+i+","+j,domaine);
      }
    }

    //Affichage variable pièces
    System.out.println("Affichage des variables pièces : \n");
    for(int i=0;i<largeur;i++)
    {
      for(int j=0;j<longueur;j++)
      {
        System.out.println("Variable "+ tabVar[i][j].getName());
      }
    }
    


    //Récupérer les variables pièce pour pouvoir appliqué des contraintes sur les pièces
    List<Variable>ListeVariablesPiece=new ArrayList<Variable>(house.getVariables());

    //*****Création des contraintes portant sur les positions i,j des pièces********************//
    /******************************************************************/
    for(int i=0;i<ListeVariablesPiece.size();i++)
    {
      //Ajout de la contrainte sur l'occupation de tous les emplacement i,j par une pièce        
      Variable var = ListeVariablesPiece.get(i);
      
      house.addContrainte(new DifferenceConstraint(var,null));
      for(int j=i+1;j<ListeVariablesPiece.size();j++)
      {
        //Ajout des contrainte portante sur la différence de chaque pièce entre elles occupant une postion au sein d'une maison
        Variable varNext = ListeVariablesPiece.get(j);
        house.addContrainte(new DifferenceConstraint(var,varNext));
      }
    }
    
    System.out.println("\n");
    //*********************** Contrainte pièce d'eau cote à cote *******/
    /******************************************************************/
      for(int i=0;i<largeur;i++)
      {
       for(int j=0;j<longueur;j++)
       {
        Variable var = tabVar[i][j];
       

        for(int x=0;x<largeur;x++ )
        {

          for(int k=0;k<longueur;k++)
          {
            
                Variable varNext = tabVar[x][k];
                if(!varNext.equals(var))
                {
                  //Création d'une contrainte binaryextension sur la var et varNext
                  BinaryExtensionConstraint contrainteBinaireExtension=new BinaryExtensionConstraint(var,varNext);
                  for(Object value : var.getDomain())
                  {
                    for(Object valueNext : varNext.getDomain())
                    {
                      //Test que les pièces ne soit pas voisines pour spécifié les valeur autorisées
                      if(!house.adjacent(x,k,tabVar,var))
                      {
                        //Si l'une des deux pièces n'eest  pas une  pièces d'eau
                        if(!NbPieceEau.contains(value) || !NbPieceEau.contains(valueNext))
                        {
                         
                          contrainteBinaireExtension.addTuple(value,valueNext);
                        }
                      }
                      //Si voisines autorisés toutes les valeurs
                      else if(house.adjacent(x,k,tabVar,var)) {
                        contrainteBinaireExtension.addTuple(value,valueNext);
                      }
                      
                    }
                  }
                house.addContrainte(contrainteBinaireExtension);
                } 
            
          }

        }
       

       }
      } 
      System.out.println("\n");

     
        
        
       
    
    //*******Création de variables booléennes et ajout au set variable de la maison********************//
    /******************************************************************/
  
    BooleanVariable dalleCoulle=new BooleanVariable("Dalle coulée");
    BooleanVariable solHumide=new BooleanVariable("Sol humide");
    BooleanVariable mursEleve=new BooleanVariable("Murs élevés");
    BooleanVariable toitureTermine=new BooleanVariable("Toiture terminée");
    house.addVariable(dalleCoulle);
    house.addVariable(solHumide);
    house.addVariable(mursEleve);
    house.addVariable(toitureTermine);

    //********************Contrainte d'implication sur les booleanVariable *****************/
      Constraint non_dalle_solMouille = new Implication(dalleCoulle, false, solHumide, true);
      Constraint sol_sec_dallecoulle = new Implication(solHumide, false, dalleCoulle, true);
      Constraint solMouille_nonMur = new Implication(solHumide, true, mursEleve, false);
      Constraint NonMur_NonToit = new Implication(mursEleve, false, toitureTermine, false);
      Constraint Mur_dalle = new Implication(mursEleve,true, dalleCoulle, true);
      Constraint mur_solSec = new Implication(mursEleve, true, solHumide, false);
      Constraint toi_dalle =new Implication(toitureTermine, true, dalleCoulle, true);
      Constraint toi_sol =new Implication(toitureTermine, true, solHumide, false);
      Constraint toi_mur = new Implication(toitureTermine, true, mursEleve, true);

      house.addContrainte(non_dalle_solMouille);
      house.addContrainte(sol_sec_dallecoulle);
      house.addContrainte(solMouille_nonMur);
      house.addContrainte(NonMur_NonToit);
      house.addContrainte(Mur_dalle);
      house.addContrainte(mur_solSec);
      house.addContrainte(toi_dalle);
      house.addContrainte(toi_sol);
      house.addContrainte(toi_mur);
      System.out.println("........Résolution.....................");

      System.out.println("\n------------------Solution --------------------------- \n");
      Map<Variable,Object>Solution = new MACSolver(house.getVariables(),house.getConstraints()).solve();
      if(Solution!=null)
      {
        for(Variable var : Solution.keySet())
        {
          System.out.println("Nom variable : " +var.getName() +" valeur : "+Solution.get(var));
        }
      }

      else {
        System.out.println("Pas de solution pour ce type de problème.");
        return;
      }
      
/*********************************************** PLANNIFICATION *****************************************************/
//********************Etat initial pour la plan  ******************//
System.out.println("\n.......Plannification...... \n");
Map<Variable,Object>etatInitial = new HashMap<>();

  //Recuperer les premières pièces par rapport au nombre possible de pièce d'eau
  List<Variable>listeFirstVar = new ArrayList<Variable>();
  List<Variable>listeVarNext = new ArrayList<Variable>();
  System.out.println("\n");

  int valeurDepartNbEau=0;
  for(int i=0;i<largeur;i++)
  {
    
    for(int j=0;j<longueur;j++)
    {
    
      if(valeurDepartNbEau<NbPieceEau.size())
      {
        listeFirstVar.add(tabVar[i][j]);
        System.out.println(tabVar[i][j].getName());
        
      }else {
        listeVarNext.add(tabVar[i][j]);
        System.out.println(tabVar[i][j].getName());

      }
      valeurDepartNbEau++;
    }
  }
    for(int i=0;i<listeFirstVar.size();i++)
    {
      //Ajout des variables pièces sans valeur dans le domaine
      etatInitial.put(listeFirstVar.get(i),null);
    }
    for(int i=0;i<listeVarNext.size();i++)
    {
      //Ajout des variables pièces sans valeur dans le domaine
      etatInitial.put(listeVarNext.get(i),null);
    }

    //***Ajout des variables booleane avec valeur non satisfaisante*********/
    etatInitial.put(dalleCoulle,false);
    etatInitial.put(solHumide,false);
    etatInitial.put(mursEleve,true);
    etatInitial.put(toitureTermine,true);

   
    
    /****************************Ajout et création des Actions menant au but  ******/
    Set<Action>action = new HashSet<>();
    Map<Variable,Object> precondition = new HashMap<>();
    //************Couler une dalle  ********/
    Map<Variable,Object>effet=new HashMap<>();
    effet.put(dalleCoulle,true);
    effet.put(solHumide,true);
    int cout=8;
    action.add(new BasicAction(precondition,effet,cout));


    //**************** Attendre que dale et sol seche*******/
    precondition = new HashMap<>();
    effet=new HashMap<>();
    precondition.put(dalleCoulle,true);
    precondition.put(solHumide,true);
    effet.put(solHumide,true);
    cout=4;
    action.add(new BasicAction(precondition,effet,cout));

    //************** Elever les murs ******/
    precondition = new HashMap<>();
    effet=new HashMap<>();
    precondition.put(dalleCoulle,true);
    precondition.put(solHumide,false);
    effet.put(mursEleve,true);
    action.add(new BasicAction(precondition,effet,cout));

    //**************Terminer toiture  */

    precondition = new HashMap<>();
    effet=new HashMap<>();
    precondition.put(dalleCoulle,true);
    precondition.put(solHumide,false);
    precondition.put(mursEleve,true);
    effet.put(toitureTermine,true);
    action.add(new BasicAction(precondition,effet,cout));




     

  //****************************Création de l'état but à partir de la solution du solver ainsi que les valeurs des variable boolean optimale */
  
  Map<Variable,Object>etatBut = new HashMap<Variable,Object>(Solution);
  
  //Chercher  solution spécifique décocher le commentaire //

  /*Map<Variable,Object>etatBut = new HashMap<Variable,Object>();
  etatBut.put(tabVar[0][0],"Salle de bain");
  etatBut.put(tabVar[0][1],"Salle de bain2");
  etatBut.put(tabVar[0][2],"Cuisine");
  etatBut.put(tabVar[1][0],"Toilette");
  etatBut.put(tabVar[1][1],"Chambre");
  etatBut.put(tabVar[1][2],"Chambre 1");
  etatBut.put(tabVar[2][0],"Chambre 2");
  etatBut.put(tabVar[2][1],"Chambre 3");
  etatBut.put(tabVar[2][2],"Mezzanine");*/

  etatBut.put(dalleCoulle,true);
  etatBut.put(solHumide,false);
  etatBut.put(mursEleve,true);
  etatBut.put(toitureTermine,true);
  Goal but = new BasicGoal(etatBut);


  /***********************************************************/


/******************************Pièce eau en premier************/

/*for(int i=0;i<ListeVariablesPiece.size();i++)
{
  precondition = new HashMap<>();
  precondition.put(dalleCoulle,true);
  precondition.put(solHumide,false);
  Variable var = ListeVariablesPiece.get(i);
  precondition.put(var,null);
  for(Object value : var.getDomain())
  {
    effet = new HashMap<>();
    effet.put(var,value);
    action.add(new BasicAction(precondition,effet,5));
  }
}*/


//Pièce eau defintion des actions
for(int i=0;i<listeFirstVar.size();i++)
{
  Variable pieceEau = listeFirstVar.get(i);
  
  for(Object valeurdom : pieceEau.getDomain())
  {
    if(NbPieceEau.contains(valeurdom))
    {
      precondition = new HashMap<>();
      precondition.put(dalleCoulle,true);
      precondition.put(solHumide,false);
      precondition.put(pieceEau,null);
      for(int k=0;k<listeVarNext.size();k++)
      {
        precondition.put(listeVarNext.get(k),null);
      }
      effet=new HashMap<>();
      effet.put(pieceEau,valeurdom);
      action.add(new BasicAction(precondition,effet,5));
    }
  }
}

//Pièces restante qui ne seront pas des pièces d'eau //
for(int i=0;i<listeVarNext.size();i++)
{
  Variable varRestante = listeVarNext.get(i);
  for(Object valeurDomRestante : varRestante.getDomain())
  {
    if(!NbPieceEau.contains(valeurDomRestante))
    {
      precondition = new HashMap<>();
      precondition.put(dalleCoulle,true);
      precondition.put(solHumide,false);
      precondition.put(varRestante,null);
      
      effet=new HashMap<>();
      effet.put(varRestante,valeurDomRestante);
      action.add(new BasicAction(precondition,effet,5));
    }
  }

}

/*****************************************************************/




  /*************************PLAN *************************** */
  System.out.println("\n.... Recherche plan d'action menant au but ....\n");
    DFSPlanner dfs= new DFSPlanner(etatInitial,action,but);
    List<Action> plan =  dfs.plan();
    if(plan!=null)
    {
      System.out.println("------------Plan---------------");
      int i=0;
      for(Action a1 : plan)
      {
        System.out.println("Action " + i + " : " + a1);
        i++;
      }
    }
    else {
      System.out.println("Plan non trouvé.");
    }



//******************************************Extraction des données **************//
//Création database et ajout des  
Database base =new Database(house.getVariables());
  for(int i=0;i<15;i++)
  {
    base.add(new BacktrackSolver(house.getVariables(), house.getConstraints()).solve());
    
  }
  for(int j=0;j<5;j++)
    {
      base.add(new MACSolver(house.getVariables(), house.getConstraints()).solve());
    }
  BooleanDatabase databoolean = base.propositionalize();
  System.out.println("----Extraction des règles fréquence : 0.4 et confiance à 0.7---\n");
  BruteForceAssociationRuleMiner bruteForce = new BruteForceAssociationRuleMiner(databoolean);
	Set<AssociationRule> regles = new HashSet<AssociationRule>(bruteForce.extract( 0.4f,  0.7f));
		System.out.println("-----Règle extraites ----\n");
    for(AssociationRule regle : regles) 
    {
		  System.out.println(regle);
		}


    }
}