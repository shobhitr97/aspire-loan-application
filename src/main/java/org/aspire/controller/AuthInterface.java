package org.aspire.controller;

import org.aspire.dto.RegisterUserDTO;
import org.aspire.exception.ValidationException;

public interface AuthInterface {

    void registerUser(RegisterUserDTO registerUserDTO) throws ValidationException;
}
