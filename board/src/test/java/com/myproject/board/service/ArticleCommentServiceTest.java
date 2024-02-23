package com.myproject.board.service;

import com.myproject.board.domain.Article;
import com.myproject.board.domain.ArticleComment;
import com.myproject.board.domain.UserAccount;
import com.myproject.board.dto.ArticleCommentDto;
import com.myproject.board.dto.UserAccountDto;
import com.myproject.board.repository.ArticleCommentRepository;
import com.myproject.board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks private ArticleCommentService sut;
    @Mock private ArticleCommentRepository articleCommentRepository;
    @Mock private ArticleRepository articleRepository;




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
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // when
        sut.saveArticleComment(createArticleCommentDto());

        // then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
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

    private ArticleCommentDto createArticleCommentDto(){
        return ArticleCommentDto.of(1L, 1L, createUserAccountDto(), "content", LocalDateTime.now(), "jinhak", LocalDateTime.now(), "jinhak");
    }
}