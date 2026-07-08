package com.siteactualites.controller;

import com.siteactualites.model.Article;
import com.siteactualites.service.ArticleService;
import com.siteactualites.service.CategorieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccueilController {

    private static final int TAILLE_PAGE = 5;

    private final ArticleService articleService;
    private final CategorieService categorieService;

    public AccueilController(ArticleService articleService, CategorieService categorieService) {
        this.articleService = articleService;
        this.categorieService = categorieService;
    }

    @GetMapping("/")
    public String accueil(@RequestParam(defaultValue = "0") int page, Model model) {

        Page<Article> pageArticles =
                articleService.lister(PageRequest.of(page, TAILLE_PAGE));

        model.addAttribute("articles", pageArticles.getContent());
        model.addAttribute("categories", categorieService.listerToutes());
        model.addAttribute("pageCourante", page);
        model.addAttribute("pagePrecedente", pageArticles.hasPrevious());
        model.addAttribute("pageSuivante", pageArticles.hasNext());

        return "accueil";
    }
}
