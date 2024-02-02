package com.myproject.board.dto;

import java.time.LocalDateTime;

public record ArticleCommentDto(
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String content
) {
    public static ArticleCommentDto of(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String content) {
        return new ArticleCommentDto(createdAt, createdBy, modifiedAt, content);
    }
}
