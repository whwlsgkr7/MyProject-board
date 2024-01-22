package com.myproject.board.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Spring Data REST 통합테스트는 불필요하므로 제외시킴")
@DisplayName("Data REST - API 테스트")
@Transactional // 테스트 메서드에 @Transactional을 붙이면 테스트 메서드가 완료된 후, 그 메서드 내에서 발생한 모든 데이터베이스 변경사항이 롤백된다
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {
    private final MockMvc mvc; // final이 붙은 변수는 생성자에서만 초기화 가능, 생성자 주입을 사용할 때 자주 사용
   // MockMvc는 웹 서버를 실제로 실행시키지 않고도 스프링 MVC의 동작을 시뮬레이션할 수 있게 해준다.
    // 이를 통해 HTTP 요청과 응답을 모의(Mocking)하여 컨트롤러의 동작을 테스트할 수 있다.
    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[api] 게시글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticles_thenReturnsArticlesJsonResponse() throws Exception {
        // given

        // when && then
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk()) // 응답 상태 코드가 200임을 확인
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json"))); // 응답 컨텐츠 타입이 application/hal+json임을 확인

    }

    @DisplayName("[api] 게시글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticles_thenReturnsArticleJsonResponse() throws Exception {
        // given

        // when && then
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk()) // 응답 상태 코드가 200임을 확인
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json"))); // 응답 컨텐츠 타입이 application/hal+json임을 확인

    }

    @DisplayName("[api] 게시글 -> 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleCommentsFromArticle_thenReturnsArticleCommentsJsonResponse() throws Exception {
        // given

        // when && then
        mvc.perform(get("/api/articles/1/articleComments"))
                .andExpect(status().isOk()) // 응답 상태 코드가 200임을 확인
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json"))); // 응답 컨텐츠 타입이 application/hal+json임을 확인

    }

    @DisplayName("[api] 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleComments_thenReturnsArticleCommentsJsonResponse() throws Exception {
        // given

        // when && then
        mvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk()) // 응답 상태 코드가 200임을 확인
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json"))); // 응답 컨텐츠 타입이 application/hal+json임을 확인

    }

    @DisplayName("[api] 댓글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticleComment_thenReturnsArticleCommentJsonResponse() throws Exception {
        // given

        // when && then
        mvc.perform(get("/api/articleComments/1"))
                .andExpect(status().isOk()) // 응답 상태 코드가 200임을 확인
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json"))); // 응답 컨텐츠 타입이 application/hal+json임을 확인

    }
}
