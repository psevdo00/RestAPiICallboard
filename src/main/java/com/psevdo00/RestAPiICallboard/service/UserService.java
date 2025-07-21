package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.dto.request.UserCreateRequest;
import com.psevdo00.RestAPiICallboard.dto.response.UserResponse;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.enums.UserRoleEnum;
import com.psevdo00.RestAPiICallboard.exception.VerificationException;
import com.psevdo00.RestAPiICallboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserResponse create(UserCreateRequest request){

        UserEntity user = new UserEntity();

        validationNewUser(request);

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setFamilyName(request.getFamilyName());
        user.setMiddleName(request.getMiddleName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setNumPhone(request.getNumPhone());
        user.setAdvts(null);
        user.setRole(UserRoleEnum.USER);

        repository.save(user);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return convertorEntityToResponse(user);

    }

    public UserResponse findResponseWithFilters(String email, Long id){

        return convertorEntityToResponse(findEntityWithFilters(email, id));

    }

    public UserEntity findEntityWithFilters(String email, Long id){

        Specification<UserEntity> spec = (root, query, cb) -> null;

        if (email != null){

            spec = spec.and((root, query, cb) -> cb.equal(root.get("email"), email));

        }

        if (id != null){

            spec = spec.and((root, query, cb) -> cb.equal(root.get("id"), id));

        }

        return repository.findOne(spec).get();

    }

    public void delete(Long id){

        repository.deleteById(id);

    }

    private UserResponse convertorEntityToResponse(UserEntity user){

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setFamilyName(user.getFamilyName());
        response.setMiddleName(user.getMiddleName());
        response.setEmail(user.getEmail());
        response.setNumPhone(user.getNumPhone());
        response.setRole(user.getRole().toString());

        return response;

    }

    public void validationNewUser(UserCreateRequest request){

        UserEntity userCheck = repository.findByEmail(request.getEmail());
        if (userCheck != null){

            Map<String, String> err = new HashMap<>();
            err.put("email", "Данная почта уже используется");

            if (userCheck.getUsername().equals(request.getUsername())){

                err.put("username", "Данный логин уже используется");

            }

            if (userCheck.getNumPhone().equals(request.getNumPhone()) && !userCheck.getNumPhone().isEmpty()){

                err.put("numPhone", "Данный номер телефона уже используется");

            }

            throw new VerificationException(

                    HttpStatus.BAD_REQUEST,
                    err

            );

        }

    }

}
