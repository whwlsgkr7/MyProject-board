package com.myproject.board.repository;

import com.myproject.board.config.JpaConfig;
import com.myproject.board.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트") // 해당 테스트 이름 정의
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;
    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                             @Autowired ArticleCommentRepository articleCommentRepository,
                             @Autowired UserAccountRepository userAccountRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }


    @DisplayName("update 테스트")
    @Test
    void given_when_then(){
        // given 준비
        Article article = articleRepository.findById(1L).orElseThrow();
        String updateHashtag = "#springboot";
        article.setHashtag(updateHashtag);

        // when 실행
        Article savedArticle = articleRepository.saveAndFlush(article);
// Flush는 영속성 컨텍스트에 캐시된 모든 변경사항을 데이터베이스에 즉시 반영하도록 강제한다.
        // then 검증
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updateHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void given_whenDeleting_then(){
        // given 준비
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();


        // when 실행
        articleRepository.delete(article);

        // then 검증
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
    }
}
