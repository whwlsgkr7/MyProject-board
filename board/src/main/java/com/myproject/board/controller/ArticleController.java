package com.myproject.board.controller;

import com.myproject.board.domain.type.SearchType;
import com.myproject.board.dto.response.ArticleResponse;
import com.myproject.board.dto.response.ArticleWithCommentsResponse;
import com.myproject.board.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String articles(SearchType searchType,
                           String searchValue,
                           @PageableDefault(size=10, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                           Model m) {

        m.addAttribute("articles", articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from));
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, Model m){
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
        m.addAttribute("article", article);
        m.addAttribute("articleComments", article.articleCommentsResponse());
        return "articles/detail";
    }
}
