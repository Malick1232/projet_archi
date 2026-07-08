package com.siteactualites.service;

import com.siteactualites.model.Categorie;
import com.siteactualites.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public List<Categorie> listerToutes() {
        return categorieRepository.findAll();
    }

    public Categorie trouverParId(Long id) {
        return categorieRepository.findById(id).orElse(null);
    }

    public Categorie enregistrer(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public void supprimer(Long id) {
        categorieRepository.deleteById(id);
    }
}