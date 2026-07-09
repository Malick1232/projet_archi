# CONTRAT.md — Services web (Rôle 2)

Ce document décrit précisément les services REST et SOAP pour permettre à la
personne 3 (application cliente) de coder contre ce contrat sans attendre.

Toutes les URLs supposent le site lancé en local : `http://localhost:8080`.

---

## 1. Service REST — base `/api`

Format de réponse choisi via le paramètre `?format=json` ou `?format=xml`.
Sans paramètre : JSON par défaut. Accessible sans authentification.

### GET /api/articles?format=json|xml
Liste tous les articles, du plus récent au plus ancien.

**Exemple JSON** — `GET /api/articles?format=json`
```json
{
  "articles": [
    {
      "id": 10,
      "titre": "Article 10",
      "resume": "Résumé 10",
      "contenu": "Contenu complet 10",
      "datePublication": "2026-07-09T10:15:00",
      "categorie": "Sport",
      "auteur": "Editeur Diallo"
    }
  ]
}
```

**Exemple XML** — `GET /api/articles?format=xml`
```xml
<articles>
    <article id="10">
        <titre>Article 10</titre>
        <resume>Résumé 10</resume>
        <contenu>Contenu complet 10</contenu>
        <datePublication>2026-07-09T10:15:00</datePublication>
        <categorie>Sport</categorie>
        <auteur>Editeur Diallo</auteur>
    </article>
</articles>
```

### GET /api/articles/parcategorie?format=json|xml
Toutes les catégories, chacune avec la liste de ses articles.

```json
{
  "categories": [
    {
      "id": 1,
      "libelle": "Sport",
      "description": "Actualités sportives",
      "articles": [ { "id": 1, "titre": "Article 1", "...": "..." } ]
    }
  ]
}
```

### GET /api/categories/{id}/articles?format=json|xml
Articles d'une seule catégorie (par son id).

- `200 OK` avec la catégorie et ses articles si l'id existe.
- `404 Not Found` si l'id n'existe pas.

```json
{
  "id": 1,
  "libelle": "Sport",
  "description": "Actualités sportives",
  "articles": [ { "id": 1, "titre": "Article 1", "...": "..." } ]
}
```

---

## 2. Service SOAP — WSDL `/ws/utilisateurs.wsdl`

URL complète : `http://localhost:8080/ws/utilisateurs.wsdl`
Namespace : `http://siteactualites.com/soap/utilisateurs`

Pour générer le client (rôle 3) :
```
wsimport -keep -p com.clientadmin.soap http://localhost:8080/ws/utilisateurs.wsdl
```

**Règle commune** : toute opération sauf `authentifier` exige un `jeton` valide
(existe en base et `actif = true`). Sinon → faute SOAP `"Jeton invalide"`.

### authentifier(login, motDePasse) → role
Ne nécessite pas de jeton. Retourne le rôle (`ADMIN` ou `EDITEUR`) si le
login/mot de passe sont corrects, sinon faute SOAP `"Login ou mot de passe incorrect"`.

Requête :
```xml
<authentifierRequest xmlns="http://siteactualites.com/soap/utilisateurs">
    <login>admin</login>
    <motDePasse>motdepasseClair</motDePasse>
</authentifierRequest>
```
Réponse :
```xml
<authentifierResponse xmlns="http://siteactualites.com/soap/utilisateurs">
    <role>ADMIN</role>
</authentifierResponse>
```

### listerUtilisateurs(jeton) → liste d'utilisateurs
```xml
<listerUtilisateursRequest xmlns="http://siteactualites.com/soap/utilisateurs">
    <jeton>123456789abcdef...1234</jeton>
</listerUtilisateursRequest>
```
Réponse : liste de `<utilisateur>` avec `id, nom, prenom, login, role,
dateCreation` — **jamais le mot de passe**.

### ajouterUtilisateur(jeton, utilisateur) → utilisateur créé
`utilisateur` en entrée contient : `nom, prenom, login, motDePasse, role`
(le mot de passe est haché côté serveur avant stockage).

### modifierUtilisateur(jeton, utilisateur) → utilisateur modifié
`utilisateur` en entrée contient : `id, nom, prenom, login, motDePasse
(optionnel), role`. Si `motDePasse` est absent ou vide, le mot de passe
existant n'est pas changé.

### supprimerUtilisateur(jeton, id) → succes (booléen)

---

## 3. Jeton de test (data.sql)

Un jeton actif est déjà inséré pour les tests :
```
123456789abcdef123456789abcdef123456789abcdef123456789abcdef1234
```
Créé par l'utilisateur `admin` (id=1).

## 4. Comptes de test (data.sql)
| login   | rôle    |
|---------|---------|
| admin   | ADMIN   |
| editeur | EDITEUR |

(mots de passe en clair à demander à la personne 1, qui a généré les hash BCrypt)

## 5. Comment tester

- **REST** : navigateur ou Postman, GET simple, pas d'authentification.
- **SOAP** : SoapUI (créer un nouveau projet SOAP à partir du WSDL ci-dessus),
  ou `curl` :
```bash
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  --data '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
    <soapenv:Body>
      <authentifierRequest xmlns="http://siteactualites.com/soap/utilisateurs">
        <login>admin</login>
        <motDePasse>...</motDePasse>
      </authentifierRequest>
    </soapenv:Body>
  </soapenv:Envelope>'
```
