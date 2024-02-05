package com.aliakseikul.storenew.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    @NotNull(message = "Nickname shouldn't be null")
    @Size(min = 1, max = 44, message = "User nickname should be not null and from 1 to 44 symbols")
    String userNickname;

    @NotNull(message = "Password shouldn't be null")
    @Size(min = 1, max = 44, message = "Password should be not null and from 1 to 44 symbols")
    String userPassword;

    @NotNull(message = "First name shouldn't be null")
    @Size(min = 1, max = 44, message = "First name should be not null and from 1 to 44 symbols")
    String userFirstName;

    @NotNull(message = "Last name shouldn't be null")
    @Size(min = 1, max = 44, message = "Last name should be not null and from 3 to 50 symbols")
    String userLastName;

    @Email(message = "Invalid email format")
    @NotNull(message = "Email shouldn't be null")
    String userEmail;
}
