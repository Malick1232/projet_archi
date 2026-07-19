package com.projetarchi.clientadmin.ui;

import com.projetarchi.clientadmin.config.AppConfig;
import com.projetarchi.clientadmin.model.Utilisateur;
import com.projetarchi.clientadmin.service.ServiceException;
import com.projetarchi.clientadmin.service.ServiceUtilisateurs;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale : tableau des utilisateurs + boutons
 * Ajouter / Modifier / Supprimer / Actualiser (missions du rôle 3).
 */
public class MainFrame extends JFrame {

    private final ServiceUtilisateurs service;
    private final AppConfig config;
    private final UtilisateurTableModel tableModel = new UtilisateurTableModel();
    private final JTable table = new JTable(tableModel);

    public MainFrame(ServiceUtilisateurs service, AppConfig config) {
        super("Gestion des utilisateurs - Administration");
        this.service = service;
        this.config = config;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);

        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton boutonAjouter = new JButton("Ajouter");
        JButton boutonModifier = new JButton("Modifier");
        JButton boutonSupprimer = new JButton("Supprimer");
        JButton boutonActualiser = new JButton("Actualiser");

        boutonAjouter.addActionListener(e -> ajouter());
        boutonModifier.addActionListener(e -> modifier());
        boutonSupprimer.addActionListener(e -> supprimer());
        boutonActualiser.addActionListener(e -> chargerUtilisateurs());

        JPanel barreBoutons = new JPanel();
        barreBoutons.add(boutonAjouter);
        barreBoutons.add(boutonModifier);
        barreBoutons.add(boutonSupprimer);
        barreBoutons.add(boutonActualiser);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(barreBoutons, BorderLayout.SOUTH);

        chargerUtilisateurs();
    }

    private void chargerUtilisateurs() {
        try {
            tableModel.setUtilisateurs(service.lister(config.getJeton()));
        } catch (ServiceException ex) {
            afficherErreur(ex);
        }
    }

    private void ajouter() {
        UtilisateurFormDialog dialog = new UtilisateurFormDialog(this, null);
        dialog.setVisible(true);
        Utilisateur saisi = dialog.getResultat();
        if (saisi == null) {
            return; // annulé
        }
        try {
            service.ajouter(config.getJeton(), saisi);
            chargerUtilisateurs();
        } catch (ServiceException ex) {
            afficherErreur(ex);
        }
    }

    private void modifier() {
        int ligne = table.getSelectedRow();
        if (ligne < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionne d'abord un utilisateur dans le tableau.",
                    "Aucune sélection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Utilisateur existant = tableModel.getUtilisateurAt(ligne);
        UtilisateurFormDialog dialog = new UtilisateurFormDialog(this, existant);
        dialog.setVisible(true);
        Utilisateur modifie = dialog.getResultat();
        if (modifie == null) {
            return; // annulé
        }
        try {
            service.modifier(config.getJeton(), modifie);
            chargerUtilisateurs();
        } catch (ServiceException ex) {
            afficherErreur(ex);
        }
    }

    private void supprimer() {
        int ligne = table.getSelectedRow();
        if (ligne < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionne d'abord un utilisateur dans le tableau.",
                    "Aucune sélection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Utilisateur cible = tableModel.getUtilisateurAt(ligne);
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Supprimer l'utilisateur \"" + cible.getLogin() + "\" ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            service.supprimer(config.getJeton(), cible.getId());
            chargerUtilisateurs();
        } catch (ServiceException ex) {
            afficherErreur(ex);
        }
    }

    private void afficherErreur(ServiceException ex) {
        String titre;
        switch (ex.getType()) {
            case JETON_INVALIDE:
                titre = "Jeton invalide";
                break;
            case SERVICE_INJOIGNABLE:
                titre = "Service injoignable";
                break;
            case AUTHENTIFICATION_ECHOUEE:
                titre = "Authentification échouée";
                break;
            default:
                titre = "Erreur";
        }
        JOptionPane.showMessageDialog(this, ex.getMessage(), titre, JOptionPane.ERROR_MESSAGE);
    }
}
