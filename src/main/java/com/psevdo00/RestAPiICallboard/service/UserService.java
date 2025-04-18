package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.dto.request.AuthUserDTO;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserService {

    private final StudentRepository repository;

    public UserService(StudentRepository repository) {
        this.repository = repository;
    }

    public void registrationUser(UserEntity user){

        if (repository.findByEmail(user.getEmail()) != null) {

            throw new ResponseStatusException(

                    HttpStatus.NOT_FOUND,
                    "Пользователь с такой почтой уже существует!"

            );

        } else if (repository.findByUsername(user.getUsername()) != null){

            throw new ResponseStatusException(

                    HttpStatus.NOT_FOUND,
                    "Пользователь с таким именем уже существует!"

            );

        } else if (!Objects.equals(user.getPassword(), user.getRepeatPassword())){

            throw new ResponseStatusException(

                    HttpStatus.NOT_FOUND,
                    "Пароли не совпадают!"

            );

        }

        repository.save(user);

    }

    public boolean authorizationUsers(AuthUserDTO userDTO){

        if (repository.findByUsername(userDTO.getUsername()) != null){

            return userDTO.getPassword().equals(repository.findPasswordByUsername(userDTO.getUsername()));

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
