package com.galka.jenkinsspringexample;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;
    private String password;

}
