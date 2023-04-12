package com.api.business_manager_api.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "USERS")
public class UserModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID user_id;

    @Column(nullable = false, length = 90)
    private String username;

    @Column(nullable = false, length = 80)
    private String email;

    @Column(nullable = false, length = 70)
    private String password;


    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
