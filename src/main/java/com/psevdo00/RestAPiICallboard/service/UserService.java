package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.dto.request.AuthUserDTO;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.exception.UserAlreadyExistsException;
import com.psevdo00.RestAPiICallboard.exception.UserNotFoundException;
import com.psevdo00.RestAPiICallboard.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
public class UserService {

    private final StudentRepository repository;

    public UserService(StudentRepository repository) {
        this.repository = repository;
    }

    public void registrationUser(UserEntity user){

        if (repository.findByEmail(user.getEmail()) != null) {

            throw new UserAlreadyExistsException("Пользователь с такой почтой уже существует!");

        } else if (repository.findByUsername(user.getUsername()) != null){

            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует!");

        } else if (!Objects.equals(user.getPassword(), user.getRepeatPassword())){

            throw new UserAlreadyExistsException("Пароли не совпадают!");

        }

        repository.save(user);

    }

    public boolean authorizationUsers(AuthUserDTO userDTO){

        if (repository.findByUsername(userDTO.getUsername()) != null){

            return userDTO.getPassword().equals(repository.findPasswordByUsername(userDTO.getUsername()));

        } else {

            throw new UserAlreadyExistsException("Данный пользователь не найден!");

        }

    }

    public UserEntity findByEmail(String email){

        if (repository.findByEmail(email) == null){

            throw new UserNotFoundException("Пользователя с такой почтой нет.");

        } else {

            return repository.findByEmail(email);

        }

    }

    public UserEntity findById(Long id){

        return repository.findById(id).orElseThrow();

    }

    public Long findIdByUsername(String username){

        UserEntity user = repository.findByUsername(username);
        return user.getId();

    }

    public Long deleteUserById(Long id){

        repository.deleteById(id);
        return id;

    }

}
