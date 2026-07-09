package com.siteactualites.soap;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/**
 * Levée quand le jeton fourni est absent, inconnu, ou désactivé.
 * Spring-WS la transforme automatiquement en faute SOAP "Jeton invalide"
 * (règle de la section 4 du sujet).
 */
@SoapFault(faultCode = FaultCode.CLIENT, faultStringOrReason = "Jeton invalide")
public class JetonInvalideException extends RuntimeException {

    public JetonInvalideException(String message) {
        super(message);
    }
}
