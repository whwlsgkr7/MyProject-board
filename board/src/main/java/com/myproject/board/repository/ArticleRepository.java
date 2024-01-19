package com.myproject.board.repository;

import com.myproject.board.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> { // entity와 pk를 지넥릭 타입으로 넣어줌

}
