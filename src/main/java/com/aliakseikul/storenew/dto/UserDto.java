package com.aliakseikul.storenew.dto;

import com.aliakseikul.storenew.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    String userNickname;
    String userFirstName;
    String userLastName;
    String userEmail;
    String userPhoneNumber;
    boolean verifiedAccount;
    UserRole userRole;
}
