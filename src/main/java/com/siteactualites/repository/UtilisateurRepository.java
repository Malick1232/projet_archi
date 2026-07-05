package com.siteactualites.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siteactualites.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByLogin(String login);

}