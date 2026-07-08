package com.siteactualites.controller;

import com.siteactualites.model.Article;
import com.siteactualites.model.Categorie;
import com.siteactualites.service.ArticleService;
import com.siteactualites.service.CategorieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategorieController {

    private static final int TAILLE_PAGE = 5;

    private final CategorieService categorieService;
    private final ArticleService articleService;

    public CategorieController(CategorieService categorieService, ArticleService articleService) {
        this.categorieService = categorieService;
        this.articleService = articleService;
    }

    @GetMapping("/categorie/{id}")
    public String articlesParCategorie(@PathVariable Long id,
                                       @RequestParam(defaultValue = "0") int page,
                                       Model model) {

        Categorie categorie = categorieService.trouverParId(id);

        if (categorie == null) {
            return "redirect:/";
        }

        Page<Article> pageArticles =
                articleService.listerParCategorie(categorie, PageRequest.of(page, TAILLE_PAGE));

        model.addAttribute("categorie", categorie);
        model.addAttribute("articles", pageArticles.getContent());
        model.addAttribute("pageCourante", page);
        model.addAttribute("pagePrecedente", pageArticles.hasPrevious());
        model.addAttribute("pageSuivante", pageArticles.hasNext());

        return "categorie";
    }
}