package com.siteactualites.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteactualites.model.Categorie;
import com.siteactualites.service.CategorieService;

@Controller
@RequestMapping("/editeur/categories")
public class EditeurCategorieController {

    private final CategorieService categorieService;

    public EditeurCategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    /** Liste des catégories avec boutons modifier/supprimer. */
    @GetMapping
    public String lister(Model model) {
        model.addAttribute("categories", categorieService.listerToutes());
        return "editeur/categories";
    }

    /** Formulaire vide (ajout). */
    @GetMapping("/nouvelle")
    public String nouvelle(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "editeur/categorie-form";
    }

    /** Formulaire pré-rempli (modification). */
    @GetMapping("/{id}/modifier")
    public String modifier(@PathVariable Long id, Model model) {
        Categorie categorie = categorieService.trouverParId(id);
        if (categorie == null) {
            return "redirect:/editeur/categories";
        }
        model.addAttribute("categorie", categorie);
        return "editeur/categorie-form";
    }

    /** Enregistre l'ajout OU la modification (si id présent). */
    @PostMapping
    public String enregistrer(@ModelAttribute Categorie categorie) {
        categorieService.enregistrer(categorie);
        return "redirect:/editeur/categories";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id) {
        categorieService.supprimer(id);
        return "redirect:/editeur/categories";
    }
}