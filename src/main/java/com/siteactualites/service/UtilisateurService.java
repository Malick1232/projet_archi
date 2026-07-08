package com.siteactualites.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.siteactualites.model.Utilisateur;
import com.siteactualites.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository,
                              PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Utilisateur> listerTous() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur trouverParId(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur trouverParLogin(String login) {
        return utilisateurRepository.findByLogin(login).orElse(null);
    }

    /** Création : le mot de passe est haché avant d'être stocké. */
    public Utilisateur creer(Utilisateur utilisateur, String motDePasseClair) {
        utilisateur.setMotDePasse(passwordEncoder.encode(motDePasseClair));
        utilisateur.setDateCreation(LocalDateTime.now());
        return utilisateurRepository.save(utilisateur);
    }

    /** Modification : mot de passe changé seulement s'il est fourni. */
    public Utilisateur modifier(Long id, Utilisateur donnees, String motDePasseClair) {
        Utilisateur existant = utilisateurRepository.findById(id).orElseThrow();
        existant.setNom(donnees.getNom());
        existant.setPrenom(donnees.getPrenom());
        existant.setLogin(donnees.getLogin());
        existant.setRole(donnees.getRole());
        if (motDePasseClair != null && !motDePasseClair.isBlank()) {
            existant.setMotDePasse(passwordEncoder.encode(motDePasseClair));
        }
        return utilisateurRepository.save(existant);
    }

    public void supprimer(Long id) {
        utilisateurRepository.deleteById(id);
    }
}