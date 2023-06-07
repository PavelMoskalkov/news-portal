package org.newsportal.service.impl;

import lombok.RequiredArgsConstructor;
import org.newsportal.database.entity.Article;
import org.newsportal.database.repository.ArticleRepository;
import org.newsportal.service.ArticleService;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.model.ArticleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    private final ArticleRepository articleRepository;
    @Override
    public List<ArticleDto> getAll() {
        return articleMapper.toDto(articleRepository.findAll()
                .orElseThrow(() -> new RuntimeException("no articles found")));
    }

    @Override
    public ArticleDto getById(Long id) {
        return articleMapper.toDto(articleRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("no article found")));
    }

    @Override
    public Long add(ArticleDto article) {
        return articleRepository.create(articleMapper.toEntity(article))
                .orElseThrow(()-> new RuntimeException("no article found"));
    }

    @Override
    public Article changeById(Long id, ArticleDto articleDto) {
        return articleRepository.updateById(id, articleMapper.toEntity(articleDto))
                .orElseThrow(()-> new RuntimeException("Article not found"));
    }

    @Override
    public void removeById(Long id) {
        articleRepository.deleteById(id);
    }
}
