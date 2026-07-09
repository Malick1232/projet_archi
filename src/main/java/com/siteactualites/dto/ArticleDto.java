package com.siteactualites.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.siteactualites.model.Article;

/**
 * Représentation d'un article exposée par le service REST (section 4 du sujet).
 * On n'expose jamais directement l'entité JPA Article.
 */
@JacksonXmlRootElement(localName = "article")
public class ArticleDto {

    @JacksonXmlProperty(isAttribute = true)
    private Long id;

    private String titre;
    private String resume;
    private String contenu;
    private LocalDateTime datePublication;
    private String categorie;
    private String auteur;

    public ArticleDto() {
    }

    public static ArticleDto depuis(Article article) {
        ArticleDto dto = new ArticleDto();
        dto.id = article.getId();
        dto.titre = article.getTitre();
        dto.resume = article.getResume();
        dto.contenu = article.getContenu();
        dto.datePublication = article.getDatePublication();
        dto.categorie = article.getCategorie() != null ? article.getCategorie().getLibelle() : null;
        dto.auteur = article.getAuteur() != null
                ? article.getAuteur().getPrenom() + " " + article.getAuteur().getNom()
                : null;
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDateTime datePublication) {
        this.datePublication = datePublication;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
}
