package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.exception.UserAlreadyExistsException;
import com.psevdo00.RestAPiICallboard.exception.UserNotFoundException;
import com.psevdo00.RestAPiICallboard.service.UserService;
import com.psevdo00.RestAPiICallboard.validator.CreateUserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService service;

    public UserController (UserService servise){
        
        this.service = servise;
        
    }
    
    @PostMapping("/regUser")
    public ResponseEntity registrationUsers(@Valid @RequestBody CreateUserDTO userValid){

        try {

            UserEntity user = new UserEntity();
            user.setUsername(userValid.getUsername());
            user.setEmail(userValid.getEmail());
            user.setPhone(userValid.getPhone());
            user.setPassword(userValid.getPassword());
            user.setRepeatPassword(userValid.getRepeatPassword());

            return ResponseEntity.ok().body(Map.of(

                    "message", "Регистрация прошла успешно!",
                    "newURL", "/createAdvt.html",
                    "newUser", service.registrationUser(user)

            ));

        } catch (UserAlreadyExistsException e) {

            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Ошибка регистрации! " + e);

        }

    }

    @PostMapping("/authUser")
    public ResponseEntity authorizationUsers(@RequestBody UserEntity user){

        try {

            if (service.authorizationUsers(user)){

                return ResponseEntity.ok("Успешная авторизация!");

            }

            return ResponseEntity.badRequest().body("Неправильное имя или пароль!");

        } catch (UserAlreadyExistsException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Ошибка авторизации! " + e);

        }

    }

    @GetMapping("/search")
    public ResponseEntity findByEmail(@RequestParam String email){

        try {

            return ResponseEntity.ok(service.findByEmail(email));

        } catch (UserNotFoundException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Произошла ошибка во время поиска!");

        }

    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id){

        try {

            return ResponseEntity.ok("Удаление прошло успешно! ID: " + service.deleteUserById(id));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Произошла ошибка с удалением пользователя!" + e);

        }

    }

}
