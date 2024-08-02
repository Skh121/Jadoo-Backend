package com.example.tourtravel.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponsePojo {
    private String accessToken;
    private Integer userId;
    private List<String> roles;
    private String refreshToken;

    public AuthResponsePojo(String accessToken, String refreshToken, Integer userId, List<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.roles = roles;
    }
}
