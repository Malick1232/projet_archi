package com.siteactualites.controller;

import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.siteactualites.model.Categorie;
import com.siteactualites.service.CategorieService;

/**
 * Ajoute la liste des catégories au modèle de TOUTES les pages,
 * pour que la barre de navigation puisse les afficher partout.
 */
@ControllerAdvice
public class GlobalModelAdvice {

    private final CategorieService categorieService;

    public GlobalModelAdvice(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @ModelAttribute("categories")
    public List<Categorie> categories() {
        return categorieService.listerToutes();
    }
}
