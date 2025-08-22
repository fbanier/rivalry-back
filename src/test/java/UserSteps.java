import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.rivalry.dto.UserPlayerReceiveDto;
import org.example.rivalry.exception.AlreadyExistException;
import org.example.rivalry.repository.UserPlayerRepository;
import org.example.rivalry.service.UserPlayerService;
import org.junit.Assert;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class UserSteps {

    private UserPlayerReceiveDto user;
    private final UserPlayerRepository userRepository = Mockito.mock(UserPlayerRepository.class);
    private final UserPlayerService userService =  new UserPlayerService(userRepository);

    @Given("Player {string} who want to create an account to compete")
    public void player_who_want_to_create_an_account_to_compete(String player) {
        user = new UserPlayerReceiveDto();
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
        String confirmationMail = userService.register(user);
        Assert.assertEquals("user Register!", confirmationMail);
    }


}
