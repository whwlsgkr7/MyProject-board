package com.myproject.board.controller;

import com.myproject.board.domain.type.SearchType;
import com.myproject.board.dto.response.ArticleResponse;
import com.myproject.board.dto.response.ArticleWithCommentsResponse;
import com.myproject.board.service.ArticleService;
import com.myproject.board.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping
    public String articles(SearchType searchType,
                           String searchValue,
                           @PageableDefault(size=10, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                           Model m) {

        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

        m.addAttribute("articles", articles);
        m.addAttribute("paginationBarNumbers", barNumbers);
        m.addAttribute("searchTypes", SearchType.values());


        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, Model m){
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
        m.addAttribute("article", article);
        m.addAttribute("articleComments", article.articleCommentsResponse());
        m.addAttribute("totalCount", articleService.getArticleCount());
        return "articles/detail";
    }

    @GetMapping("/search-hashtag")
    public String searchHashtag(
            String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Model m
    ){
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages());
        List<String> hashtags = articleService.getHashtags();

        m.addAttribute("articles", articles);
        m.addAttribute("hashtags", hashtags);
        m.addAttribute("paginationBarNumbers", barNumbers);
        m.addAttribute("searchType", SearchType.HASHTAG);
        return "articles/search-hashtag";

    }
}
