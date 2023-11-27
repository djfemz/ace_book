package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.*;
import africa.semicolon.acebook.dtos.response.*;
import africa.semicolon.acebook.exceptions.AccountNotFoundException;
import africa.semicolon.acebook.models.AccountDetails;
import africa.semicolon.acebook.models.Account;
import africa.semicolon.acebook.repositories.AccountRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.acebook.models.Tier.BASIC;
import static africa.semicolon.acebook.utils.AppUtils.createPageRequest;
import static java.util.Arrays.stream;

@Service
@AllArgsConstructor
@Slf4j
public class AceBookAccountService implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final MailService mailService;
    private final PaymentService paymentService;

    @Override
    public RegisterResponse register(UserRegisterRequest registerRequest) {
        AccountDetails accountDetails = modelMapper.map(registerRequest, AccountDetails.class);
        Account account = new Account();
        account.setAccountDetails(accountDetails);
        account.setTier(BASIC);
        Account savedAccount = accountRepository.save(account);
        String email = savedAccount.getAccountDetails().getEmail();
        mailService.sendMail(buildMailRequest(email));
        RegisterResponse response = new RegisterResponse();
        response.setId(savedAccount.getId());
        return response;
    }

    @Override
    public UserResponse getUserBy(Long id) throws AccountNotFoundException{
        Account foundAccount = accountRepository.findById(id)
                .orElseThrow(()->new AccountNotFoundException(
                        String.format("account with id %d not found", id)
                ));
        return buildUserResponse(foundAccount);
    }

    @Override
    public List<UserResponse> getAllBasicAccounts(int page, int size) {
        Pageable pageable = createPageRequest(page, size);
        Page<Account> foundAccounts = accountRepository.findAll(pageable);
        List<Account> accounts = foundAccounts.getContent();
        return accounts.stream()
                       .map(account->buildUserResponse(account))
                       .toList();
    }

    @Override
    public UpdateUserResponse updateAccount(Long accountId, UpdateAccountRequest request) throws AccountNotFoundException, JsonPatchException {
        ObjectMapper mapper = new ObjectMapper();
        List<JsonPatchOperation> operations = new ArrayList<>(buildJsonPatchOperations(request));


        JsonPatch updatePatch = new JsonPatch(operations);

        Account foundAccount = accountRepository.findById(accountId)
                                        .orElseThrow(()-> getAccountNotFoundException(accountId));


        JsonNode accountNode = mapper.convertValue(foundAccount, JsonNode.class);
        JsonNode updatedNode = applyPatch(updatePatch, accountNode);

        var updatedAccount = mapper.convertValue(updatedNode, Account.class);
        accountRepository.save(updatedAccount);
        return new UpdateUserResponse("profile update successful");
    }

    @Override
    public AddFriendResponse addFriend(AddFriendRequest request) throws AccountNotFoundException {
        Account sender = accountRepository.findById(request.getSender()).orElseThrow(
                ()->new AccountNotFoundException("")
        );
        Account recipient = accountRepository.findById(request.getRecipient()).orElseThrow(
                ()->new AccountNotFoundException("")
        );

        return null;
    }

    @Override
    public ApiResponse<?> subscribeToPremium(UpgradeAccountRequest request) throws AccountNotFoundException {
        Account account = getAccount(request.getAccountId());
        CreatePaymentResponse<?> paymentResponse = paymentService.pay(buildPaymentRequest(account));

        ApiResponse<CreatePaymentResponse<?>> response = new ApiResponse<>();
        response.setData(paymentResponse);
        return response;
    }

    private CreatePaymentRequest buildPaymentRequest(Account account){
        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();
        createPaymentRequest.setEmail(account.getAccountDetails().getEmail());
        createPaymentRequest.setAmount(BigDecimal.valueOf(2000));
        return createPaymentRequest;
    }

    private Account getAccount(Long id) throws AccountNotFoundException {
        return accountRepository.findById(id)
                .orElseThrow(()->new AccountNotFoundException(
                        String.format("account with id %d not found", id)
                ));
    }

    private static List<ReplaceOperation> buildJsonPatchOperations(UpdateAccountRequest request) {
        Field[] fields = request.getClass().getDeclaredFields();
        return stream(fields).filter((field)-> isFieldNonNull(request, field))
                      .map((field) -> buildReplaceOperation(request, field))
                .toList();
    }

    private static boolean isFieldNonNull(UpdateAccountRequest request, Field field) {
        try {
            field.setAccessible(true);
            return field.get(request) != null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static ReplaceOperation buildReplaceOperation(UpdateAccountRequest request, Field field) {
        String prefix="/";
        if (field.getName().equals("email")||
        field.getName().equals("password")) prefix+="accountDetails/";
        try {
            return new ReplaceOperation(new JsonPointer(prefix+field.getName()),
            new TextNode(field.get(request).toString()));
        } catch (JsonPointerException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode applyPatch(JsonPatch updatePatch, JsonNode accountNode) throws JsonPatchException {
        try{
            JsonNode updatedNode = updatePatch.apply(accountNode);
            return updatedNode;
        }catch (JsonPatchException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    private static AccountNotFoundException getAccountNotFoundException(Long id) {
        return new AccountNotFoundException(
                String.format("account with id %d not found", id)
        );
    }

    private static UserResponse buildUserResponse(Account account) {
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstname(account.getFirstname());
        userResponse.setLastname(account.getLastname());
        userResponse.setEmail(account.getAccountDetails().getEmail());
        return userResponse;
    }

    private static SendMailRequest buildMailRequest(String email){
        SendMailRequest mailRequest = new SendMailRequest();
        Sender sender = new Sender("acebook", "acebook@email.com");
        List<Recipient> recipients = List.of(
                new Recipient(email, email)
        );
        mailRequest.setSubject("Welcome");
        mailRequest.setHtmlContent("<p>Hello Semicolon</p>");
        mailRequest.setSender(sender);
        mailRequest.setRecipients(recipients);
        return mailRequest;
    }
}
