package az.ingress.ms2example.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {

    String email;

    String oldPassword;

    String newPassword;
}
