version Java: 11

Dans le package fr.bastion.rest:

Le point d'entrée est la class 'Program'.
Celle-ci contient le 'main', qui va instancier 'RestServiceManagement' et lui appliquer deux méthodes.
La méthode 'setParameters' et 'messaging'.

'setParameters' prend en entrée une chaine de charactère.
Il s'agit de l'emplacement dans lequel se situe le fichier .xml.
Ce fichier xml contient tous les paramètres indispensable à l'envoie d'un message REST:
la méthode, l'url, le header, le body et la localisation du fichier de sortie.
La méthode 'setParameter' va donc parser le fichier .xml pour récupérer les paramètres.

'messaging' instancie la classe 'ApiRest' et lui applique sa methode 'messageRest'
qui prend en paremètres ceux qu'on vient de récupérer du fichier .xml grâce à la méthode 'setParameters'.

'messageRest' est très générique et fait plusieurs choses.
-D'abords, la méthode 'checkingFile' regarde si le fichier de sortie (outputFile) existe, si ce n'est pas le cas, le fichier est crée.
-Ensuite, selon la méthode demandée (POST, GET, PUT...), la bonne classe est instanciée (HttpPost, HttpGet, HttpPut...).
-Le header et le body (si il y en a) sont envoyé à l'url.
-La réponse est récupérée, puis copiée dans le fichier de sortie (outputFile), précédée de la date.

Il n'y a donc qu'à "run" la classe 'Program'.