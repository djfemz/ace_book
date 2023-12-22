package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.*;
import africa.semicolon.acebook.dtos.response.*;
import africa.semicolon.acebook.exceptions.AccountNotFoundException;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static africa.semicolon.acebook.utils.AppUtils.IMAGE_LOCATION;
import static africa.semicolon.acebook.utils.TestUtils.getTestImage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
//@Sql(scripts = {"/db/data.sql"})
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

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


        RegisterResponse response = accountService.register(userRegisterRequest);

        assertNotNull(response);
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void testGetById(){
        var response = accountService.register(userRegisterRequest);
        try {
            UserResponse userResponse = accountService.getUserBy(response.getId());

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
                accountService.getAllBasicAccounts(1, 3);
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
                accountService.updateAccount(107L, request);
        assertThat(updateUserResponse).isNotNull();
        UserResponse updatedGuy = accountService.getUserBy(107L);
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

        AddFriendResponse response = accountService.addFriend(request);

        assertThat(response).isNotNull();

    }


    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void testSubscribeForPremiumService() throws AccountNotFoundException {
        PremiumSubscriptionRequest request = new PremiumSubscriptionRequest();
        request.setAccountId(100L);
        request.setFile(getTestImage(IMAGE_LOCATION));

        ApiResponse<?> response = accountService.subscribeToPremium(request);
        log.info("response---->{}", response);
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
    }


    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void testUpgradeAccount() throws AccountNotFoundException {
        UpgradeAccountRequest request = new UpgradeAccountRequest();
        request.setAccountId(100L);
        request.setTransactionReference("tevijo3lnq");

        ApiResponse<String> response = accountService.upgradeAccountFor(request);
        assertThat(response).isNotNull();
        assertThat(response.getData()).containsIgnoringCase("Successful");




    }


}
