package com.galka.jenkinsspringexample;

import com.galka.jenkinsspringexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/")
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/check")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("It works!");
    }

    @PutMapping(path = "/user/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = User.builder()
                .username(createUserRequest.username())
                .password(createUserRequest.password())
                .build();
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    @PostMapping(path = "/user/login", consumes = "application/json")
    public ResponseEntity<String> login(@RequestBody LoginUserRequest loginUserRequest) {
        Optional<User> user = userRepository.findByUsername(loginUserRequest.username());
        return ResponseEntity.ok(
                user.map(u -> validatePwd(u, loginUserRequest.password()))
                        .orElse("User not found"));
    }

    private static String validatePwd(User user, String password) {
        boolean correct = user.getPassword().equals(password);
        return String.valueOf(correct);
    }
}

@ControllerAdvice
class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
