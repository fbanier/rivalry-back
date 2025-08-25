package org.example.rivalry.service;

import org.example.rivalry.dto.BracketDto;
import org.example.rivalry.dto.TurnDto;
import org.example.rivalry.entity.Bracket;
import org.example.rivalry.entity.Turn;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.BracketRepository;
import org.example.rivalry.repository.TurnRepository;

import java.util.List;

public class TurnService {

    private final TurnRepository turnRepository;
    private final BracketRepository bracketRepository;


    public TurnService(TurnRepository turnRepository, BracketRepository bracketRepository) {
        this.turnRepository = turnRepository;
        this.bracketRepository = bracketRepository;
    }

    public TurnDto create(TurnDto turnDto){
        return turnRepository.save(turnDto.dtoToEntity(bracketRepository)).entityToDto();
    }

    public TurnDto get(Long id){ return turnRepository.findById(id).orElseThrow(NotFoundException::new).entityToDto(); }

    public List<TurnDto> get(){
        return turnRepository.findAll().stream().map(Turn::entityToDto).toList();
    }

    public TurnDto update(Long id, TurnDto turnDto){
        Turn turn = turnRepository.findById(id).orElseThrow(NotFoundException::new);
        Turn turnGet = turnDto.dtoToEntity(bracketRepository);
        turn.setBracket(turnGet.getBracket());
        turn.setPoints(turnGet.getPoints());
        turn.setIsActive(turnGet.getIsActive());
        return turnRepository.save(turn).entityToDto();
    }

    public void delete(Long id){
        Turn turn = turnRepository.findById(id).orElseThrow(NotFoundException::new);
        turn.setIsActive(false);
        turnRepository.save(turn);
    }
}
