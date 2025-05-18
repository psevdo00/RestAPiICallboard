package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.dto.request.AuthUserDTO;
import com.psevdo00.RestAPiICallboard.dto.request.CreateUserDTO;
import com.psevdo00.RestAPiICallboard.dto.response.UserSessionDTO;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.enums.UserRoleEnum;
import com.psevdo00.RestAPiICallboard.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserSessionDTO registrationUser(CreateUserDTO userValid){

        UserEntity user = new UserEntity();

        user.setUsername(userValid.getUsername());
        user.setEmail(userValid.getEmail());
        user.setPhone(userValid.getPhone());
        user.setPassword(userValid.getPassword());
        user.setRepeatPassword(userValid.getRepeatPassword());
        user.setRole(UserRoleEnum.USER);

        if (repository.findByEmail(user.getEmail()) != null) {

            throw new ResponseStatusException(

                    HttpStatus.BAD_REQUEST,
                    "Пользователь с такой почтой уже существует!"

            );

        } else if (repository.findByUsername(user.getUsername()) != null){

            throw new ResponseStatusException(

                    HttpStatus.BAD_REQUEST,
                    "Пользователь с таким именем уже существует!"

            );

        } else if (!Objects.equals(user.getPassword(), user.getRepeatPassword())){

            throw new ResponseStatusException(

                    HttpStatus.BAD_REQUEST,
                    "Пароли не совпадают!"

            );

        }

        repository.save(user);

        return repository.valuesForUserSessionDTO(user.getUsername());

    }

    public UserSessionDTO authorizationUsers(AuthUserDTO userDTO){

        UserEntity user = repository.findByUsername(userDTO.getUsername());

        if (user != null){

            if (userDTO.getPassword().equals(user.getPassword())){

                return repository.valuesForUserSessionDTO(user.getUsername());

            } else {

                throw new ResponseStatusException(

                        HttpStatus.BAD_REQUEST,
                        "Имя пользователя или пароль не верный!"

                );

            }

        } else {

            throw new ResponseStatusException(

                    HttpStatus.NOT_FOUND,
                    "Данный пользователь не найден!"

            );

        }

    }

    public UserEntity findByEmail(String email){

        UserEntity user = repository.findByEmail(email);

        if (user == null){

            throw new ResponseStatusException(

                    HttpStatus.NOT_FOUND,
                    "Пользователя с данной почтой нет!"

            );

        }

            return user;

    }

    public UserEntity findById(Long id){

        return repository.findById(id).orElseThrow();

    }

    public UserEntity findByUsername(String username){

        return repository.findByUsername(username);

    }

    public Long deleteUserById(Long id){

        repository.deleteById(id);
        return id;

    }

}
