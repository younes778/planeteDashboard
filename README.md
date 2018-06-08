Planète Dashboard
===================================
## Fonctionnement

PLANÈTE DASHBOARD ou tableau de bord permet la centralisation en un seul point, d’un ensemble de données permettant de piloter efficacement votre activité. 
La complexité des données et leur multiplication complique la synthèse des données et la mise en place de tableaux analytiques. PLANÈTE DASHBOARD vous permet de construire des tableaux de bord avec des indicateurs (KPI) pertinents ayant pour objectif :

• De vous aider à augmenter votre chiffre d'affaires;.

• D’amplifier la satisfaction de vos clients. 

• D’optimiser vos achats et vos stocks.

• De rendre votre entreprise plus agile et réactive. 


## Architecture fonctionnelle
Planète Dashboard est une application qui se répartie en plusieurs parties :

*	[Partie Applicative] elle se lance au niveau de smartphone (ayant comme Système d’exploitation Android), permettant de représenter les indicateurs sous forme d’un Dashboard avec des graphes explicites.

*	[Base de données minimale]  portable avec la partie applicative, représentant les données minimales qui permet de lancer l’application. Cette dernière se met à jour régulièrement. Cette dernière est développé avec l’outil Realm une base de données très puissante dédiée pour mobile basée sur SQLite.

*	[Serveur de synchronisation]  Il permet de lier l’application se trouvant sur smartphone avec l’ERP et les bases de données des clients d’une manière efficace. Ainsi, il permet d’unifier l’accès aux données en permettant à n’importe quel utilisateur mobile d’accéder à une base de données spécifiques tout en respectant les contraintes de sécurité. Ce serveur est basé sur Spring une technologie fondée sur une architecture RestFull. Le langage de programmation utilisé à ce niveau est Java.

## Technologies utilisées
Planète Dashboard est une application qui se répartie en plusieurs parties :

*	[Android studio - Android & Java](https://developer.android.com/about/) l'application est développée sous l'environement Android qui utilise le language de programation Java.

*	[Realm](https://realm.io/docs/java/latest/)  portable avec la partie applicative,  l’outil Realm une base de données très puissante dédiée pour mobile basée sur SQLite, plus fluide et simple à utiliser.

*	[SqlServer](https://www.microsoft.com/en-us/sql-server/sql-server-2017)  L'ERP se trouve au niveau de client est basé sur SQL server, pour rêqueter ce dernier il faut utiliser le language SQL.

* [Spring](https://spring.io/) Permet de mettre en place un serveur respectant l'architecture RESTFull. 
