package com.aliakseikul.storenew.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    String userNickname;
    String userPassword;
    String userFirstName;
    String userLastName;
    String userEmail;
}
