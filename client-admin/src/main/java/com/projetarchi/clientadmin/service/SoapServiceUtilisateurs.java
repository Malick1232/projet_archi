package com.projetarchi.clientadmin.service;

import com.projetarchi.clientadmin.model.Utilisateur;
import com.projetarchi.clientadmin.soap.generated.*;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation réelle de ServiceUtilisateurs, basée sur les classes générées
 * par wsimport à partir de http://localhost:8080/ws/utilisateurs.wsdl
 * (package com.projetarchi.clientadmin.soap.generated).
 *
 * Pour rebasculer en mock : remplacer dans Main.java
 *   new SoapServiceUtilisateurs() -> new MockServiceUtilisateurs()
 */
public class SoapServiceUtilisateurs implements ServiceUtilisateurs {

    private final UtilisateursPort port;

    public SoapServiceUtilisateurs() {
        UtilisateursPortService service = new UtilisateursPortService();
        this.port = service.getUtilisateursPortSoap11();
    }

    @Override
    public Utilisateur authentifier(String login, String motDePasse) throws ServiceException {
        try {
            AuthentifierRequest requete = new AuthentifierRequest();
            requete.setLogin(login);
            requete.setMotDePasse(motDePasse);

            AuthentifierResponse reponse = port.authentifier(requete);

            // Le contrat SOAP ne renvoie que le rôle (pas l'id/nom/prénom) : ça suffit
            // pour la vérification ADMIN faite dans LoginFrame.
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setLogin(login);
            utilisateur.setRole(reponse.getRole());
            return utilisateur;

        } catch (SOAPFaultException e) {
            // Le service renvoie une faute SOAP en cas de login/mot de passe incorrect
            throw new ServiceException(ServiceException.Type.AUTHENTIFICATION_ECHOUEE,
                    "Login ou mot de passe incorrect.", e);
        } catch (WebServiceException e) {
            throw traduireErreurReseau(e);
        }
    }

    @Override
    public List<Utilisateur> lister(String jeton) throws ServiceException {
        try {
            ListerUtilisateursRequest requete = new ListerUtilisateursRequest();
            requete.setJeton(jeton);

            ListerUtilisateursResponse reponse = port.listerUtilisateurs(requete);

            List<Utilisateur> resultat = new ArrayList<>();
            for (UtilisateurInfo info : reponse.getUtilisateur()) {
                resultat.add(versUtilisateur(info));
            }
            return resultat;

        } catch (SOAPFaultException e) {
            throw traduireFauteSoap(e);
        } catch (WebServiceException e) {
            throw traduireErreurReseau(e);
        }
    }

    @Override
    public Utilisateur ajouter(String jeton, Utilisateur utilisateur) throws ServiceException {
        try {
            UtilisateurCreation creation = new UtilisateurCreation();
            creation.setNom(utilisateur.getNom());
            creation.setPrenom(utilisateur.getPrenom());
            creation.setLogin(utilisateur.getLogin());
            creation.setMotDePasse(utilisateur.getMotDePasse());
            creation.setRole(utilisateur.getRole());

            AjouterUtilisateurRequest requete = new AjouterUtilisateurRequest();
            requete.setJeton(jeton);
            requete.setUtilisateur(creation);

            AjouterUtilisateurResponse reponse = port.ajouterUtilisateur(requete);
            return versUtilisateur(reponse.getUtilisateur());

        } catch (SOAPFaultException e) {
            throw traduireFauteSoap(e);
        } catch (WebServiceException e) {
            throw traduireErreurReseau(e);
        }
    }

    @Override
    public void modifier(String jeton, Utilisateur utilisateur) throws ServiceException {
        try {
            UtilisateurModification modification = new UtilisateurModification();
            modification.setId(utilisateur.getId());
            modification.setNom(utilisateur.getNom());
            modification.setPrenom(utilisateur.getPrenom());
            modification.setLogin(utilisateur.getLogin());
            // Le schéma autorise motDePasse vide (minOccurs="0") : on l'envoie quand même,
            // le serveur doit ignorer un champ vide et conserver l'ancien mot de passe.
            modification.setMotDePasse(utilisateur.getMotDePasse());
            modification.setRole(utilisateur.getRole());

            ModifierUtilisateurRequest requete = new ModifierUtilisateurRequest();
            requete.setJeton(jeton);
            requete.setUtilisateur(modification);

            port.modifierUtilisateur(requete);

        } catch (SOAPFaultException e) {
            throw traduireFauteSoap(e);
        } catch (WebServiceException e) {
            throw traduireErreurReseau(e);
        }
    }

    @Override
    public void supprimer(String jeton, Long id) throws ServiceException {
        try {
            SupprimerUtilisateurRequest requete = new SupprimerUtilisateurRequest();
            requete.setJeton(jeton);
            requete.setId(id);

            SupprimerUtilisateurResponse reponse = port.supprimerUtilisateur(requete);
            if (!reponse.isSucces()) {
                throw new ServiceException(ServiceException.Type.ERREUR_INCONNUE,
                        "La suppression a échoué côté serveur.");
            }

        } catch (SOAPFaultException e) {
            throw traduireFauteSoap(e);
        } catch (WebServiceException e) {
            throw traduireErreurReseau(e);
        }
    }

    private Utilisateur versUtilisateur(UtilisateurInfo info) {
        return new Utilisateur(info.getId(), info.getNom(), info.getPrenom(),
                info.getLogin(), null, info.getRole());
    }

    /**
     * Traduit une faute SOAP (SOAPFaultException) en ServiceException.
     * Le contrat (section 4 / règle du guide) précise que toute opération SOAP
     * protégée renvoie une faute "Jeton invalide" quand le jeton est absent/inactif.
     */
    private ServiceException traduireFauteSoap(SOAPFaultException e) {
        String message = e.getMessage() == null ? "" : e.getMessage().toLowerCase();
        if (message.contains("jeton")) {
            return new ServiceException(ServiceException.Type.JETON_INVALIDE,
                    "Jeton invalide ou inactif.", e);
        }
        return new ServiceException(ServiceException.Type.ERREUR_INCONNUE,
                "Erreur renvoyée par le service : " + e.getMessage(), e);
    }

    /**
     * Traduit une erreur technique (connexion refusée, timeout, serveur éteint...)
     * en ServiceException lisible pour l'IHM (pas de stacktrace brute).
     */
    private ServiceException traduireErreurReseau(WebServiceException e) {
        return new ServiceException(ServiceException.Type.SERVICE_INJOIGNABLE,
                "Le service SOAP est injoignable. Vérifie que le site est démarré.", e);
    }
}