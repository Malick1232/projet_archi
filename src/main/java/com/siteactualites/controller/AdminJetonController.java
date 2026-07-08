package com.siteactualites.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.siteactualites.model.Utilisateur;
import com.siteactualites.service.JetonService;
import com.siteactualites.service.UtilisateurService;

@Controller
@RequestMapping("/admin/jetons")
public class AdminJetonController {

    private final JetonService jetonService;
    private final UtilisateurService utilisateurService;

    public AdminJetonController(JetonService jetonService,
                                UtilisateurService utilisateurService) {
        this.jetonService = jetonService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public String lister(Model model) {
        model.addAttribute("jetons", jetonService.listerTous());
        return "admin/jetons";
    }

    @PostMapping("/generer")
    public String generer(Principal principal) {
        Utilisateur admin = utilisateurService.trouverParLogin(principal.getName());
        jetonService.generer(admin);
        return "redirect:/admin/jetons";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id) {
        jetonService.supprimer(id);
        return "redirect:/admin/jetons";
    }
}