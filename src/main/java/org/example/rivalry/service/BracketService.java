package org.example.rivalry.service;

import org.example.rivalry.dto.BracketDto;
import org.example.rivalry.dto.TournamentDto;
import org.example.rivalry.entity.Bracket;
import org.example.rivalry.entity.Tournament;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.BracketRepository;
import org.example.rivalry.repository.GameRepository;
import org.example.rivalry.repository.TournamentRepository;

import java.util.List;

public class BracketService {

    private final BracketRepository bracketRepository;
    private final TournamentRepository tournamentRepository;

    public BracketService(BracketRepository bracketRepository, TournamentRepository tournamentRepository) {
        this.bracketRepository = bracketRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public BracketDto create(BracketDto bracketDto){
        return bracketRepository.save(bracketDto.dtoToEntity(tournamentRepository)).entityToDto();
    }

    public BracketDto get(Long id){ return bracketRepository.findById(id).orElseThrow(NotFoundException::new).entityToDto(); }

    public List<BracketDto> get(){
        return bracketRepository.findAll().stream().map(Bracket::entityToDto).toList();
    }

    public BracketDto update(Long id, BracketDto bracketDto){
        Bracket bracket = bracketRepository.findById(id).orElseThrow(NotFoundException::new);
        Bracket bracketGet = bracketDto.dtoToEntity(tournamentRepository);
        bracket.setName(bracketGet.getName());
        bracket.setTournament(bracketGet.getTournament());
        return bracketRepository.save(bracket).entityToDto();
    }

    public void delete(Long id){
        Bracket bracket = bracketRepository.findById(id).orElseThrow(NotFoundException::new);
        bracket.setIsActive(false);
        bracketRepository.save(bracket);
    }
}
