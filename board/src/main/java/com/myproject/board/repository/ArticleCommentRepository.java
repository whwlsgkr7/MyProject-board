package com.myproject.board.repository;

import com.myproject.board.domain.Article;
import com.myproject.board.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
