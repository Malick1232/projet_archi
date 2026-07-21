package com.projetarchi.clientadmin.model;

/**
 * Représente un utilisateur tel que manipulé par l'application cliente.
 * Correspond à la table "utilisateur" du schéma commun (section 3 du guide).
 */
public class Utilisateur {

    private Long id;
    private String nom;
    private String prenom;
    private String login;
    // Le mot de passe en clair ne transite ici que lors de la création/modification
    // depuis le formulaire ; il n'est jamais stocké tel quel côté serveur (BCrypt).
    private String motDePasse;
    private String role; // "EDITEUR" ou "ADMIN"

    public Utilisateur() {
    }

    public Utilisateur(Long id, String nom, String prenom, String login, String motDePasse, String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return login + " (" + role + ")";
    }
}
