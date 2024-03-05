package com.galka.jenkinsspringexample;

import ch.qos.logback.classic.Logger;
import com.galka.jenkinsspringexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/")
public class Controller {

    Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(Controller.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/check")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("It works!");
    }

    @PutMapping(path = "/user/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest createUserRequest) {
        Optional<User> byUsername = userRepository.findByUsername(createUserRequest.username());
        if (byUsername.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Resource already exists");
        }
        logger.info("Creating user: {}", createUserRequest);
        User user = User.builder()
                .username(createUserRequest.username())
                .password(createUserRequest.password())
                .build();
        logger.info("Attempting to save user: {}", user);
        userRepository.save(user);
        logger.info("User saved");
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    @PostMapping(path = "/user/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> login(@RequestBody LoginUserRequest loginUserRequest) {
        logger.info("Logging in user: {}", loginUserRequest);
        Optional<User> user = userRepository.findByUsername(loginUserRequest.username());
        logger.info("Fetched user: {}", user);
        return ResponseEntity.ok(
                user.map(u -> validatePwd(u, loginUserRequest.password()))
                        .orElse("User not found"));
    }

    private String validatePwd(User user, String password) {
        boolean correct = user.getPassword().equals(password);
        logger.info("Password validation result: {}", correct);
        return String.valueOf(correct);
    }
}

@ControllerAdvice
class ErrorHandler {
    Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(Controller.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception e) {
        logger.error("Exception: {}", e.getMessage());
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
