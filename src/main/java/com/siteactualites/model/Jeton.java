package com.siteactualites.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "jeton")
public class Jeton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String valeur;

    @ManyToOne
    @JoinColumn(name = "cree_par", nullable = false)
    private Utilisateur creePar;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(nullable = false)
    private boolean actif;

    public Jeton() {
    }

    public Long getId() {
        return id;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Utilisateur getCreePar() {
        return creePar;
    }

    public void setCreePar(Utilisateur creePar) {
        this.creePar = creePar;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
}