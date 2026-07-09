package com.siteactualites.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Enveloppe utilisée pour renvoyer la liste des catégories (avec leurs articles).
 */
@JacksonXmlRootElement(localName = "categories")
public class CategorieListeDto {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "categorie")
    private List<CategorieAvecArticlesDto> categories;

    public CategorieListeDto() {
    }

    public CategorieListeDto(List<CategorieAvecArticlesDto> categories) {
        this.categories = categories;
    }

    public List<CategorieAvecArticlesDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategorieAvecArticlesDto> categories) {
        this.categories = categories;
    }
}
