package com.projetarchi.clientadmin.ui;

import com.projetarchi.clientadmin.config.AppConfig;
import com.projetarchi.clientadmin.model.Utilisateur;
import com.projetarchi.clientadmin.service.ServiceException;
import com.projetarchi.clientadmin.service.ServiceUtilisateurs;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre de connexion. Missions du rôle 3 :
 *  - demande login + mot de passe
 *  - invoque authentifier()
 *  - si le rôle retourné n'est pas ADMIN -> "Accès refusé", reste sur cet écran
 *  - sinon -> ouvre la fenêtre de gestion des utilisateurs (MainFrame)
 */
public class LoginFrame extends JFrame {

    private final ServiceUtilisateurs service;
    private final AppConfig config;

    private final JTextField champLogin = new JTextField(15);
    private final JPasswordField champMotDePasse = new JPasswordField(15);
    private final JLabel messageErreur = new JLabel(" ");

    public LoginFrame(ServiceUtilisateurs service, AppConfig config) {
        super("Connexion - Application de gestion des utilisateurs");
        this.service = service;
        this.config = config;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        panel.add(new JLabel("Login :"), c);
        c.gridx = 1;
        panel.add(champLogin, c);

        c.gridx = 0; c.gridy = 1;
        panel.add(new JLabel("Mot de passe :"), c);
        c.gridx = 1;
        panel.add(champMotDePasse, c);

        messageErreur.setForeground(Color.RED);
        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        panel.add(messageErreur, c);

        JButton boutonConnexion = new JButton("Se connecter");
        boutonConnexion.addActionListener(e -> tenterConnexion());
        c.gridy = 3;
        panel.add(boutonConnexion, c);

        // Permet de valider avec Entrée depuis le champ mot de passe
        champMotDePasse.addActionListener(e -> tenterConnexion());

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void tenterConnexion() {
        String login = champLogin.getText().trim();
        String motDePasse = new String(champMotDePasse.getPassword());

        if (login.trim().isEmpty() || motDePasse.trim().isEmpty()) {
            messageErreur.setText("Merci de saisir un login et un mot de passe.");
            return;
        }

        try {
            Utilisateur utilisateur = service.authentifier(login, motDePasse);
            if (!"ADMIN".equalsIgnoreCase(utilisateur.getRole())) {
                messageErreur.setText("Accès refusé : seul un administrateur peut gérer les utilisateurs.");
                champMotDePasse.setText("");
                return;
            }
            // Connexion réussie -> ouverture de la fenêtre principale
            dispose();
            new MainFrame(service, config).setVisible(true);

        } catch (ServiceException ex) {
            switch (ex.getType()) {
                case AUTHENTIFICATION_ECHOUEE:
                    messageErreur.setText("Login ou mot de passe incorrect.");
                    break;
                case SERVICE_INJOIGNABLE:
                    messageErreur.setText("Service injoignable. Le site est-il démarré ?");
                    break;
                default:
                    messageErreur.setText("Erreur : " + ex.getMessage());
            }
            champMotDePasse.setText("");
        }
    }
}
