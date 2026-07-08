package com.siteactualites.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.siteactualites.model.Role;
import com.siteactualites.model.Utilisateur;
import com.siteactualites.service.UtilisateurService;

@Controller
@RequestMapping("/admin/utilisateurs")
public class AdminUtilisateurController {

    private final UtilisateurService utilisateurService;

    public AdminUtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public String lister(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.listerTous());
        return "admin/utilisateurs";
    }

    @GetMapping("/nouveau")
    public String nouveau(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("roles", Role.values());
        return "admin/utilisateur-form";
    }

    @GetMapping("/{id}/modifier")
    public String modifier(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.trouverParId(id);
        if (utilisateur == null) {
            return "redirect:/admin/utilisateurs";
        }
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("roles", Role.values());
        return "admin/utilisateur-form";
    }

    /**
     * Le mot de passe arrive en clair dans un champ séparé (jamais lié à l'entité)
     * et est haché par le service. En modification, vide = inchangé.
     */
    @PostMapping
    public String enregistrer(@ModelAttribute Utilisateur utilisateur,
                              @RequestParam String motDePasseClair,
                              RedirectAttributes attrs) {
        try {
            if (utilisateur.getId() == null) {
                utilisateurService.creer(utilisateur, motDePasseClair);
            } else {
                utilisateurService.modifier(utilisateur.getId(), utilisateur, motDePasseClair);
            }
        } catch (DataIntegrityViolationException e) {
            attrs.addFlashAttribute("erreur", "Ce login existe déjà.");
        }
        return "redirect:/admin/utilisateurs";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id, RedirectAttributes attrs) {
        try {
            utilisateurService.supprimer(id);
        } catch (DataIntegrityViolationException e) {
            attrs.addFlashAttribute("erreur",
                    "Impossible de supprimer : cet utilisateur est l'auteur d'articles ou a créé des jetons.");
        }
        return "redirect:/admin/utilisateurs";
    }
}