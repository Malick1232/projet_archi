package com.siteactualites.repository;

import com.siteactualites.model.Article;
import com.siteactualites.model.Categorie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // Liste des articles par ordre de publication décroissant
    Page<Article> findAllByOrderByDatePublicationDesc(Pageable pageable);

    // Liste des articles d'une catégorie par ordre de publication décroissant
    Page<Article> findByCategorieOrderByDatePublicationDesc(Categorie categorie, Pageable pageable);

    // Recherche d'un article par son identifiant
    Optional<Article> findById(Long id);

}