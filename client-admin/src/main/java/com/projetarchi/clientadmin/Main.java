package com.projetarchi.clientadmin;

import com.projetarchi.clientadmin.config.AppConfig;
import com.projetarchi.clientadmin.service.MockServiceUtilisateurs;
import com.projetarchi.clientadmin.service.ServiceUtilisateurs;
import com.projetarchi.clientadmin.ui.LoginFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // ---------------------------------------------------------------
        // POUR L'INSTANT : on utilise le Mock (aucun serveur nécessaire).
        // Comptes de test : admin/admin123 (ADMIN), editeur/editeur123 (EDITEUR).
        //
        // QUAND LE WSDL DE LA PERSONNE 2 SERA DISPONIBLE :
        // remplace la ligne suivante par :
        //     ServiceUtilisateurs service = new SoapServiceUtilisateurs();
        // (après avoir complété SoapServiceUtilisateurs comme indiqué dans le
        // README et dans les commentaires de cette classe). C'est le SEUL
        // changement nécessaire : LoginFrame et MainFrame ne changent pas.
        // ---------------------------------------------------------------
        ServiceUtilisateurs service = new MockServiceUtilisateurs();
        AppConfig config = new AppConfig();

        SwingUtilities.invokeLater(() -> new LoginFrame(service, config).setVisible(true));
    }
}
