package com.shop.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    @Size(min = 3, max = 10, message = "nhập 3->10 kí tự")
    private String firstName;
    @Size(min = 3, max = 10, message = "nhập 3->10 kí tự")
    private String lastName;
    private String username;
    @Size(min = 5, max = 10, message = "nhập 3->50 kí tự")
    private String password;
    private String repeatPassword;
}
