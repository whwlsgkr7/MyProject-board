package com.myproject.board.repository;

import com.myproject.board.domain.Article;
import com.myproject.board.domain.QArticle;
import com.myproject.board.repository.querydsl.ArticleRepositoryCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>, // 엔티티를 위한 기본 CRUD작업을 제공, entity와 pk 타입을 지넥릭 타입으로 넣어줌
        ArticleRepositoryCustom,
        QuerydslPredicateExecutor<Article>, // 복잡한 쿼리 로직을 생성하고 실행할 수 있음
        QuerydslBinderCustomizer<QArticle> {

    Page<Article> findByTitleContaining(String title, Pageable pageable);
    Page<Article> findByContentContaining(String content, Pageable pageable);
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);
    Page<Article> findByHashtag(String hashtag, Pageable pageable);


    @Override
    default void customize(QuerydslBindings bindings, QArticle root){ // 인터페이스에는 오직 추상메서드만 선언할 수 있었지만 JAVA8 이후로 default 메서드를 사용해 인터페이스 내에서 구현을 포함하는 메서드를 정의할 수 있다.
        bindings.excludeUnlistedProperties(true); // 명시적으로 바인딩되지 않은 속성들을 검색 쿼리에서 제외
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); // 검색 쿼리에서 포함시킬 속성들을 지정
        // 속성별 바인딩 규칙
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // 대소문자 구분하지 않음
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 정확히 일치하는 날짜로 검색
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);

    }
}
