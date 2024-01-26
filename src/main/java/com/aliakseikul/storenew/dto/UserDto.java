package com.aliakseikul.storenew.dto;

import lombok.*;

@Builder
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
}
