package com.projetarchi.clientadmin.service;

import com.projetarchi.clientadmin.model.Utilisateur;

import java.util.List;

/**
 * Contrat unique utilisé par TOUTE l'interface graphique.
 * L'IHM ne doit jamais dépendre directement de MockServiceUtilisateurs ni de
 * SoapServiceUtilisateurs : elle ne connaît que cette interface.
 * Cela permet de développer et tester l'appli avant que le service SOAP
 * (rôle 2) ne soit disponible, puis de basculer en changeant une seule ligne
 * (voir Main.java).
 *
 * Correspond aux 5 opérations SOAP définies dans le contrat (section 4 du guide) :
 *   authentifier, listerUtilisateurs, ajouterUtilisateur,
 *   modifierUtilisateur, supprimerUtilisateur
 */
public interface ServiceUtilisateurs {

    /**
     * Authentifie un utilisateur par login/mot de passe.
     * @return l'utilisateur authentifié (avec son rôle) si les identifiants sont corrects
     * @throws ServiceException si les identifiants sont incorrects ou si le service est injoignable
     */
    Utilisateur authentifier(String login, String motDePasse) throws ServiceException;

    /**
     * Liste tous les utilisateurs. Nécessite un jeton actif.
     */
    List<Utilisateur> lister(String jeton) throws ServiceException;

    /**
     * Ajoute un utilisateur. Nécessite un jeton actif.
     * @return l'utilisateur créé (avec son id généré)
     */
    Utilisateur ajouter(String jeton, Utilisateur utilisateur) throws ServiceException;

    /**
     * Modifie un utilisateur existant. Nécessite un jeton actif.
     */
    void modifier(String jeton, Utilisateur utilisateur) throws ServiceException;

    /**
     * Supprime un utilisateur par id. Nécessite un jeton actif.
     */
    void supprimer(String jeton, Long id) throws ServiceException;
}
