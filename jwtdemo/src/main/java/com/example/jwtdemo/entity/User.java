package com.example.jwtdemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq_generator"
    )
    @SequenceGenerator(
            name = "user_seq_generator",
            sequenceName = "USER_SEQ",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

}
