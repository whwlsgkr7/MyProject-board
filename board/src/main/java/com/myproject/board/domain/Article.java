package com.myproject.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields{
    @Id // pk로 사용할 속성
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 번호 증가
    private Long id;

    @Setter @ManyToOne(optional = false) @JoinColumn(name = " userId")
    private UserAccount userAccount; // 유저 정보 (ID)

    @Setter @Column(nullable = false)
    private String title; // 제목
    @Setter @Column(nullable = false, length = 10000)
    private String content; // 내용

    @Setter
    private String hashtag; // 해시태그

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();
    // 양방향 매핑에서 mappedBy를 쓰는 쪽은 FK를 생성하지 않는다.  CascadeType.ALL은 모든 종류의 작업
    //(생성, 삭제, 업데이트 등)이 자식 엔티티에도 적용됨을 의미한다. 예를 들어, Article 엔티티를 삭제하면 관련된 모든 ArticleComment 엔티티도 함께 삭제
    // 이 줄은 Article 엔티티가 여러 개의 ArticleComment를 가질 수 있음을 나타내며, 이를 저장하기 위해 Set 컬렉션을 사용

    
    protected Article(){}

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
