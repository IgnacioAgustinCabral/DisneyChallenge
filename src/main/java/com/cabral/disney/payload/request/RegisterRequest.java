package com.cabral.disney.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "username required.")
    @Size(min = 8,max = 16,message = "the username must be between {min} and {max} characters long.")
    private String username;

    @NotBlank(message = "password required.")
    @Size(min = 8,max = 16,message = "the password must be between {min} and {max} characters long.")
    private String password;

    @NotBlank(message = "email required.")
    @Email
    private String email;
}
