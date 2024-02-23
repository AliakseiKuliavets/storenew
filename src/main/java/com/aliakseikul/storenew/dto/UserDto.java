package com.aliakseikul.storenew.dto;

import com.aliakseikul.storenew.entity.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Tag(name = "UserDto", description = "Class for return a user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Schema(minLength = 1, maxLength = 44, description = "Unique value of the user's hiring nickname")
    String userNickname;

    @Schema(minLength = 1, maxLength = 44, description = "Value of the user's first name")
    String userFirstName;

    @Schema(minLength = 1, maxLength = 44, description = "Value of the user's last name")
    String userLastName;

    @Schema(description = "Value of the user's email")
    String userEmail;

    @Schema(minLength = 6, maxLength = 44, description = "Value of the user's phoneNumber")
    String userPhoneNumber;

    @Schema(description = "Confirmation of a verified account")
    boolean verifiedAccount;

    @Schema(description = "User role")
    UserRole userRole;
}
