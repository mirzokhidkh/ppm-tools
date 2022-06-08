package uz.mk.ppmtool.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class JWTLoginSuccessResponse {
    private boolean success;
    private String token;
}
