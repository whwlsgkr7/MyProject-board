//package com.myproject.board.controller;
//
//import com.myproject.board.config.SecurityConfig;
//import com.myproject.board.service.ArticleService;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.then;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@DisplayName("view 컨트롤러 - 게시글")
//@Import(SecurityConfig.class)
//@WebMvcTest(ArticleController.class)
//class ArticleControllerTest {
//    @MockBean
//    private ArticleService articleService;
//
//    private final MockMvc mvc;
//    public ArticleControllerTest(@Autowired MockMvc mvc) { // Test 패키지에서 생성자 주입 사용시 @Autowired 생략불가
//        this.mvc = mvc;
//    }
//
//    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
//    @Test
//    public void givenNothing_whenRequestingArticlesView_thenReturnArticlesView() throws Exception {
//        // given
//        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class) )).willReturn(Page.empty());
//
//        // when & then
//        mvc.perform(get("/articles")) // when
//                .andExpect(status().isOk()) // then
//                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andExpect(view().name("articles/index"))
//                .andExpect(model().attributeExists("articles"));
//        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class) );
//
//
//    }
//
//
//    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
//    @Test
//    public void givenNothing_whenRequestingArticleView_thenReturnArticleView() throws Exception {
//        // given
//        Long articleId = 1L;
//        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());
//
//        // when & then
//        mvc.perform(get("/articles/" + articleId)) // when
//                .andExpect(status().isOk()) // then
//                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andExpect(view().name("articles/detail"))
//                .andExpect(model().attributeExists("article"))
//                .andExpect(model().attributeExists("articleComments"));
//        then(articleService).should().getArticle(articleId);
//
//    }
//
//    @Disabled("구현 중")
//    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
//    @Test
//    public void givenNothing_whenRequestingArticleSearchView_thenReturnArticleSearchView() throws Exception {
//        // given
//
//        // when & then
//        mvc.perform(get("/articles/search")) // when
//                .andExpect(status().isOk()) // then
//                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andExpect(view().name("articles/search"));
//
//
//    }
//
//    @Disabled("구현 중")
//    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
//    @Test
//    public void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnArticleHashtagSearchView() throws Exception {
//        // given
//
//        // when & then
//        mvc.perform(get("/articles/search-hashtag")) // when
//                .andExpect(status().isOk()) // then
//                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andExpect(view().name("articles/search-hashtag"));
//
//    }
//}