package com.aliakseikul.storenew.dto.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @NotNull(message = "Nickname shouldn't be null")
    @Size(min = 1, max = 44, message = "User nickname should be not null and from 1 to 44 symbols")
    private String userNickname;

    @NotNull(message = "Password shouldn't be null")
    @Size(min = 1, max = 44, message = "Password should be not null and from 1 to 44 symbols")
    private String userPassword;
}
