package africa.semicolon.acebook.controllers;


import africa.semicolon.acebook.dtos.request.UserRegisterRequest;
import africa.semicolon.acebook.dtos.response.RegisterResponse;
import africa.semicolon.acebook.dtos.response.UserResponse;
import africa.semicolon.acebook.exceptions.AccountNotFoundException;
import africa.semicolon.acebook.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account/basic")
@AllArgsConstructor
public class BasicController {
    private final AccountService basicService;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@RequestBody UserRegisterRequest userRegisterRequest){
        return ResponseEntity.ok(basicService.register(userRegisterRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) throws AccountNotFoundException {
        return ResponseEntity.ok(basicService.getUserBy(id));
    }
}
