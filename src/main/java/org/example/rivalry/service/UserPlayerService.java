package org.example.rivalry.service;

import org.example.rivalry.dto.UserPlayerAuthResponseDto;
import org.example.rivalry.dto.UserPlayerReceiveDto;
import org.example.rivalry.dto.UserPlayerResponseDto;
import org.example.rivalry.entity.UserPlayer;
import org.example.rivalry.exception.AlreadyExistException;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.exception.WrongPasswordException;
import org.example.rivalry.repository.UserPlayerRepository;
import org.example.rivalry.util.Password;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPlayerService {

    private final UserPlayerRepository userPlayerRepository;

    public UserPlayerService(UserPlayerRepository userPlayerRepository) {
        this.userPlayerRepository = userPlayerRepository;
    }

    protected UserPlayerResponseDto create(UserPlayerReceiveDto UserPlayerReceiveDto){ return userPlayerRepository.save(UserPlayerReceiveDto.dtoToEntity()).entityToPublicDto(); }

    public UserPlayerResponseDto get(Long id){ return userPlayerRepository.findById(id).orElseThrow(NotFoundException::new).entityToPublicDto(); }

    public UserPlayerAuthResponseDto getAuth(Long id){ return userPlayerRepository.findById(id).orElseThrow(NotFoundException::new).entityToDto(); }

    public List<UserPlayerResponseDto> get(){
        return userPlayerRepository.findAll().stream().map(UserPlayer::entityToPublicDto).toList();
    }

    public List<UserPlayerAuthResponseDto> getAuth(){
        return userPlayerRepository.findAll().stream().map(UserPlayer::entityToDto).toList();
    }

    public List<UserPlayerResponseDto> getByUsername(String username){
        return null;
    }

    public String register (UserPlayerReceiveDto user){
        List<UserPlayer> usersFound = userPlayerRepository.findByEmail(user.getEmail());
        if(usersFound.isEmpty()){
            user.setPassword(Password.hashPassword(user.getPassword()));
            this.create(user);
            return "user Register!";
        }
        throw new AlreadyExistException("User Already Exist");
    }

    public String connection (UserPlayer user){
        List<UserPlayer> usersFound = userPlayerRepository.findByEmail(user.getUsername());
        if(!usersFound.isEmpty() && !usersFound.stream().filter(u -> u.getEmail().equals(user.getEmail())).toList().isEmpty()){
            String password = usersFound.getFirst().getPassword();
            if(Password.checkPassword(password, user.getPassword())){
                return "user is Log:"+user;
            }
            throw new WrongPasswordException("Wrong Password");
        }
        throw new NotFoundException();
    }

    public UserPlayerAuthResponseDto update(Long id, UserPlayerReceiveDto UserPlayerReceiveDto){
        UserPlayer UserPlayerToUpdate = userPlayerRepository.findById(id).orElseThrow(NotFoundException::new);
        UserPlayer UserPlayerGet = UserPlayerReceiveDto.dtoToEntity();
        UserPlayerToUpdate.setEmail(UserPlayerGet.getEmail());
        UserPlayerToUpdate.setUsername(UserPlayerGet.getUsername());
        UserPlayerToUpdate.setFirstName(UserPlayerGet.getFirstName());
        UserPlayerToUpdate.setLastName(UserPlayerGet.getLastName());
        UserPlayerToUpdate.setDateOfBirth(UserPlayerGet.getDateOfBirth());
        UserPlayerToUpdate.setAvatar(UserPlayerGet.getAvatar());
        UserPlayerToUpdate.setRole(UserPlayerGet.getRole());
        return userPlayerRepository.save(UserPlayerToUpdate).entityToDto();
    }

    public boolean updatePass(Long id, UserPlayerReceiveDto UserPlayerReceiveDto){
        UserPlayer user =  userPlayerRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setPassword(Password.hashPassword(UserPlayerReceiveDto.getPassword()));
        try{
            userPlayerRepository.save(user);
        } catch (Exception e){
            return false;
        }
        return true;
    }


    public void delete(Long id){ userPlayerRepository.deleteById(id); }
}
