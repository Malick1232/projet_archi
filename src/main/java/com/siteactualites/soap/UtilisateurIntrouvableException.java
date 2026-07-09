package com.siteactualites.soap;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/** Levée par modifierUtilisateur() quand l'id fourni n'existe pas. */
@SoapFault(faultCode = FaultCode.CLIENT, faultStringOrReason = "Utilisateur introuvable")
public class UtilisateurIntrouvableException extends RuntimeException {

    public UtilisateurIntrouvableException(String message) {
        super(message);
    }
}
