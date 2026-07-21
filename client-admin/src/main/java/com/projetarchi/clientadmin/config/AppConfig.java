package com.projetarchi.clientadmin.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Charge config.properties (src/main/resources), qui contient le jeton
 * d'authentification à envoyer à chaque opération SOAP protégée.
 * Un administrateur génère ce jeton depuis la page d'administration du site
 * (rôle 1) puis le communique pour qu'on le colle ici.
 */
public class AppConfig {

    private static final String FICHIER = "/config.properties";
    private final Properties properties = new Properties();

    public AppConfig() {
        try (InputStream in = getClass().getResourceAsStream(FICHIER)) {
            if (in != null) {
                properties.load(in);
            }
        } catch (IOException e) {
            System.err.println("Impossible de lire " + FICHIER + " : " + e.getMessage());
        }
    }

    public String getJeton() {
        return properties.getProperty("jeton", "");
    }

    public String getSoapWsdlUrl() {
        return properties.getProperty("soap.wsdl.url", "http://localhost:8080/ws/utilisateurs.wsdl");
    }
}
