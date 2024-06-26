package org.aspire.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class RegisterUserDTO {

    private String username;
    private String password;
    private String name;
    private String type;
}
