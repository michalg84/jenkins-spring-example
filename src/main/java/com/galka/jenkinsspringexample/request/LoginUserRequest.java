package com.galka.jenkinsspringexample.request;

import lombok.Value;

@Value
public class LoginUserRequest {
    String username;
    String password;

}
