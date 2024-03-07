package com.galka.jenkinsspringexample.request;

import lombok.Value;

@Value
public class CreateUserRequest {
    String username;
    String password;
}
