package com.galka.jenkinsspringexample;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Data
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue()
    private UUID id;

    private String username;
    private String password;

}
