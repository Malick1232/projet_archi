package com.siteactualites.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siteactualites.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}