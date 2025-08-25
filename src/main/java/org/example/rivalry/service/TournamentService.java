package org.example.rivalry.service;

import org.example.rivalry.dto.TournamentDto;
import org.example.rivalry.entity.Tournament;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.GameRepository;
import org.example.rivalry.repository.TournamentRepository;

import java.util.List;

public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final GameRepository gameRepository;

    public TournamentService(TournamentRepository tournamentRepository, GameRepository gameRepository) {
        this.tournamentRepository = tournamentRepository;
        this.gameRepository = gameRepository;
    }

    public TournamentDto create(TournamentDto tournamentDto){
        return tournamentRepository.save(tournamentDto.dtoToEntity(gameRepository)).entityToDto();
    }

    public TournamentDto get(Long id){ return tournamentRepository.findById(id).orElseThrow(NotFoundException::new).entityToDto(); }

    public List<TournamentDto> get(){
        return tournamentRepository.findAll().stream().map(Tournament::entityToDto).toList();
    }

    public TournamentDto update(Long id, TournamentDto tournamentDto){
        Tournament tournament = tournamentRepository.findById(id).orElseThrow(NotFoundException::new);
        Tournament tournamentGet = tournamentDto.dtoToEntity(gameRepository);
        tournament.setName(tournamentGet.getName());
        tournament.setDescription(tournamentGet.getDescription());
        tournament.setBeginDate(tournamentGet.getBeginDate());
        tournament.setEndDate(tournamentGet.getEndDate());
        tournament.setFormat(tournamentGet.getFormat());
        tournament.setStatus(tournamentGet.getStatus());
        tournament.setNumberOfPlayers(tournamentGet.getNumberOfPlayers());
        tournament.setGame(tournamentGet.getGame());
        return tournamentRepository.save(tournament).entityToDto();
    }

    public void delete(Long id){
        Tournament tournament = tournamentRepository.findById(id).orElseThrow(NotFoundException::new);
        tournament.setIsActive(false);
        tournamentRepository.save(tournament);
    }
}
