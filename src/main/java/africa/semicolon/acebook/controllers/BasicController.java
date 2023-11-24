package africa.semicolon.acebook.controllers;


import africa.semicolon.acebook.dtos.request.UserRegisterRequest;
import africa.semicolon.acebook.services.BasicService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account/basic")
@AllArgsConstructor
public class BasicController {
    private final BasicService basicService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest userRegisterRequest){
        return ResponseEntity.ok(basicService.register(userRegisterRequest));
    }
}
