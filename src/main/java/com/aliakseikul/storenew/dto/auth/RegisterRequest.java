package com.aliakseikul.storenew.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotNull(message = "First name shouldn't be null")
    @Size(min = 1, max = 44, message = "First name should be not null and from 1 to 44 symbols")
    private String userFirstName;

    @NotNull(message = "Last name shouldn't be null")
    @Size(min = 1, max = 44, message = "Last name should be not null and from 1 to 44 symbols")
    private String userLastName;

    @NotNull(message = "Nickname shouldn't be null")
    @Size(min = 1, max = 44, message = "User nickname should be not null and from 1 to 44 symbols")
    private String userNickname;

    @Email(message = "Invalid email format")
    @NotNull(message = "Email shouldn't be null")
    private String userEmail;

    @NotNull(message = "Password shouldn't be null")
    @Size(min = 1, max = 44, message = "Password should be not null and from 1 to 44 symbols")
    private String userPassword;
}
