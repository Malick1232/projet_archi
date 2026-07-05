package com.siteactualites.repository;

import com.siteactualites.model.Jeton;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JetonRepository extends JpaRepository<Jeton, Long> {

    Optional<Jeton> findByValeur(String valeur);

}