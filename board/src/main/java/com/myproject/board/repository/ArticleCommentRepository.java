package com.myproject.board.repository;

import com.myproject.board.domain.ArticleComment;
import com.myproject.board.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>,
        QuerydslBinderCustomizer<QArticleComment>{

    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root){ // 인터페이스에는 오직 추상메서드만 선언할 수 있었지만 JAVA8 이후로 default 메서드를 사용해 인터페이스 내에서 구현을 포함하는 메서드를 정의할 수 있다.
        bindings.excludeUnlistedProperties(true); // 명시적으로 바인딩되지 않은 속성들을 검색 쿼리에서 제외
        bindings.including(root.content, root.createdAt, root.createdBy); // 검색 쿼리에서 포함시킬 속성들을 지정
        // 속성별 바인딩 규칙
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // 대소문자 구분하지 않음
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 정확히 일치하는 날짜로 검색
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);

    }
}

