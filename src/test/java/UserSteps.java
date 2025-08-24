import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.rivalry.dto.UserPlayerReceiveDto;
import org.example.rivalry.exception.AlreadyExistException;
import org.example.rivalry.exception.NotFoundException;
import org.example.rivalry.exception.WrongPasswordException;
import org.example.rivalry.repository.UserPlayerRepository;
import org.example.rivalry.service.UserPlayerService;
import org.example.rivalry.util.Password;
import org.junit.Assert;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class UserSteps {

    private UserPlayerReceiveDto user;
    private final UserPlayerRepository userRepository = Mockito.mock(UserPlayerRepository.class);
    private final Password password = Mockito.mock(Password.class);
    private final UserPlayerService userService =  new UserPlayerService(userRepository);

    @Given("Player {string} who wants to login to his account")
    public void player_who_wants_to_login_an_account_to_compete(String name) {
        user = new UserPlayerReceiveDto();
        user.setFirstName(name);
    }

    @When("Player register email : {string}, username : {string}, age : {string}, and password : {string}")
    public void player_register_email_username_and_password(String email, String username, String age, String password) {
        user.setEmail(email);
        user.setDateOfBirth(age);
        user.setUsername(username);
        user.setPassword(password);
    }

    @Then("Check if email {string} already exist and raise an error if it does")
    public void check_if_email_already_exist_and_raise_an_error_if_it_does(String email) {
        Mockito.when(userRepository.findByEmail(email)).thenReturn(List.of(user.dtoToEntity()));
        Assert.assertThrows(AlreadyExistException.class, () -> { userService.register(user); });
    }

    @And("Player receive an confirmation email")
    public void player_receive_an_confirmation_email() {
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(new ArrayList<>());
        Mockito.when(userRepository.save(user.dtoToEntity())).thenReturn(user.dtoToEntity());
        String confirmationMail = userService.register(user);
        Assert.assertEquals("user Register!", confirmationMail);
    }

    @When("Player input email : {string} and password : {string}")
    public void player_input_email_and_password(String email, String password) {
        user.setEmail(email);
        user.setPassword(password);
    }

    @Then("search the email in database and raise an error if not found")
    public void search_the_email_in_database_and_raise_an_error_if_not_found(){
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(new ArrayList<>());
        Assert.assertThrows(NotFoundException.class, () -> { userService.connection(user); });
    }

    @And("compare the password with the one stored and raise an error if it doesn't match")
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
    }





}
