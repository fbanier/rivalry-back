package org.example.rivalry.service;

import org.example.rivalry.dto.BracketDto;
import org.example.rivalry.dto.GameDto;
import org.example.rivalry.entity.Bracket;
import org.example.rivalry.entity.Game;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.BracketRepository;
import org.example.rivalry.repository.GameRepository;
import org.example.rivalry.repository.TournamentRepository;

import java.util.List;

public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameDto create(GameDto gameDto) {
        return gameRepository.save(gameDto.dtoToEntity()).entityToDto();
    }

    public GameDto get(Long id){ return gameRepository.findById(id).orElseThrow(NotFoundException::new).entityToDto(); }

    public List<GameDto> get(){
        return gameRepository.findAll().stream().map(Game::entityToDto).toList();
    }

    public GameDto update(Long id, GameDto gameDto){
        Game game = gameRepository.findById(id).orElseThrow(NotFoundException::new);
        Game gameGet = gameDto.dtoToEntity();
        game.setTitle(gameGet.getTitle());
        game.setDescription(gameGet.getDescription());
        game.setImage(gameGet.getImage());
        game.setIsActive(gameGet.getIsActive());
        return gameRepository.save(game).entityToDto();
    }

    public void delete(Long id){
        Game game = gameRepository.findById(id).orElseThrow(NotFoundException::new);
        game.setIsActive(false);
        gameRepository.save(game);
    }
}
