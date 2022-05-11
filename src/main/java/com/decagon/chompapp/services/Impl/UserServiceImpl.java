package com.decagon.chompapp.services.Impl;



import com.decagon.chompapp.dtos.PasswordDto;
import com.decagon.chompapp.exceptions.PasswordConfirmationException;
import com.decagon.chompapp.dtos.EditUserDto;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> editUserDetails(EditUserDto editUserDto) {
        String person = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameOrEmail(person, person).get();
        if (editUserDto.getFirstName() != null)
            user.setFirstName(editUserDto.getFirstName());
        if (editUserDto.getLastName() != null)
            user.setLastName(editUserDto.getLastName());
        if (editUserDto.getGender() != null)
            user.setGender(editUserDto.getGender());

        userRepository.save(user);

        String message = "User Details edit successful";

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<String> updatePassword(PasswordDto passwordDto) {

        if(!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())){
            throw new PasswordConfirmationException("new password must be the same with confirm password");
        }

        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                        .getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean matchPasswordWithOldPassword = passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword());

        if(!matchPasswordWithOldPassword){
            throw new PasswordConfirmationException("old password is not correct");
        }
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("password updated successfully");
    }
}
