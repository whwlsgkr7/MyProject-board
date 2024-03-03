package com.myproject.board.service;

import com.myproject.board.domain.Article;
import com.myproject.board.domain.ArticleComment;
import com.myproject.board.domain.UserAccount;
import com.myproject.board.dto.ArticleCommentDto;
import com.myproject.board.dto.UserAccountDto;
import com.myproject.board.repository.ArticleCommentRepository;
import com.myproject.board.repository.ArticleRepository;
import com.myproject.board.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks private ArticleCommentService sut;
    @Mock private ArticleCommentRepository articleCommentRepository;
    @Mock private ArticleRepository articleRepository;
    @Mock private UserAccountRepository userAccountRepository;





//    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
//    @Test
//    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments(){
//        // given
//        Long articleId = 1L;
//
////        given(articleRepository.findById(articleId)).willReturn(Optional.of(
////                Article.of("title", "content", "#java")));
//
//        // when
//        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);
//
//        // then
//        assertThat(articleComments).isNotNull();
//        then(articleRepository).should().findById(articleId);
//    }
//


    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSavesArticleComment(){
        // given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willReturn(createArticle());
        given(userAccountRepository.getReferenceById(dto.userAccountDto().userId())).willReturn(createUserAccount());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // when
        sut.saveArticleComment(dto);

        // then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(userAccountRepository).should().getReferenceById(dto.userAccountDto().userId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }


    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면, 경고 로그를 찍고 아무것도 안 한다.")
    @Test
    void givenNonexistentArticle_whenSavingArticleComment_thenLogsSituationAndDoesNothing() {
        // Given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willThrow(EntityNotFoundException.class);
        // When
        sut.saveArticleComment(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(userAccountRepository).shouldHaveNoInteractions();
        then(articleCommentRepository).shouldHaveNoInteractions();
    }


    @DisplayName("댓글 ID를 입력하면, 댓글을 삭제한다.")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() {
        // Given
        Long articleCommentId = 1L;
        String userId = "uno";
        willDoNothing().given(articleCommentRepository).deleteByIdAndUserAccount_UserId(articleCommentId, userId);

        // When
        sut.deleteArticleComment(articleCommentId, userId);

        // Then
        then(articleCommentRepository).should().deleteByIdAndUserAccount_UserId(articleCommentId, userId);
    }




    private Article createArticle(){
        return Article.of(createUserAccount(), "title", "content", "#hashtag");
    }

    private ArticleComment createArticleComment(){
        return ArticleComment.of(createArticle(), createUserAccount(), "commentContent");
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "jinhak",
                "password",
                "jinhak@email.com",
                "jinhak",
                null
        );
    }

    private UserAccountDto createUserAccountDto(){
        return UserAccountDto.of("jinhak", "password", "email", "jinhak", "memo", LocalDateTime.now(), "jinhak", LocalDateTime.now(), "jinhak");
    }

    private ArticleCommentDto createArticleCommentDto(String content){
        return ArticleCommentDto.of(1L, 1L, createUserAccountDto(), "content", LocalDateTime.now(), "jinhak", LocalDateTime.now(), "jinhak");
    }
}