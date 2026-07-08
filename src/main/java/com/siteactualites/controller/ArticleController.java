package com.siteactualites.controller;

import com.siteactualites.model.Article;
import com.siteactualites.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/article/{id}")
    public String detailArticle(@PathVariable Long id, Model model) {

        Article article = articleService.trouverParId(id);

        if (article == null) {
            return "redirect:/";
        }

        model.addAttribute("article", article);

        return "article";
    }
}
