package com.orange.rouber.client;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserDto {

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Long phoneNumber;

    @NotNull
    private String address;

}
