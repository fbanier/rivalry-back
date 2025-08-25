package org.example.rivalry.service;

import org.example.rivalry.dto.*;
import org.example.rivalry.entity.Tournament;
import org.example.rivalry.entity.UserPlayer;
import org.example.rivalry.exception.InvalidCredentials;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.exception.UserAlreadyExistException;
import org.example.rivalry.repository.UserPlayerRepository;
import org.example.rivalry.security.JWTGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserPlayerService {

    private final UserPlayerRepository userPlayerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator generator;

    public UserPlayerService(UserPlayerRepository userPlayerRepository, PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager, JWTGenerator generator ) {
        this.userPlayerRepository = userPlayerRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.generator = generator;
    }

    protected UserPlayer create(UserPlayer userPlayer){ return userPlayerRepository.save(userPlayer); }

    public UserPublicDto get(Long id){ return userPlayerRepository.findById(id).orElseThrow(NotFoundException::new).entityToPublicDto(); }

    public UserDto getAuth(Long id){ return userPlayerRepository.findById(id).orElseThrow(NotFoundException::new).entityToDto(); }

    public List<UserPublicDto> get(){
        return userPlayerRepository.findAll().stream().map(UserPlayer::entityToPublicDto).toList();
    }

    public String register (RegisterRequestDto registerRequestDto) throws UserAlreadyExistException{
        Optional<UserPlayer> userOptional = userPlayerRepository.findByEmail(registerRequestDto.getEmail());
        if(userOptional.isEmpty()){
            registerRequestDto.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
            UserPlayer user = new UserPlayer(
                    registerRequestDto.getEmail(),
                    registerRequestDto.getUsername(),
                    registerRequestDto.getPassword(),
                    0,
                    true
            );
            this.create(user);
            return "user Register!";
        }
        throw new UserAlreadyExistException();
    }

    public LoginResponseDto login (LoginRequestDto loginRequestDto)  throws InvalidCredentials {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
             return LoginResponseDto.builder().token(generator.generateToken(authentication)).build();
        } catch (Exception ex) {
            throw new InvalidCredentials("Invalid Email or Password");
        }
    }

    public UserDto update(Long id, UserDto userDto){
        UserPlayer UserPlayerToUpdate = userPlayerRepository.findById(id).orElseThrow(NotFoundException::new);
        UserPlayerToUpdate.setUsername(userDto.getUsername());
        UserPlayerToUpdate.setFirstName(userDto.getFirstName());
        UserPlayerToUpdate.setLastName(userDto.getLastName());
        UserPlayerToUpdate.setDateOfBirth(LocalDate.parse(userDto.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        UserPlayerToUpdate.setAvatar(userDto.getAvatar());
        UserPlayerToUpdate.setIsActive(userDto.getActive());
        return userPlayerRepository.save(UserPlayerToUpdate).entityToDto();
    }

    public boolean updatePass(Long id, RegisterRequestDto registerRequestDto){
        UserPlayer user =  userPlayerRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        try{
            userPlayerRepository.save(user);
        } catch (Exception e){
            return false;
        }
        return true;
    }


    public void delete(Long id){
        UserPlayer user = userPlayerRepository.findById(id).orElseThrow(NotFoundException::new);
        user.setIsActive(false);
        userPlayerRepository.save(user);
    }
}
