package com.projetarchi.clientadmin.ui;

import com.projetarchi.clientadmin.model.Utilisateur;

import javax.swing.*;
import java.awt.*;

/**
 * Boîte de dialogue modale pour ajouter ou modifier un utilisateur.
 * Si "utilisateurExistant" est null -> mode ajout, sinon -> mode modification.
 */
public class UtilisateurFormDialog extends JDialog {

    private final JTextField champNom = new JTextField(20);
    private final JTextField champPrenom = new JTextField(20);
    private final JTextField champLogin = new JTextField(20);
    private final JPasswordField champMotDePasse = new JPasswordField(20);
    private final JComboBox<String> champRole = new JComboBox<>(new String[]{"EDITEUR", "ADMIN"});

    private Utilisateur resultat; // null si l'utilisateur a annulé

    public UtilisateurFormDialog(Frame parent, Utilisateur utilisateurExistant) {
        super(parent, utilisateurExistant == null ? "Ajouter un utilisateur" : "Modifier l'utilisateur", true);

        JPanel formulaire = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        ajouterLigne(formulaire, c, 0, "Nom :", champNom);
        ajouterLigne(formulaire, c, 1, "Prénom :", champPrenom);
        ajouterLigne(formulaire, c, 2, "Login :", champLogin);
        ajouterLigne(formulaire, c, 3, "Mot de passe :", champMotDePasse);
        ajouterLigne(formulaire, c, 4, "Rôle :", champRole);

        if (utilisateurExistant != null) {
            champNom.setText(utilisateurExistant.getNom());
            champPrenom.setText(utilisateurExistant.getPrenom());
            champLogin.setText(utilisateurExistant.getLogin());
            champRole.setSelectedItem(utilisateurExistant.getRole());
            champMotDePasse.setToolTipText("Laisser vide pour conserver le mot de passe actuel");
        }

        JButton boutonValider = new JButton(utilisateurExistant == null ? "Ajouter" : "Enregistrer");
        JButton boutonAnnuler = new JButton("Annuler");

        boutonValider.addActionListener(e -> {
            if (champNom.getText().trim().isEmpty() || champLogin.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Le nom et le login sont obligatoires.",
                        "Champs manquants", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Long id = utilisateurExistant != null ? utilisateurExistant.getId() : null;
            String motDePasse = new String(champMotDePasse.getPassword());
            if (motDePasse.trim().isEmpty() && utilisateurExistant != null) {
                motDePasse = utilisateurExistant.getMotDePasse();
            }
            resultat = new Utilisateur(id, champNom.getText(), champPrenom.getText(),
                    champLogin.getText(), motDePasse, (String) champRole.getSelectedItem());
            dispose();
        });
        boutonAnnuler.addActionListener(e -> {
            resultat = null;
            dispose();
        });

        JPanel boutons = new JPanel();
        boutons.add(boutonValider);
        boutons.add(boutonAnnuler);

        setLayout(new BorderLayout());
        add(formulaire, BorderLayout.CENTER);
        add(boutons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void ajouterLigne(JPanel panel, GridBagConstraints c, int ligne, String label, JComponent champ) {
        c.gridx = 0;
        c.gridy = ligne;
        c.weightx = 0;
        panel.add(new JLabel(label), c);
        c.gridx = 1;
        c.weightx = 1;
        panel.add(champ, c);
    }

    /** @return l'utilisateur saisi, ou null si l'utilisateur a annulé la boîte de dialogue */
    public Utilisateur getResultat() {
        return resultat;
    }
}
