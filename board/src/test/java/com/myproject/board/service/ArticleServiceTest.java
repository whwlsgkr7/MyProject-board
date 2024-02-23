package com.myproject.board.service;

import com.myproject.board.domain.Article;
import com.myproject.board.domain.ArticleComment;
import com.myproject.board.domain.UserAccount;
import com.myproject.board.domain.type.SearchType;
import com.myproject.board.dto.ArticleDto;
import com.myproject.board.dto.ArticleUpdateDto;
import com.myproject.board.dto.ArticleWithCommentsDto;
import com.myproject.board.dto.UserAccountDto;
import com.myproject.board.repository.ArticleRepository;
import com.myproject.board.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserAccountRepository userAccountRepository;
    @InjectMocks
    private ArticleService sut;


    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenNoSearchParameter_whenSearchingArticles_thenReturnsArticlePage() {
        // given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        // when
        Page<ArticleDto> articles = sut.searchArticles(null, null, pageable);

        // then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }


    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameter_whenSearchingArticle_thenReturnArticlePage() {
        // given
        SearchType searchType = SearchType.CONTENT;
        String searchKeyword = "content";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByContentContaining(searchKeyword, pageable)).willReturn(Page.empty());

        // when
        Page<ArticleDto> articles = sut.searchArticles(searchType, searchKeyword, pageable);

        // then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByContentContaining(searchKeyword, pageable);
    }



    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle(){
        // given
        Long articleId = 1L;
        willDoNothing().given(articleRepository).delete(any(Article.class));

        // when
        sut.deleteArticle(1L);

        // then
        then(articleRepository).should().delete(any(Article.class));
    }


    @DisplayName("검색어 없이 해시태그 검색하면, 빈 페이지를 반환한다.")
    @Test
    void givenNoSearchParameters_whenSearchingArticlesViaHashtag_thenReturnEmptyPage(){
        // given
        Pageable pageable = Pageable.ofSize(20);

        // when
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(null, pageable);

        // then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).shouldHaveNoInteractions();
    }

    @DisplayName("검색어를 입력하여 해시태그 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenHashtag_whenSearchingArticlesViaHashtag_thenReturnArticlesPage(){
        // given
        String hashtag = "#java";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByHashtag(hashtag, pageable)).willReturn(Page.empty(pageable));

        // when
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(hashtag, pageable);

        // then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).should().findByHashtag(hashtag, pageable);
    }

    @DisplayName("해시태그를 조회하면, 유니크 해시태그 리스트를 반환한다.")
    @Test
    void givenNothing_whenCalling_thenReturnsHashtags(){
        // given
        List<String> expectedHashtags = List.of("java", "#spring", "#boot");
        given(articleRepository.findAllDistinctHashtags()).willReturn(expectedHashtags);

        // when
        List<String> actualHashtags = sut.getHashtags();

        // then
        assertThat(actualHashtags).isEqualTo(expectedHashtags);
        then(articleRepository).should().findAllDistinctHashtags();
     }




    @DisplayName("게시글 수를 조회하면, 게시글 수를 반환한다.")
    @Test
    void givenNothing_whenCountingArticles_thenReturnAricleCount(){
        // given
        long expected = 0L;
        given(articleRepository.count()).willReturn(expected);
        // when
        long cnt = sut.getArticleCount();

        // then
        assertThat(cnt).isEqualTo(expected);
        then(articleRepository).should().count();
    }





    private UserAccount createUserAccount() {
        return createUserAccount("jinhak");
    }

    private UserAccount createUserAccount(String userId) {
        return UserAccount.of(
                userId,
                "password",
                "jinhak@email.com",
                "jinhak",
                null
        );
    }


//
//
//    private ArticleDto createArticleDto() {
//        return createArticleDto("title", "content");
//    }
//
//    private ArticleDto createArticleDto(String title, String content) {
//        return ArticleDto.of(
//                1L,
//                createUserAccountDto(),
//                title,
//                content,
//                null,
//                LocalDateTime.now(),
//                "jinhak",
//                LocalDateTime.now(),
//                "jinhak");
//    }
//
//    private UserAccountDto createUserAccountDto() {
//        return UserAccountDto.of(
//                "jinhak",
//                "password",
//                "jinhak@mail.com",
//                "jinhak",
//                "This is memo",
//                LocalDateTime.now(),
//                "jinhak",
//                LocalDateTime.now(),
//                "jinhak"
//        );
//    }

}