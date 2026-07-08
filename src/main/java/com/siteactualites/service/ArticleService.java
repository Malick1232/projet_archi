package com.siteactualites.service;

import com.siteactualites.model.Article;
import com.siteactualites.model.Categorie;
import com.siteactualites.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // Liste paginée des articles triés par date de publication (du plus récent au plus ancien)
    public Page<Article> lister(Pageable pageable) {
        return articleRepository.findAllByOrderByDatePublicationDesc(pageable);
    }

    // Liste paginée des articles d'une catégorie
    public Page<Article> listerParCategorie(Categorie categorie, Pageable pageable) {
        return articleRepository.findByCategorieOrderByDatePublicationDesc(categorie, pageable);
    }

    // Recherche d'un article par son identifiant
    public Article trouverParId(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    // Enregistrer ou modifier un article
    public Article enregistrer(Article article) {
        return articleRepository.save(article);
    }

    // Supprimer un article
    public void supprimer(Long id) {
        articleRepository.deleteById(id);
    }

}