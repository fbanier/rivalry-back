package org.example.rivalry.service;

import org.example.rivalry.dto.MatchDto;
import org.example.rivalry.entity.Match;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.repository.*;

import java.util.List;

public class MatchService {

    private final MatchRepository matchRepository;
    private final TurnRepository turnRepository;
    private final UserPlayerRepository userPlayerRepository;

    public MatchService(MatchRepository matchRepository, TurnRepository turnRepository, UserPlayerRepository userPlayerRepository ) {
        this.matchRepository = matchRepository;
        this.turnRepository = turnRepository;
        this.userPlayerRepository = userPlayerRepository;
    }

    public MatchDto create(MatchDto matchDto) {
        return matchRepository.save(matchDto.dtoToEntity(turnRepository, userPlayerRepository)).entityToDto();
    }

    public MatchDto get(Long id){ return matchRepository.findById(id).orElseThrow(NotFoundException::new).entityToDto(); }

    public List<MatchDto> get(){
        return matchRepository.findAll().stream().map(Match::entityToDto).toList();
    }

    public MatchDto update(Long id, MatchDto matchDto){
        Match match = matchRepository.findById(id).orElseThrow(NotFoundException::new);
        Match matchGet = matchDto.dtoToEntity(turnRepository, userPlayerRepository);
        match.setNumber(matchGet.getNumber());
        match.setPlayerOne(matchGet.getPlayerOne());
        match.setPlayerTwo(matchGet.getPlayerTwo());
        match.setWinner(matchGet.getWinner());
        match.setMatchDate(matchGet.getMatchDate());
        match.setIsActive(matchGet.getIsActive());
        return matchRepository.save(match).entityToDto();
    }

    public void delete(Long id){
        Match match = matchRepository.findById(id).orElseThrow(NotFoundException::new);
        match.setIsActive(false);
        matchRepository.save(match);
    }
}
