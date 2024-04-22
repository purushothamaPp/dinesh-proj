package com.creative.hfs.hfsbackend.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User {
    private Integer userId;
    private String password;
    private  String status;
    private String is_default_password;

    public User(Integer userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
