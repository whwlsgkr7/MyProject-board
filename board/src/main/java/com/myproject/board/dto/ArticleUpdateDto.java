package com.myproject.board.dto;

public record ArticleUpdateDto(String title,
                               String content,
                               String hashtag) {
    // 정적 팩토리 메서드
    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag);
    }
}
