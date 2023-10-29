package com.cabral.disney.mapper;

import com.cabral.disney.models.Role;
import com.cabral.disney.models.User;
import com.cabral.disney.payload.request.RegisterRequest;
import com.cabral.disney.payload.response.AuthResponse;
import com.cabral.disney.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@Component
public class UserMapper {
    private static PasswordEncoder passwordEncoder;
    private static RoleRepository roleRepository;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        UserMapper.passwordEncoder = passwordEncoder;
        UserMapper.roleRepository = roleRepository;
    }

    public static AuthResponse mapToDTO(String message) {
        return AuthResponse.builder().message(message).build();
    }

    public static User mapToEntity(RegisterRequest request, MultipartFile profilePicture) {

        Role roles = roleRepository.findByName("ROLE_USER").get();

        // Create a list of roles and add the default role to it
        User.UserBuilder builder = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .roles(Collections.singletonList(roles));

        if (profilePicture != null) {
            try {
                builder.profile_picture(Base64.getEncoder().encode(profilePicture.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return builder.build();
    }
}
