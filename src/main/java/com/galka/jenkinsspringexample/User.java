package com.galka.jenkinsspringexample;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;


@Data
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String username;
    private String password;

}
