package com.siteactualites.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.siteactualites.model.Categorie;

/**
 * Une catégorie avec la liste de ses articles.
 * Utilisé par GET /api/articles/parcategorie
 */
@JacksonXmlRootElement(localName = "categorie")
public class CategorieAvecArticlesDto {

    @JacksonXmlProperty(isAttribute = true)
    private Long id;

    private String libelle;
    private String description;

    @JacksonXmlElementWrapper(localName = "articles")
    @JacksonXmlProperty(localName = "article")
    private List<ArticleDto> articles;

    public CategorieAvecArticlesDto() {
    }

    public static CategorieAvecArticlesDto depuis(Categorie categorie, List<ArticleDto> articles) {
        CategorieAvecArticlesDto dto = new CategorieAvecArticlesDto();
        dto.id = categorie.getId();
        dto.libelle = categorie.getLibelle();
        dto.description = categorie.getDescription();
        dto.articles = articles;
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ArticleDto> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDto> articles) {
        this.articles = articles;
    }
}
