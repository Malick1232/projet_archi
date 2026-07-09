package com.siteactualites.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siteactualites.dto.ArticleDto;
import com.siteactualites.dto.ArticleListeDto;
import com.siteactualites.dto.CategorieAvecArticlesDto;
import com.siteactualites.dto.CategorieListeDto;
import com.siteactualites.model.Categorie;
import com.siteactualites.service.ArticleService;
import com.siteactualites.service.CategorieService;

/**
 * Service REST (section 4 du sujet, base /api).
 * Chaque endpoint répond en JSON ou en XML selon ?format=json|xml
 * (négociation de contenu configurée dans WebConfig).
 */
@RestController
@RequestMapping("/api")
public class ArticleRestController {

    private final ArticleService articleService;
    private final CategorieService categorieService;

    public ArticleRestController(ArticleService articleService, CategorieService categorieService) {
        this.articleService = articleService;
        this.categorieService = categorieService;
    }

    /** GET /api/articles?format=json|xml : liste de tous les articles. */
    @GetMapping(value = "/articles", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE })
    public ArticleListeDto listerArticles() {
        List<ArticleDto> articles = articleService.listerTous().stream()
                .map(ArticleDto::depuis)
                .toList();
        return new ArticleListeDto(articles);
    }

    /** GET /api/articles/parcategorie?format=json|xml : articles regroupés par catégorie. */
    @GetMapping(value = "/articles/parcategorie", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE })
    public CategorieListeDto listerArticlesParCategorie() {
        List<CategorieAvecArticlesDto> categories = categorieService.listerToutes().stream()
                .map(categorie -> {
                    List<ArticleDto> articles = articleService.listerParCategorie(categorie).stream()
                            .map(ArticleDto::depuis)
                            .toList();
                    return CategorieAvecArticlesDto.depuis(categorie, articles);
                })
                .toList();
        return new CategorieListeDto(categories);
    }

    /** GET /api/categories/{id}/articles?format=json|xml : articles d'une catégorie donnée. */
    @GetMapping(value = "/categories/{id}/articles", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE })
    public ResponseEntity<CategorieAvecArticlesDto> listerArticlesDeCategorie(@PathVariable Long id) {
        Categorie categorie = categorieService.trouverParId(id);
        if (categorie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<ArticleDto> articles = articleService.listerParCategorie(categorie).stream()
                .map(ArticleDto::depuis)
                .toList();
        return ResponseEntity.ok(CategorieAvecArticlesDto.depuis(categorie, articles));
    }
}
