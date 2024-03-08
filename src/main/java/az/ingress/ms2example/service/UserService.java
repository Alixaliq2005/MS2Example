package az.ingress.ms2example.service;

import az.ingress.ms2example.dto.LoginDto;
import az.ingress.ms2example.dto.ResetPasswordDto;
import az.ingress.ms2example.dto.UserRegisterDto;
import az.ingress.ms2example.entity.ResetPassword;
import az.ingress.ms2example.entity.User;
import az.ingress.ms2example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {


    private final ModelMapper modelMapper;

    private final UserRepository userRepository;


    public void register(UserRegisterDto userRegisterDto) {
        User user = modelMapper.map (userRegisterDto, User.class);

        if (!user.getPassword ().equals (user.getRepeatPassword ())) {
            throw new RuntimeException ("Please repeat password successfully");
        }
        if (userRepository.existsByEmail (userRegisterDto.getEmail ())) {
            throw new RuntimeException ("Email already exist");
        }

        userRepository.save (user);


    }

    public User findUser(Long id) {
       return userRepository.findById(Math.toIntExact (id)).orElseThrow (()->new RuntimeException ());
    }

    public boolean login(LoginDto loginDto) {
        User user=userRepository.findByUserName(loginDto.getUserName ());
        if (user==null){
            throw new RuntimeException ("not found this username"+loginDto.getUserName ());
        }
        return user.getPassword ().equals (loginDto.getPassword ());

    }

    public void resetPassword(ResetPassword resetPassword) {
        User user = userRepository.findByEmail(resetPassword.getEmail());
        if (user == null || !user.getEmail().equals(resetPassword.getEmail())) {
            throw new RuntimeException("invalid email");
        }
        user.setPassword(resetPassword.getNewPassword());
        userRepository.save(user);
    }
}
