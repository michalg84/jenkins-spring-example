package com.galka.jenkinsspringexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@org.springframework.stereotype.Controller()
@RestController("webapp")
public class Controller {
    @GetMapping
    public String code() {
        return "It works!";
    }
}
