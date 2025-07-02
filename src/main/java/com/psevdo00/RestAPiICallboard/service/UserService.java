package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.dto.request.AuthUserDTO;
import com.psevdo00.RestAPiICallboard.dto.request.CreateUserDTO;
import com.psevdo00.RestAPiICallboard.dto.response.UserDTO;
import com.psevdo00.RestAPiICallboard.dto.response.UserSessionDTO;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.enums.UserRoleEnum;
import com.psevdo00.RestAPiICallboard.exception.CustomApiException;
import com.psevdo00.RestAPiICallboard.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserDTO createUser(CreateUserDTO request){

        UserEntity user = new UserEntity();

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

            throw new CustomApiException(

                    HttpStatus.BAD_REQUEST,
                    err

            );

        }

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setFamilyName(request.getFamilyName());
        user.setMiddleName(request.getMiddleName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setNumPhone(request.getNumPhone());
        user.setAdvts(null);
        user.setRole(UserRoleEnum.valueOf(request.getRole()));

        repository.save(user);

        UserEntity newUser = repository.findByEmail(request.getEmail());
        return convertorUserEntityToUserDTO(newUser);

    }

    public UserDTO loginUser(AuthUserDTO request){

        UserEntity checkUser = repository.findByEmail(request.getEmail());

        Map<String, String> err = new HashMap<>();

        if (checkUser != null){

            if (checkUser.getPassword().equals(request.getPassword())){

                return convertorUserEntityToUserDTO(checkUser);

            } else {

                err.put("password", "Пароль неверный!");

            }

        } else {

            err.put("email", "Пользователя с данной почтой нет!");

        }

        throw new CustomApiException(

                HttpStatus.BAD_REQUEST,
                err

        );

    }

    public UserDTO findWithFilters(String email, Long id){

        Specification<UserEntity> spec = (root, query, cb) -> null;

        if (email != null){

            spec = spec.and((root, query, cb) -> cb.equal(root.get("email"), email));

        }

        if (id != null){

            spec = spec.and((root, query, cb) -> cb.equal(root.get("id"), id));

        }

        UserEntity user = repository.findOne(spec).get();

        return convertorUserEntityToUserDTO(user);

    }

    public UserEntity findUserEntityWithFilters(String email, Long id){

        Specification<UserEntity> spec = (root, query, cb) -> null;

        if (email != null){

            spec = spec.and((root, query, cb) -> cb.equal(root.get("email"), email));

        }

        if (id != null){

            spec = spec.and((root, query, cb) -> cb.equal(root.get("id"), id));

        }

        UserEntity user = repository.findOne(spec).get();

        return user;

    }

    public Long deleteUserById(Long id){

        repository.deleteById(id);
        return id;

    }

    public UserDTO convertorUserEntityToUserDTO(UserEntity user){

        UserDTO response = new UserDTO();

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

}
