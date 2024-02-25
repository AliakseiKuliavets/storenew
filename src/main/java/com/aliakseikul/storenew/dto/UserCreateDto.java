package com.aliakseikul.storenew.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Tag(name = "UserCreateDto", description = "Class for creating a user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @Schema(minLength = 1, maxLength = 44, description = "Unique value of the user's hiring nickname")
    @NotNull(message = "Nickname shouldn't be null")
    @Size(min = 1, max = 44, message = "User nickname should be not null and from 1 to 44 symbols")
    String userNickname;

    @Schema(minLength = 1, maxLength = 44, description = "Value of the user's password")
    @NotNull(message = "Password shouldn't be null")
    @Size(min = 1, max = 44, message = "Password should be not null and from 1 to 44 symbols")
    String userPassword;

    @Schema(minLength = 1, maxLength = 44, description = "Value of the user's first name")
    @NotNull(message = "First name shouldn't be null")
    @Size(min = 1, max = 44, message = "First name should be not null and from 1 to 44 symbols")
    String userFirstName;

    @Schema(minLength = 1, maxLength = 44, description = "Value of the user's last name")
    @NotNull(message = "Last name shouldn't be null")
    @Size(min = 1, max = 44, message = "Last name should be not null and from 1 to 44 symbols")
    String userLastName;

    @Schema(description = "Value of the user's email")
    @Email(message = "Invalid email format")
    @NotNull(message = "Email shouldn't be null")
    String userEmail;
}
