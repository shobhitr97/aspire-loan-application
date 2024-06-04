package org.aspire.controller;

import org.aspire.constants.LoanConstants;
import org.aspire.data.User;
import org.aspire.dto.RegisterUserDTO;
import org.aspire.exception.ValidationException;
import org.aspire.handler.IUserHandler;
import org.aspire.model.RegisterUserRequest;
import org.aspire.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthInterface {

    @Autowired
    private IUserHandler userHandler;


    @Override
    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        ValidatorUtils.validateNotNull(registerUserDTO, "request body");
        ValidatorUtils.validateNotBlank(registerUserDTO.getName(), "user full name");
        ValidatorUtils.validateNotBlank(registerUserDTO.getUsername(), "username");
        ValidatorUtils.validateNotBlank(registerUserDTO.getPassword(), "user password");
        ValidatorUtils.validateNotBlank(registerUserDTO.getType(), "user type");
        ValidatorUtils.validateInValues(registerUserDTO.getType(), LoanConstants.ALLOWED_USER_TYPES, "user type");

        User existingUserWithSameUsername = this.userHandler.getUserByUsername(registerUserDTO.getUsername());
        if (existingUserWithSameUsername != null) {
            throw new ValidationException("Username not available");
        }
        this.userHandler.createUser(RegisterUserRequest.builder()
                .name(registerUserDTO.getName())
                .password(registerUserDTO.getPassword())
                .type(registerUserDTO.getType())
                .username(registerUserDTO.getUsername())
                .build());
    }
}
