package com.blog.springbootdeveloper.controller.view;

import com.blog.springbootdeveloper.domain.Article;
import com.blog.springbootdeveloper.dto.view.ArticleListViewResponse;
import com.blog.springbootdeveloper.dto.view.ArticleViewReponse;
import com.blog.springbootdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewReponse(article));
        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        // id 가 없으면 빈 ArticleViewResponse를 반환
        if (id == null) {
            model.addAttribute("article", new ArticleViewReponse());
        }
        else {
        // id 가 있으면 Article 조회 후 넘기기
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewReponse(article));
        }

        return "newArticle";
    }
}
