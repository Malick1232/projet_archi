package com.siteactualites.soap;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/** Levée par authentifier() quand le login est inconnu ou le mot de passe incorrect. */
@SoapFault(faultCode = FaultCode.CLIENT, faultStringOrReason = "Login ou mot de passe incorrect")
public class AuthentificationException extends RuntimeException {

    public AuthentificationException(String message) {
        super(message);
    }
}
