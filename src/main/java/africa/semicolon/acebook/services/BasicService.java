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

import java.util.List;

public interface BasicService {
    RegisterResponse register(UserRegisterRequest registerRequest);

    UserResponse getUserBy(Long id) throws AccountNotFoundException;


    List<UserResponse> getAllBasicAccounts(int page, int size);

    UpdateUserResponse updateAccount(Long accountId, UpdateAccountRequest request) throws AccountNotFoundException, JsonPatchException;


    AddFriendResponse addFriend(AddFriendRequest request) throws AccountNotFoundException;

}
