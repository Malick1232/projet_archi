package com.siteactualites.soap;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.siteactualites.model.Role;
import com.siteactualites.model.Utilisateur;
import com.siteactualites.repository.UtilisateurRepository;
import com.siteactualites.service.JetonService;

// Classes générées par JAXB (plugin jaxb2, à partir de utilisateurs.xsd)
// -> disponibles après un `mvn generate-sources` / `mvn compile`
import com.siteactualites.soap.wsdl.AjouterUtilisateurRequest;
import com.siteactualites.soap.wsdl.AjouterUtilisateurResponse;
import com.siteactualites.soap.wsdl.AuthentifierRequest;
import com.siteactualites.soap.wsdl.AuthentifierResponse;
import com.siteactualites.soap.wsdl.ListerUtilisateursRequest;
import com.siteactualites.soap.wsdl.ListerUtilisateursResponse;
import com.siteactualites.soap.wsdl.ModifierUtilisateurRequest;
import com.siteactualites.soap.wsdl.ModifierUtilisateurResponse;
import com.siteactualites.soap.wsdl.SupprimerUtilisateurRequest;
import com.siteactualites.soap.wsdl.SupprimerUtilisateurResponse;
import com.siteactualites.soap.wsdl.UtilisateurCreation;
import com.siteactualites.soap.wsdl.UtilisateurInfo;
import com.siteactualites.soap.wsdl.UtilisateurModification;

/**
 * Service SOAP (section 4 du sujet).
 * WSDL exposé sur : http://localhost:8080/ws/utilisateurs.wsdl
 * Règle : toute opération sauf authentifier vérifie d'abord le jeton
 * (voir JetonService.estValide) et renvoie une faute SOAP "Jeton invalide" sinon.
 */
@Endpoint
public class UtilisateurEndpoint {

    private static final String NAMESPACE_URI = "http://siteactualites.com/soap/utilisateurs";

    private final UtilisateurRepository utilisateurRepository;
    private final JetonService jetonService;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurEndpoint(UtilisateurRepository utilisateurRepository,
                                JetonService jetonService,
                                PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.jetonService = jetonService;
        this.passwordEncoder = passwordEncoder;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "authentifierRequest")
    @ResponsePayload
    public AuthentifierResponse authentifier(@RequestPayload AuthentifierRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new AuthentificationException("Login ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getMotDePasse(), utilisateur.getMotDePasse())) {
            throw new AuthentificationException("Login ou mot de passe incorrect");
        }

        AuthentifierResponse response = new AuthentifierResponse();
        response.setRole(utilisateur.getRole().name());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listerUtilisateursRequest")
    @ResponsePayload
    public ListerUtilisateursResponse listerUtilisateurs(@RequestPayload ListerUtilisateursRequest request) {
        verifierJeton(request.getJeton());

        ListerUtilisateursResponse response = new ListerUtilisateursResponse();
        utilisateurRepository.findAll()
                .forEach(u -> response.getUtilisateur().add(versUtilisateurInfo(u)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ajouterUtilisateurRequest")
    @ResponsePayload
    public AjouterUtilisateurResponse ajouterUtilisateur(@RequestPayload AjouterUtilisateurRequest request) {
        verifierJeton(request.getJeton());

        UtilisateurCreation donnees = request.getUtilisateur();

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(donnees.getNom());
        utilisateur.setPrenom(donnees.getPrenom());
        utilisateur.setLogin(donnees.getLogin());
        utilisateur.setMotDePasse(passwordEncoder.encode(donnees.getMotDePasse()));
        utilisateur.setRole(Role.valueOf(donnees.getRole()));
        utilisateur.setDateCreation(LocalDateTime.now());
        utilisateur = utilisateurRepository.save(utilisateur);

        AjouterUtilisateurResponse response = new AjouterUtilisateurResponse();
        response.setUtilisateur(versUtilisateurInfo(utilisateur));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "modifierUtilisateurRequest")
    @ResponsePayload
    public ModifierUtilisateurResponse modifierUtilisateur(@RequestPayload ModifierUtilisateurRequest request) {
        verifierJeton(request.getJeton());

        UtilisateurModification donnees = request.getUtilisateur();
        Utilisateur utilisateur = utilisateurRepository.findById(donnees.getId())
                .orElseThrow(() -> new UtilisateurIntrouvableException("Utilisateur introuvable : id=" + donnees.getId()));

        utilisateur.setNom(donnees.getNom());
        utilisateur.setPrenom(donnees.getPrenom());
        utilisateur.setLogin(donnees.getLogin());
        utilisateur.setRole(Role.valueOf(donnees.getRole()));
        if (donnees.getMotDePasse() != null && !donnees.getMotDePasse().isBlank()) {
            utilisateur.setMotDePasse(passwordEncoder.encode(donnees.getMotDePasse()));
        }
        utilisateur = utilisateurRepository.save(utilisateur);

        ModifierUtilisateurResponse response = new ModifierUtilisateurResponse();
        response.setUtilisateur(versUtilisateurInfo(utilisateur));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "supprimerUtilisateurRequest")
    @ResponsePayload
    public SupprimerUtilisateurResponse supprimerUtilisateur(@RequestPayload SupprimerUtilisateurRequest request) {
        verifierJeton(request.getJeton());

        utilisateurRepository.deleteById(request.getId());

        SupprimerUtilisateurResponse response = new SupprimerUtilisateurResponse();
        response.setSucces(true);
        return response;
    }

    /** Vérifie que le jeton existe et est actif ; sinon lève une faute SOAP "Jeton invalide". */
    private void verifierJeton(String valeur) {
        if (!jetonService.estValide(valeur)) {
            throw new JetonInvalideException("Jeton invalide");
        }
    }

    /** Conversion entité JPA -> type XML exposé (jamais le mot de passe). */
    private UtilisateurInfo versUtilisateurInfo(Utilisateur utilisateur) {
        UtilisateurInfo info = new UtilisateurInfo();
        info.setId(utilisateur.getId());
        info.setNom(utilisateur.getNom());
        info.setPrenom(utilisateur.getPrenom());
        info.setLogin(utilisateur.getLogin());
        info.setRole(utilisateur.getRole().name());
        if (utilisateur.getDateCreation() != null) {
            info.setDateCreation(utilisateur.getDateCreation().toString());
        }
        return info;
    }
}
