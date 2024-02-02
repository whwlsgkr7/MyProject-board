package com.myproject.board.dto;

import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleDto(LocalDateTime createdAt,
                         String createdBy,
                         String title,
                         String content,
                         String hashtag) {
    // 정적 팩토리 메서드
    public static ArticleDto of(LocalDateTime createdAt, String createdBy, String title, String content, String hashtag) {
        return new ArticleDto(createdAt, createdBy, title, content, hashtag);
    }
}
