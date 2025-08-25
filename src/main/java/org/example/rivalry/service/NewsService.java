package org.example.rivalry.service;

import org.example.rivalry.dto.NewsDto;
import org.example.rivalry.entity.News;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.NewsRepository;

import java.util.List;

public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public NewsDto create(NewsDto newsDto) {
        return newsRepository.save(newsDto.dtoToEntity()).entityToDto();
    }

    public NewsDto get(Long id){ return newsRepository.findById(id).orElseThrow(NotFoundException::new).entityToDto(); }

    public List<NewsDto> get(){
        return newsRepository.findAll().stream().map(News::entityToDto).toList();
    }

    public NewsDto update(Long id, NewsDto newsDto){
        News news = newsRepository.findById(id).orElseThrow(NotFoundException::new);
        News newsGet = newsDto.dtoToEntity();
        news.setName(newsGet.getName());
        news.setPreamble(newsGet.getPreamble());
        news.setText(newsGet.getText());
        news.setImage(newsGet.getImage());
        news.setDate(newsGet.getDate());
        news.setIsActive(newsGet.getIsActive());
        return newsRepository.save(news).entityToDto();
    }

    public void delete(Long id){
        News news = newsRepository.findById(id).orElseThrow(NotFoundException::new);
        news.setIsActive(false);
        newsRepository.save(news);
    }
}
