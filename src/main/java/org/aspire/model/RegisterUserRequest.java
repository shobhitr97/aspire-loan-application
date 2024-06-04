package org.aspire.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class RegisterUserRequest {

    private String username;
    private String password;
    private String name;
    private String type;
}
