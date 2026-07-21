package com.projetarchi.clientadmin.service;

import com.projetarchi.clientadmin.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implémentation "en dur" de ServiceUtilisateurs, utilisée en attendant que
 * le service SOAP réel (rôle 2) soit disponible.
 *
 * Jeton valide pour cette maquette : "TOKEN-TEST-1234"
 * Comptes de test :
 *   admin  / admin123   -> ADMIN
 *   editeur/ editeur123 -> EDITEUR (n'a pas accès à la gestion des utilisateurs,
 *                          l'écran de connexion doit refuser ce cas)
 */
public class MockServiceUtilisateurs implements ServiceUtilisateurs {

    private static final String JETON_VALIDE = "TOKEN-TEST-1234";

    private final List<Utilisateur> utilisateurs = new ArrayList<>();
    private long prochainId = 3L;

    public MockServiceUtilisateurs() {
        utilisateurs.add(new Utilisateur(1L, "Diop", "Admin", "admin", "admin123", "ADMIN"));
        utilisateurs.add(new Utilisateur(2L, "Fall", "Editeur", "editeur", "editeur123", "EDITEUR"));
    }

    @Override
    public Utilisateur authentifier(String login, String motDePasse) throws ServiceException {
        for (Utilisateur u : utilisateurs) {
            if (u.getLogin().equals(login) && u.getMotDePasse().equals(motDePasse)) {
                return u;
            }
        }
        throw new ServiceException(ServiceException.Type.AUTHENTIFICATION_ECHOUEE,
                "Login ou mot de passe incorrect.");
    }

    @Override
    public List<Utilisateur> lister(String jeton) throws ServiceException {
        verifierJeton(jeton);
        return new ArrayList<>(utilisateurs);
    }

    @Override
    public Utilisateur ajouter(String jeton, Utilisateur utilisateur) throws ServiceException {
        verifierJeton(jeton);
        utilisateur.setId(prochainId++);
        utilisateurs.add(utilisateur);
        return utilisateur;
    }

    @Override
    public void modifier(String jeton, Utilisateur utilisateur) throws ServiceException {
        verifierJeton(jeton);
        for (int i = 0; i < utilisateurs.size(); i++) {
            if (Objects.equals(utilisateurs.get(i).getId(), utilisateur.getId())) {
                utilisateurs.set(i, utilisateur);
                return;
            }
        }
        throw new ServiceException(ServiceException.Type.ERREUR_INCONNUE,
                "Utilisateur introuvable (id=" + utilisateur.getId() + ").");
    }

    @Override
    public void supprimer(String jeton, Long id) throws ServiceException {
        verifierJeton(jeton);
        boolean supprime = utilisateurs.removeIf(u -> Objects.equals(u.getId(), id));
        if (!supprime) {
            throw new ServiceException(ServiceException.Type.ERREUR_INCONNUE,
                    "Utilisateur introuvable (id=" + id + ").");
        }
    }

    private void verifierJeton(String jeton) throws ServiceException {
        if (jeton == null || !jeton.equals(JETON_VALIDE)) {
            throw new ServiceException(ServiceException.Type.JETON_INVALIDE,
                    "Jeton invalide ou inactif.");
        }
    }
}
