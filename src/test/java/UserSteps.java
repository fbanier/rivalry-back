import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.rivalry.dto.LoginRequestDto;
import org.example.rivalry.dto.RegisterRequestDto;
import org.example.rivalry.entity.UserPlayer;
import org.example.rivalry.exception.InvalidCredentials;
import org.example.rivalry.exception.UserAlreadyExistException;
import org.example.rivalry.repository.UserPlayerRepository;
import org.example.rivalry.security.JWTGenerator;
import org.example.rivalry.service.UserPlayerService;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserSteps {

    private RegisterRequestDto registerRequestDto;
    private LoginRequestDto loginRequestDto;
    private final UserPlayerRepository userRepository = Mockito.mock(UserPlayerRepository.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private final AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
    private final JWTGenerator generator = Mockito.mock(JWTGenerator.class);
    private final UserPlayerService userService =  new UserPlayerService(userRepository, passwordEncoder, authenticationManager, generator);

      /***************************************************/
     /******* Scenario: Creation of an account **********/
    /***************************************************/
    @Given("Player {string} who wants to create an account")
    public void player_who_wants_to_login_an_account_to_compete(String name) {
        registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername(name);
    }

    @When("Player register email : {string}, username : {string}, and password : {string}")
    public void player_register_email_username_and_password(String email, String username, String password) {
        registerRequestDto.setEmail(email);
        registerRequestDto.setUsername(username);
        registerRequestDto.setPassword(password);
    }

    @Then("Check if email {string} already exist and raise an error if it does")
    public void check_if_email_already_exist_and_raise_an_error_if_it_does(String email) {
        UserPlayer alreadyPlayer = new UserPlayer(
                registerRequestDto.getEmail(),
                registerRequestDto.getUsername(),
                registerRequestDto.getPassword(),
                0,
                true
        );
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(alreadyPlayer));
        Assert.assertThrows(UserAlreadyExistException.class, () -> { userService.register(registerRequestDto); });
    }

    @And("Player receive an confirmation email")
    public void player_receive_an_confirmation_email() {
        Mockito.when(userRepository.findByEmail(registerRequestDto.getEmail())).thenReturn(Optional.empty());
        String confirmationMail;
        try{
            confirmationMail = userService.register(registerRequestDto);
        } catch (UserAlreadyExistException e) {
            confirmationMail = e.getMessage();
        }
        Assert.assertEquals("user Register!", confirmationMail);
    }

    /****************************************************/
    /********** Scenario: Login to account *************/
    /**************************************************/

    @Given("Player who wants to login to his account")
    public void player_who_wants_to_login_to_his_account() {
        loginRequestDto = new LoginRequestDto();
    }

    @When("Player input email : {string} and password : {string}")
    public void player_input_email_and_password(String email, String password) {
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);
    }

    @Then("search the email in database and raise an error if not found")
    public void search_the_email_in_database_and_raise_an_error_if_not_found(){
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()))).thenThrow(AuthenticationException.class);
        Assert.assertThrows(InvalidCredentials.class, () -> { userService.login(loginRequestDto); });
    }

    /*@Then("search the email in database and raise an error if not found")
    public void search_the_email_in_database_and_raise_an_error_if_not_found(){
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(new ArrayList<>());
        Assert.assertThrows(NotFoundException.class, () -> { userService.connection(user); });
    }*/

/*    @And("compare the password with the one stored and raise an error if it doesn't match")
    public void compare_the_password_with_the_one_stored_and_raise_an_error_if_it_doesnt_match(){
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(List.of(user.dtoToEntity()));
        Mockito.when(password.checkPassword(user.getPassword(), user.getPassword())).thenReturn(false);
        Assert.assertThrows(WrongPasswordException.class, () -> { userService.connection(user); });
    }

    @Then("the player is logged")
    public void the_player_is_logged(){
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(List.of(user.dtoToEntity()));
        Mockito.when(password.checkPassword(user.getPassword(), user.getPassword())).thenReturn(true);
        Assert.assertTrue(userService.connection(user));
    }*/





}
