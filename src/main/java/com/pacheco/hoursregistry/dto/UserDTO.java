package com.pacheco.hoursregistry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private String username;

    private String password;

    private String githubToken;

    private List<String> roles;

    public UserDTO(String username, String password, String githubToken) {
        this.username = username;
        this.password = password;
        this.githubToken = githubToken;
    }

}
