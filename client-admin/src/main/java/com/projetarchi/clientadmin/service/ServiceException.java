package com.projetarchi.clientadmin.service;

/**
 * Exception "métier" levée par une implémentation de ServiceUtilisateurs.
 * Elle regroupe tous les cas d'erreur qui doivent être affichés proprement
 * à l'utilisateur (pas de stacktrace brute), conformément à la mission du rôle 3 :
 *   - jeton invalide ou expiré
 *   - login / mot de passe incorrect
 *   - service SOAP injoignable (réseau, serveur éteint, etc.)
 */
public class ServiceException extends Exception {

    public enum Type {
        AUTHENTIFICATION_ECHOUEE,
        JETON_INVALIDE,
        SERVICE_INJOIGNABLE,
        ERREUR_INCONNUE
    }

    private final Type type;

    public ServiceException(Type type, String message) {
        super(message);
        this.type = type;
    }

    public ServiceException(Type type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
