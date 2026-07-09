package com.siteactualites.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.siteactualites.model.Jeton;
import com.siteactualites.model.Utilisateur;
import com.siteactualites.repository.JetonRepository;

@Service
public class JetonService {

    private final JetonRepository jetonRepository;

    public JetonService(JetonRepository jetonRepository) {
        this.jetonRepository = jetonRepository;
    }

    public List<Jeton> listerTous() {
        return jetonRepository.findAll();
    }

    /** Génère un jeton aléatoire (UUID) actif, créé par l'admin connecté. */
    public Jeton generer(Utilisateur admin) {
        Jeton jeton = new Jeton();
        jeton.setValeur(UUID.randomUUID().toString());
        jeton.setCreePar(admin);
        jeton.setDateCreation(LocalDateTime.now());
        jeton.setActif(true);
        return jetonRepository.save(jeton);
    }

    public void supprimer(Long id) {
        jetonRepository.deleteById(id);
    }

    /**
     * Vérifie qu'un jeton existe et qu'il est actif.
     * Utilisé par le service SOAP (section 4 du sujet) avant chaque opération
     * protégée (toutes sauf authentifier).
     */
    public boolean estValide(String valeur) {
        if (valeur == null || valeur.isBlank()) {
            return false;
        }
        return jetonRepository.findByValeur(valeur)
                .map(Jeton::isActif)
                .orElse(false);
    }
}