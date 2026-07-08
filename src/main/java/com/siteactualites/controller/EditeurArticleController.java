package com.siteactualites.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteactualites.model.Article;
import com.siteactualites.repository.UtilisateurRepository;
import com.siteactualites.service.ArticleService;
import com.siteactualites.service.CategorieService;

@Controller
@RequestMapping("/editeur/articles")
public class EditeurArticleController {

    private final ArticleService articleService;
    private final CategorieService categorieService;
    private final UtilisateurRepository utilisateurRepository;

    public EditeurArticleController(ArticleService articleService,
                                    CategorieService categorieService,
                                    UtilisateurRepository utilisateurRepository) {
        this.articleService = articleService;
        this.categorieService = categorieService;
        this.utilisateurRepository = utilisateurRepository;
    }

    /** Liste de tous les articles. */
    @GetMapping
    public String lister(Model model) {
        model.addAttribute("articles",
                articleService.lister(PageRequest.of(0, 100)).getContent());
        return "editeur/articles";
    }

    /** Formulaire vide (ajout). */
    @GetMapping("/nouveau")
    public String nouveau(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categorieService.listerToutes());
        return "editeur/article-form";
    }

    /** Formulaire pré-rempli (modification). */
    @GetMapping("/{id}/modifier")
    public String modifier(@PathVariable Long id, Model model) {
        Article article = articleService.trouverParId(id);
        if (article == null) {
            return "redirect:/editeur/articles";
        }
        model.addAttribute("article", article);
        model.addAttribute("categories", categorieService.listerToutes());
        return "editeur/article-form";
    }

    /** Enregistre l'ajout ou la modification. */
    @PostMapping
    public String enregistrer(@ModelAttribute Article article, Principal principal) {

        if (article.getId() == null) {
            // Nouvel article : l'auteur est l'utilisateur connecté
            article.setDatePublication(LocalDateTime.now());
            article.setAuteur(utilisateurRepository.findByLogin(principal.getName())
                    .orElseThrow());
            articleService.enregistrer(article);
        } else {
            // Modification : on garde l'auteur et la date d'origine
            Article existant = articleService.trouverParId(article.getId());
            existant.setTitre(article.getTitre());
            existant.setResume(article.getResume());
            existant.setContenu(article.getContenu());
            existant.setCategorie(article.getCategorie());
            articleService.enregistrer(existant);
        }

        return "redirect:/editeur/articles";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id) {
        articleService.supprimer(id);
        return "redirect:/editeur/articles";
    }
}