package com.blog.springbootdeveloper.dto;

import com.blog.springbootdeveloper.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    String title;
    String content;

    public Article toEntity(){
        return Article.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
