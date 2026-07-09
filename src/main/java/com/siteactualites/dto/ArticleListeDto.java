package com.siteactualites.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Enveloppe utilisée pour renvoyer une liste d'articles.
 * Nécessaire pour que le XML ait une racine propre :
 * <articles><article>...</article><article>...</article></articles>
 */
@JacksonXmlRootElement(localName = "articles")
public class ArticleListeDto {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "article")
    private List<ArticleDto> articles;

    public ArticleListeDto() {
    }

    public ArticleListeDto(List<ArticleDto> articles) {
        this.articles = articles;
    }

    public List<ArticleDto> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDto> articles) {
        this.articles = articles;
    }
}
