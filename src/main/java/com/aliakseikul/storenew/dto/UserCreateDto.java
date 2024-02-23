package com.aliakseikul.storenew.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Class for creating a user")
public class UserCreateDto {

    @NotNull(message = "Nickname shouldn't be null")
    @Size(min = 1, max = 44, message = "User nickname should be not null and from 1 to 44 symbols")
    @Schema(minLength = 1, maxLength = 44, description = "Unique value of the user's hiring nickname")
    String userNickname;

    @NotNull(message = "Password shouldn't be null")
    @Size(min = 1, max = 44, message = "Password should be not null and from 1 to 44 symbols")
    @Schema(minLength = 1, maxLength = 44, description = "Value of the user's password")
    String userPassword;

    @NotNull(message = "First name shouldn't be null")
    @Size(min = 1, max = 44, message = "First name should be not null and from 1 to 44 symbols")
    @Schema(minLength = 1, maxLength = 44, description = "Value of the user's first name")
    String userFirstName;

    @NotNull(message = "Last name shouldn't be null")
    @Size(min = 1, max = 44, message = "Last name should be not null and from 1 to 44 symbols")
    @Schema(minLength = 1, maxLength = 44, description = "Value of the user's last name")
    String userLastName;

    @Email(message = "Invalid email format")
    @NotNull(message = "Email shouldn't be null")
    @Schema(description = "Value of the user's email")
    String userEmail;
}
