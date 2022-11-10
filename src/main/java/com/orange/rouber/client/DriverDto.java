package com.orange.rouber.client;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DriverDto {

    private String name;

    @Email
    private String email;

    private Long phoneNumber;

    @Min(1)
    @Max(5)
    private Integer rating;

    public Optional<Integer> getRating() {
        return Optional.ofNullable(rating);
    }
}
