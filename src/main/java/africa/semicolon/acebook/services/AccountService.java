package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.AddFriendRequest;
import africa.semicolon.acebook.dtos.request.UpdateAccountRequest;
import africa.semicolon.acebook.dtos.request.UpgradeAccountRequest;
import africa.semicolon.acebook.dtos.request.UserRegisterRequest;
import africa.semicolon.acebook.dtos.response.*;
import africa.semicolon.acebook.exceptions.AccountNotFoundException;
import com.github.fge.jsonpatch.JsonPatchException;

import java.util.List;

//TODO:segregate interface properly
public interface AccountService {
    RegisterResponse register(UserRegisterRequest registerRequest);

    UserResponse getUserBy(Long id) throws AccountNotFoundException;


    List<UserResponse> getAllBasicAccounts(int page, int size);

    UpdateUserResponse updateAccount(Long accountId, UpdateAccountRequest request) throws AccountNotFoundException, JsonPatchException;


    AddFriendResponse addFriend(AddFriendRequest request) throws AccountNotFoundException;

    ApiResponse<?> subscribeToPremium(UpgradeAccountRequest request) throws AccountNotFoundException;

}
