package com.blog.springbootdeveloper.service;

import com.blog.springbootdeveloper.domain.Article;
import com.blog.springbootdeveloper.dto.AddArticleRequest;
import com.blog.springbootdeveloper.dto.UpdateArticleRequest;
import com.blog.springbootdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    // Create
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    // Read
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" + id));
    }

    // Update
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // Delete
    public void delete(long id) {
        blogRepository.deleteById(id);
    }
}
