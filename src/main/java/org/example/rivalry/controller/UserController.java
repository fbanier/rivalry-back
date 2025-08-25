package org.example.rivalry.controller;

import org.example.rivalry.dto.*;
import org.example.rivalry.entity.UserPlayer;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.exception.UserAlreadyExistException;
import org.example.rivalry.security.JWTGenerator;
import org.example.rivalry.service.UserPlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/public")
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserPlayerService userPlayerService;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator generator;

    public UserController(AuthenticationManager authenticationManager, UserPlayerService userPlayerService, PasswordEncoder passwordEncoder, JWTGenerator generator) {
        this.authenticationManager = authenticationManager;
        this.userPlayerService = userPlayerService;
        this.passwordEncoder = passwordEncoder;
        this.generator = generator;
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserPublicDto> getUser(@PathVariable Long id) {
        try{
            UserPublicDto userDto = userPlayerService.get(id);
            return ResponseEntity.ok(userDto);
        } catch (NotFoundException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDTO) throws NotFoundException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(userPlayerService.login(loginRequestDTO));
        }catch (Exception ex) {
            throw new NotFoundException();
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDTO) throws UserAlreadyExistException {
        RegisterResponseDto userRegistered;
        try{
            UserPlayer user = userPlayerService.register(registerRequestDTO);
            userRegistered = RegisterResponseDto.builder().id(user.getId_user()).email(user.getEmail()).username(user.getUsername()).role(user.getRole().ordinal()).build();
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok("User "+userRegistered.getId()+" registered successfully");
    }

}
