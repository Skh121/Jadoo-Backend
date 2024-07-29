package com.example.tourtravel.Pojo;
import com.example.tourtravel.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor



@Getter
@Setter
public class CustomerPojo {
    private Integer id;
    private String username;
    private String password;
    private String confirm_password;
    private List<Role> roles = new ArrayList<>();
}

