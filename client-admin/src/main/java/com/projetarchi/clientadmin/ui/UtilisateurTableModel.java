package com.projetarchi.clientadmin.ui;

import com.projetarchi.clientadmin.model.Utilisateur;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurTableModel extends AbstractTableModel {

    private final String[] colonnes = {"Id", "Nom", "Prénom", "Login", "Rôle"};
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
        fireTableDataChanged();
    }

    public Utilisateur getUtilisateurAt(int rowIndex) {
        return utilisateurs.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return utilisateurs.size();
    }

    @Override
    public int getColumnCount() {
        return colonnes.length;
    }

    @Override
    public String getColumnName(int column) {
        return colonnes[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Utilisateur u = utilisateurs.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> u.getId();
            case 1 -> u.getNom();
            case 2 -> u.getPrenom();
            case 3 -> u.getLogin();
            case 4 -> u.getRole();
            default -> "";
        };
    }
}
