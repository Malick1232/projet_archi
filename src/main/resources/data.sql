INSERT INTO utilisateur (nom, prenom, login, mot_de_passe, role, date_creation)
VALUES
('Admin', 'Principal', 'admin', '$2a$10$7QJxLQdS8nqQ5M4jLk8gQe8P2Q3D9W0k2nP5Q2g5Q0V0Q9S6JmP8W', 'ADMIN', NOW()),
('Diallo', 'Editeur', 'editeur', '$2a$10$7QJxLQdS8nqQ5M4jLk8gQe8P2Q3D9W0k2nP5Q2g5Q0V0Q9S6JmP8W', 'EDITEUR', NOW());

INSERT INTO categorie (libelle, description)
VALUES
('Sport', 'Actualités sportives'),
('Politique', 'Actualités politiques'),
('Technologie', 'Actualités technologiques');
INSERT INTO article
(titre, resume, contenu, date_publication, categorie_id, auteur_id)
VALUES
('Article 1','Résumé 1','Contenu complet 1',NOW(),1,2),
('Article 2','Résumé 2','Contenu complet 2',NOW(),1,2),
('Article 3','Résumé 3','Contenu complet 3',NOW(),1,2),
('Article 4','Résumé 4','Contenu complet 4',NOW(),2,2),
('Article 5','Résumé 5','Contenu complet 5',NOW(),2,2),
('Article 6','Résumé 6','Contenu complet 6',NOW(),2,2),
('Article 7','Résumé 7','Contenu complet 7',NOW(),3,2),
('Article 8','Résumé 8','Contenu complet 8',NOW(),3,2),
('Article 9','Résumé 9','Contenu complet 9',NOW(),3,2),
('Article 10','Résumé 10','Contenu complet 10',NOW(),1,2);

INSERT INTO jeton
(valeur, cree_par, date_creation, actif)
VALUES
('123456789abcdef123456789abcdef123456789abcdef123456789abcdef1234',
1,
NOW(),
true);