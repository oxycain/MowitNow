Ce projet est une simulation d'un système de tondeuse automatique pour des pelouses rectangulaires. Le système est capable de lire les spécifications de la pelouse et de la tondeuse ainsi que les instructions de navigation à partir d'un fichier texte, d'exécuter ces instructions pour déplacer les tondeuses en conséquence, et d'afficher les positions et orientations finales des tondeuses via une interface graphique utilisateur (GUI).


Modules
---------------

Le projet est divisé en plusieurs packages Scala qui ensemble forment un système de simulation de tondeuse cohérent :

-   Package Models : Contient tous les modèles de données utilisés dans le projet, y compris :

    -   `Orientation` : Enumération pour les orientations de la tondeuse (Nord, Est, Sud, Ouest).
    -   `Pelouse` : Représente la pelouse avec ses dimensions.
    -   `Position` : Représente une coordonnée sur la pelouse.
    -   `Tondeuse` : Représente la tondeuse avec sa position et son orientation.
-   Package Services : Contient la logique métier pour analyser les fichiers d'entrée et exécuter les instructions de navigation :

    -   `Parser` : Lit et analyse le fichier d'instructions pour extraire les dimensions de la pelouse et les spécifications des tondeuses.
    -   `Jardinage` : Exécute les instructions de mouvement et de rotation pour les tondeuses.
-   Package GUI : Fournit une interface graphique pour interagir avec la simulation :

    -   `TondeuseGUI` : Configure l'interface utilisateur, y compris les boutons et les affichages d'images pour la simulation.

Environnement Technique
-----------------------

-   Langage de programmation : Scala -version 
-   Version de Scala : 2.11.12
-   Librairies :
    -   ScalaTest 3.0.0 pour les tests unitaires
    -   Scala Swing 3.2.17 pour l'interface graphique

Installation et Configuration
-----------------------


1.  Prérequis :

    -   Scala Build Tool (sbt)
    -   JDK (installé et configuré pour le projet). Pour le developpement de ce projet nous avons utiliser JDK 17.
    -   Scala version 2.11.12
    -   Git
2.  Cloner le dépôt :

    ```
    git clone https://github.com/oxycain/MowitNow.git
    ```

3.  Construire le Projet :

    `sbt compile`

#### Exécution de la Simulation

Pour exécuter la simulation, vous pouvez utiliser sbt pour démarrer l'application GUI :

`sbt run`

Cette commande démarrera l'interface utilisateur. Depuis l'interface, vous pouvez cliquer sur le bouton "Jardiner" puis charger le fichier d'instructions à l'aide du sélecteur de fichiers.

-> Si vous ne souhaitez pas utiliser la ligne de commande et sbt pour l'execution, vous pouvez très bien run la fonction TondeuseGUI pour lancer l'application.

#### Format du Fichier d'Entrée

Le fichier d'entrée doit être structuré comme suit :

-   La première ligne correspond aux coordonnées du coin supérieur droit de la pelouse,
celles du coin inférieur gauche sont supposées être (0,0).
-   Chaque paire de lignes suivante représente une tondeuse sur la pelouse. La première ligne de chaque paire donne la position de départ et l'orientation de la tondeuse, et la seconde ligne fournit une série d'instructions ('A' pour avancer, 'G' pour tourner à gauche, 'D' pour tourner à droite).

Exemple :

```
5 5
1 2 N
GAGAGAGAA
3 3 E
AADAADADDA
```

Tests Unitaires
---------------

Le projet comprend des tests unitaires pour assurer la fiabilité des fonctionnalités clés. Les tests vérifient la capacité du système à lire et interpréter les fichiers d'instructions, à exécuter les instructions de déplacement et de rotation correctement, et à gérer divers cas d'erreurs. Les fichiers de test sont stockés dans `src/test/resources` et contiennent divers scénarios de simulation pour tester les comportements attendus et les erreurs.


Présentation de l'Interface Graphique
-------------------------------------

L'interface graphique, développée avec Scala Swing, propose une interaction simple et intuitive :

-   Panneau de pelouse : Affiche une image de la pelouse où la simulation se déroule.
-   Bouton 'Jardiner' : Permet à l'utilisateur de lancer la simulation  en lancant le sélecteur de fichiers.
-   Sélecteur de fichiers : Permet de choisir un fichier contenant les instructions de navigation pour les tondeuses.

### Gestion des Erreurs

Les erreurs telles que les fichiers manquants, les formats de fichier incorrects, ou les instructions invalides sont gérées de manière élégante, en affichant des messages d'erreur dans l'interface graphique.

