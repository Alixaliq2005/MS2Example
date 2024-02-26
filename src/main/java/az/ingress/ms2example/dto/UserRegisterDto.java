package az.ingress.ms2example.dto;

import lombok.Data;

@Data
public class UserRegisterDto {


    String userName;

    String password;

    String repeatPassword;

    String email;
}
