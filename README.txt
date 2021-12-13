
Liste étudiant du groupe 6 TD 3A  : 

Krimi Ibrahim 22011592

Projet 
Description de l'application fil rouge
On s'intéresse à la construction de maisons (dans une version très simplifiée), et à des règles telles que :

on ne construit pas le toit avant les murs
on ne construit pas les murs sur un sol humide
on regroupe les salles d'eau (cuisines, salles de bains)
etc.
Pour une construction donnée, on verra la surface de la construction comme un ensemble de positions (i,j) dans une grille, chacune pouvant accueillir une pièce. Par exemple, pour une maison rectangulaire de largeur 3 (i = 1, 2, 3) et de longueur 4 (j = 1, 2, 3, 4), on pourra placer une salle de bains en (1,2), un salon en (2,3), une première chambre en (2,2), une seconde chambre en (3,2), etc. On considérera qu'une pièce occupe au plus une position (pas de notion de taille des pièces).
Additionnellement, on s'intéressera aux faits que la dalle soit ou non coulée, que le sol soit humide ou sec, que les murs soient élevés ou non, et que la toiture soit terminée ou non.

La modélisation que nous utiliserons se basera sur les variables suivantes :

pour chaque position (i,j) dans la construction, une variable « piècei,j » ayant pour domaine l'ensemble des pièces que l'on envisage de placer dans la maison (par exemple, le domaine {« salle de bains », « cuisine », « séjour », « chambre1 », « chambre2 », « WC »}),
des variables booléennes « dalle coulée », « sol humide », « murs élevés », « toiture terminée ».
Finalement, nous considérerons qu'une construction doit respecter les règles suivantes :

chaque pièce envisagée occupe au plus une position
une pièce est envisagée à chaque position
deux pièces d'eau (salles de bains, cuisines, toilettes) doivent nécessairement être envisagées côte à côte (en (1,1) et (2,2) par exemple, mais pas en (1,1) et (1,3)),
on réalise nécessairement « dalle coulée », « sol sec » (représenté par non(« sol humide »)),  « murs élevés » et « toiture terminée » dans cet ordre.
Ainsi, une construction dans laquelle la dalle est coulée et sèche (et donc, où le sol est sec), dans laquelle les murs ne sont pas élevés et la toiture n'est pas terminée, et dans laquelle on envisage de placer une chambre différente dans chaque position, est valide. En revanche, une construction dans laquelle la dalle n'est pas coulée mais les murs sont élevés, n'est pas valide.

Pour la planification, on considérera les actions suivantes :

couler une dalle,
attendre (que la dalle, et donc le sol, sèche),
élever les murs,
terminer la toiture,
aménager la pièce p en position (i,j) (pour tous p et tous (i,j)), en respectant la règle que les pièces d'eau doivent toutes être aménagées avant de commencer les autres pièces (pour passer les tuyaux dans les cloisons),
annuler l'aménagement de la pièce p en position (i,j) (pour tous p et tous (i,j)), si elle a été construite trop précocement,
et, à titre d'exemples, les objectifs suivants :

construire une maison valide (et de façon à ce que toutes les étapes de la construction soient aussi valides - à l'exception de certaines pièces qui peuvent ne pas encore être aménagées),
construire une maison donnée (liste des pièces) en perdant le moins de temps possible.
Enfin, pour l'extraction de connaissances, on cherchera à retrouver, dans des bases de données décrivant des maisons, les patterns que l'on retrouve souvent, par exemple :

le fait que beaucoup de maisons ont une première chambre en (1,1) et une seconde en (1,2),
le fait que les maisons qui ont une salle de bains en (2,2) ont souvent une cuisine en (2,3),
etc.
