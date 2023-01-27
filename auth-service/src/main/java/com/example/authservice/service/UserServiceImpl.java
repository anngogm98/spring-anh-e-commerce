package com.example.authservice.service;

import com.example.authservice.dto.SignupDto;
import com.example.authservice.entity.User;
import com.example.authservice.error.UserAlreadyExistsException;
import com.example.authservice.error.handle.ResponseMessage;
import com.example.authservice.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public SignupDto createUser(SignupDto signupDto) {
        try {
            if (userRepo.existsByEmail(signupDto.getEmail())) {
                throw new ResponseMessage("Email đã tồn tại. Vui lòng nhập eamil khác!");
            }
            if (signupDto.getAvatar() == null || signupDto.getAvatar().isEmpty()) {
                signupDto.setAvatar("https://console.firebase.google.com/project/lover-4a315/storage/lover-4a315.appspot.com/files/~2FpicAll");
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

        } catch (DataIntegrityViolationException | ResponseMessage e) {
            throw new UserAlreadyExistsException("User with email " + signupDto.getEmail() + " already exists");
        }
        return signupDto;
    }
}
