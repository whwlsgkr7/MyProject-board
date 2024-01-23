package com.myproject.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/articles")
@Controller
public class ArticleController {
    @GetMapping
    public String articles(Model m){
        m.addAttribute("articles", List.of());
        return "articles/index";
    }
}
