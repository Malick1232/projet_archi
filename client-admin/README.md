# client-admin — Application cliente de gestion des utilisateurs (Rôle 3)

Application Java Swing indépendante (pas de Spring) qui permet à un
administrateur de gérer les utilisateurs via le service SOAP exposé par
`site-actualites` (rôle 2), conformément au contrat défini dans
`CONTRAT.md` / section 4 du guide de répartition.

## Pourquoi ce projet fonctionne déjà sans attendre le SOAP

Toute l'IHM (`LoginFrame`, `MainFrame`, `UtilisateurFormDialog`) ne dépend
que de l'interface `ServiceUtilisateurs`. Deux implémentations existent :

- `MockServiceUtilisateurs` — données en dur, utilisée **par défaut** dans
  `Main.java`. Elle simule l'authentification, la vérification du jeton
  et le CRUD, exactement comme le fera le vrai SOAP.
- `SoapServiceUtilisateurs` — squelette à compléter quand le WSDL de la
  personne 2 sera disponible (voir plus bas).

Basculer du mock au réel = **changer une seule ligne** dans `Main.java`.

## Comptes de test (mode Mock)

| Login   | Mot de passe | Rôle    | Accès à l'appli client |
|---------|---------------|---------|------------------------|
| admin   | admin123      | ADMIN   | Oui                    |
| editeur | editeur123    | EDITEUR | Non ("Accès refusé")   |

Jeton de test accepté par le mock : `TOKEN-TEST-1234` (déjà dans
`src/main/resources/config.properties`).

## Lancer l'application

Avec Maven (si `mvn` est installé) :
```bash
mvn compile exec:java
```

Sans Maven, avec javac/java directement :
```bash
find src/main/java -name "*.java" > sources.txt
javac -d target/classes -encoding UTF-8 @sources.txt
cp -r src/main/resources/* target/classes/
java -cp target/classes com.projetarchi.clientadmin.Main
```

## Basculer vers le vrai service SOAP (quand le WSDL est prêt)

1. Demander à la personne 2 (rôle services web) de démarrer `site-actualites`
   et de confirmer l'URL : `http://localhost:8080/ws/utilisateurs.wsdl`.
2. Générer le client SOAP avec `wsimport` :
   ```bash
   wsimport -keep -p com.projetarchi.clientadmin.soap.generated \
       http://localhost:8080/ws/utilisateurs.wsdl
   ```
   (ou décommenter le plugin `jaxws-maven-plugin` dans `pom.xml` et lancer
   `mvn generate-sources`).
3. Compléter `SoapServiceUtilisateurs.java` : remplacer chaque `TODO` par
   un appel au port généré, en convertissant les objets générés en
   `Utilisateur` (voir les commentaires détaillés dans le fichier).
4. Dans `Main.java`, remplacer :
   ```java
   ServiceUtilisateurs service = new MockServiceUtilisateurs();
   ```
   par :
   ```java
   ServiceUtilisateurs service = new SoapServiceUtilisateurs();
   ```
5. Demander à un administrateur (rôle 1, page admin du site) de générer un
   jeton réel, et le coller dans `src/main/resources/config.properties`
   (clé `jeton`).
6. Relancer et vérifier : connexion admin → CRUD utilisateurs → tester la
   désactivation du jeton depuis la page admin → vérifier que l'appli
   cliente affiche bien "Jeton invalide" et non une erreur brute.

## Gestion des erreurs (déjà en place)

`ServiceException` distingue 4 cas, chacun affiché avec un message clair
(jamais de stacktrace) :
- `AUTHENTIFICATION_ECHOUEE` — login/mot de passe incorrect
- `JETON_INVALIDE` — jeton inexistant, désactivé, ou faute SOAP "Jeton invalide"
- `SERVICE_INJOIGNABLE` — serveur éteint, réseau coupé, timeout
- `ERREUR_INCONNUE` — autres cas (ex. utilisateur introuvable)

## Structure

```
client-admin/
├── pom.xml
├── src/main/java/com/projetarchi/clientadmin/
│   ├── Main.java
│   ├── config/AppConfig.java
│   ├── model/Utilisateur.java
│   ├── service/
│   │   ├── ServiceUtilisateurs.java      (interface = le contrat)
│   │   ├── ServiceException.java
│   │   ├── MockServiceUtilisateurs.java  (actif par défaut)
│   │   └── SoapServiceUtilisateurs.java  (squelette à compléter)
│   └── ui/
│       ├── LoginFrame.java
│       ├── MainFrame.java
│       ├── UtilisateurFormDialog.java
│       └── UtilisateurTableModel.java
└── src/main/resources/config.properties
```

## Ce qu'il reste à faire pour finir le rôle 3

- [ ] Compléter `SoapServiceUtilisateurs` dès que le WSDL est publié
- [ ] Rôle intégrateur : vérifier les merges vers `main` tous les 2 jours
- [ ] Test de bout en bout final (checklist section 10 du guide) :
      créer un article → le retrouver via REST (JSON puis XML) → générer
      un jeton → gérer un utilisateur via cette appli → désactiver le
      jeton et vérifier le rejet
- [ ] Rédiger le README **global** du dépôt (prérequis, lancement des deux
      projets, comptes de test, URL des services) — pas seulement celui-ci
