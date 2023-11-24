package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.AddFriendRequest;
import africa.semicolon.acebook.dtos.request.UpdateAccountRequest;
import africa.semicolon.acebook.dtos.request.UserRegisterRequest;
import africa.semicolon.acebook.dtos.response.AddFriendResponse;
import africa.semicolon.acebook.dtos.response.RegisterResponse;
import africa.semicolon.acebook.dtos.response.UpdateUserResponse;
import africa.semicolon.acebook.dtos.response.UserResponse;
import africa.semicolon.acebook.exceptions.AccountNotFoundException;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
//@Sql(scripts = {"/db/data.sql"})
public class BasicServiceTest {

    @Autowired
    private BasicService basicService;

    private UserRegisterRequest userRegisterRequest;

    @BeforeEach
    public void setUp(){
        userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("joxayi1333@nexxterp.com");
        userRegisterRequest.setPassword("password");
    }

    @Test
    @DisplayName("test that a user can register to the basic tier account")
    public void testRegister(){


        RegisterResponse response = basicService.register(userRegisterRequest);

        assertNotNull(response);
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void testGetById(){
        var response = basicService.register(userRegisterRequest);
        try {
            UserResponse userResponse = basicService.getUserBy(response.getId());

            assertThat(userResponse).isNotNull();
            assertThat(userResponse.getEmail())
                    .isEqualTo(userRegisterRequest.getEmail());
        }catch (AccountNotFoundException exception){
            assertThat(exception).isNull();
        }
    }

    @Test
    public void testGetUsers(){
        List<UserResponse> accounts =
                basicService.getAllBasicAccounts(1, 3);
        log.info("accounts-->{}", accounts);
        assertThat(accounts).hasSize(3);
    }

    @Test
    public void testUpdateBasicAccount() throws AccountNotFoundException, JsonPatchException {
        UpdateAccountRequest request  = new UpdateAccountRequest();
        request.setEmail("swit@email.com");
        request.setFirstname("John");
        request.setLastname("Akpan");
        UpdateUserResponse updateUserResponse =
                basicService.updateAccount(107L, request);
        assertThat(updateUserResponse).isNotNull();
        UserResponse updatedGuy = basicService.getUserBy(107L);
        assertThat(updatedGuy.getEmail())
                .isEqualTo(request.getEmail());
        assertThat(updatedGuy.getLastname()).isEqualTo(request.getLastname());
    }


    @Test
    public void addFriendTest() throws AccountNotFoundException {
        AddFriendRequest request = new AddFriendRequest();
        request.setSender(1L);
        request.setRecipient(2L);
        request.setMessage("Hi, I'd like to be your friend. You do?, abi you no do? ");

        AddFriendResponse response = basicService.addFriend(request);

        assertThat(response).isNotNull();

    }
}
