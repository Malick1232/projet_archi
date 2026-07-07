package com.projetarchi.clientadmin.service;

import com.projetarchi.clientadmin.model.Utilisateur;

import java.util.List;

/**
 * SQUELETTE - à compléter dès que le WSDL de la personne 2 est disponible.
 *
 * Marche à suivre (détaillée dans le README.md du projet) :
 *  1. Lancer l'appli site-actualites (rôle 2) pour exposer :
 *       http://localhost:8080/ws/utilisateurs.wsdl
 *  2. Générer les classes clientes avec wsimport, par exemple :
 *       wsimport -keep -p com.projetarchi.clientadmin.soap.generated ^
 *           http://localhost:8080/ws/utilisateurs.wsdl
 *     (ou décommenter le plugin jaxws-maven-plugin dans le pom.xml et lancer
 *      "mvn generate-sources")
 *  3. Dans le code généré, tu obtiens une classe "Service" (le port SOAP) avec
 *     des méthodes qui correspondent aux 5 opérations du contrat :
 *       authentifier, listerUtilisateurs, ajouterUtilisateur,
 *       modifierUtilisateur, supprimerUtilisateur
 *  4. Remplace le contenu des méthodes ci-dessous par des appels à ce port
 *     généré, en convertissant les objets générés <-> Utilisateur.
 *  5. Dans Main.java, remplace "new MockServiceUtilisateurs()" par
 *     "new SoapServiceUtilisateurs()". C'est le SEUL changement nécessaire
 *     dans le reste de l'application grâce à l'interface ServiceUtilisateurs.
 */
public class SoapServiceUtilisateurs implements ServiceUtilisateurs {

    // private final UtilisateursServicePort port; // type généré par wsimport

    public SoapServiceUtilisateurs() {
        // TODO : instancier le client SOAP généré, par ex. :
        // UtilisateursService service = new UtilisateursService();
        // this.port = service.getUtilisateursServicePort();
        throw new UnsupportedOperationException(
                "SoapServiceUtilisateurs n'est pas encore implémenté : " +
                "attends le WSDL de la personne 2 puis suis les instructions du README.");
    }

    @Override
    public Utilisateur authentifier(String login, String motDePasse) throws ServiceException {
        try {
            // TODO : appeler port.authentifier(login, motDePasse) et convertir la réponse
            throw new UnsupportedOperationException("À implémenter");
        } catch (Exception e) {
            throw traduireErreurReseau(e);
        }
    }

    @Override
    public List<Utilisateur> lister(String jeton) throws ServiceException {
        try {
            // TODO : appeler port.listerUtilisateurs(jeton)
            throw new UnsupportedOperationException("À implémenter");
        } catch (Exception e) {
            throw traduireErreurReseau(e);
        }
    }

    @Override
    public Utilisateur ajouter(String jeton, Utilisateur utilisateur) throws ServiceException {
        try {
            // TODO : appeler port.ajouterUtilisateur(jeton, ...)
            throw new UnsupportedOperationException("À implémenter");
        } catch (Exception e) {
            throw traduireErreurReseau(e);
        }
    }

    @Override
    public void modifier(String jeton, Utilisateur utilisateur) throws ServiceException {
        try {
            // TODO : appeler port.modifierUtilisateur(jeton, ...)
            throw new UnsupportedOperationException("À implémenter");
        } catch (Exception e) {
            throw traduireErreurReseau(e);
        }
    }

    @Override
    public void supprimer(String jeton, Long id) throws ServiceException {
        try {
            // TODO : appeler port.supprimerUtilisateur(jeton, id)
            throw new UnsupportedOperationException("À implémenter");
        } catch (Exception e) {
            throw traduireErreurReseau(e);
        }
    }

    /**
     * Traduit toute exception technique (SOAPFaultException, connexion refusée,
     * timeout...) en ServiceException lisible pour l'IHM. C'est ici qu'on gère
     * "service injoignable" et "jeton invalide" renvoyé sous forme de faute SOAP.
     */
    private ServiceException traduireErreurReseau(Exception e) {
        String message = e.getMessage() == null ? "" : e.getMessage();
        if (message.toLowerCase().contains("jeton")) {
            return new ServiceException(ServiceException.Type.JETON_INVALIDE,
                    "Jeton invalide ou inactif.", e);
        }
        return new ServiceException(ServiceException.Type.SERVICE_INJOIGNABLE,
                "Le service SOAP est injoignable. Vérifie que le site est démarré.", e);
    }
}
