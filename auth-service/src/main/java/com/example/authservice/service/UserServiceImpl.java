package com.example.authservice.service;

import com.example.authservice.dto.SignupDto;
import com.example.authservice.dto.mail.InputEmailDto;
import com.example.authservice.entity.User;
import com.example.authservice.error.UserAlreadyExistsException;
import com.example.authservice.error.handle.ResponseMessage;
import com.example.authservice.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.example.authservice.common.EmailType.GENERATE_ACCOUNT_PASSWORD;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public SignupDto createUser(SignupDto signupDto) {
        try {
            if (userRepo.existsByEmail(signupDto.getEmail())) {
                throw new ResponseMessage("Email đã tồn tại. Vui lòng nhập eamil khác!");
            }
            if (signupDto.getAvatar() == null || signupDto.getAvatar().isEmpty()) {
                signupDto.setAvatar("https://firebasestorage.googleapis.com/v0/b/lover-4a315.appspot.com/o/picAll%2F1642257108103?alt=media&token=627dba36-4df2-487e-8fc5-ca0967681b8f");
            }
            if (signupDto.getRoles() == null || signupDto.getRoles().isEmpty()) {
                signupDto.setRoles(Collections.singleton(User.Role.USER.toString()));
            }
            User userCreate = new User();
            log.info("Saved user type={} with id={}", userCreate.getClass().getSimpleName(), userCreate.getId());
            userCreate.setFirstName(signupDto.getFirstName());
            userCreate.setLastName(signupDto.getLastName());
            userCreate.setAvatar(signupDto.getAvatar());
            userCreate.setEmail(signupDto.getEmail());
            userCreate.setPassword(passwordEncoder.encode(signupDto.getPassword()));
            Set<String> strRoles = signupDto.getRoles();
            Set<User.Role> roles = new HashSet<>();
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(User.Role.ADMIN);
                        break;
                    case "provider":
                        roles.add(User.Role.PROVIDER);
                        break;
                    default:
                        roles.add(User.Role.USER);
                }
            });
            userCreate.setRole(roles);
            userRepo.save(userCreate);
            log.info("Create new feature for user with ID {}", userCreate.getId());
            //gửi mail
            InputEmailDto emailDataDto = generateData(userCreate, signupDto.getPassword());
            mailService.sendMail(GENERATE_ACCOUNT_PASSWORD, emailDataDto);

        } catch (DataIntegrityViolationException | ResponseMessage e) {
            throw new UserAlreadyExistsException("User with email " + signupDto.getEmail() + " already exists");
        }
        return signupDto;
    }
    private InputEmailDto generateData(User user, String newPassword) {
        return new InputEmailDto()
                .setEmailTo(user.getEmail())
                .setFirstname(user.getFirstName())
                .setLastname(user.getLastName())
                .setNewPassword(newPassword);
    }
}
