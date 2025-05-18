package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.dto.request.AuthUserDTO;
import com.psevdo00.RestAPiICallboard.dto.response.UserSessionDTO;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.enums.UserRoleEnum;
import com.psevdo00.RestAPiICallboard.service.UserService;
import com.psevdo00.RestAPiICallboard.dto.request.CreateUserDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService service;

    public UserController (UserService service){
        
        this.service = service;
        
    }
    
    @PostMapping("/regUser")
    public ResponseEntity registrationUsers(@Valid @RequestBody CreateUserDTO userValid, HttpSession session){

        UserSessionDTO userSessionDTO = service.registrationUser(userValid);

        session.setAttribute("UserName", userSessionDTO.getUsername());
        session.setAttribute("id", userSessionDTO.getId());
        session.setAttribute("role", userSessionDTO.getRole().name());

        return ResponseEntity.ok().body(Map.of(

                "message", "Регистрация прошла успешно!",
                "newURL", "/index.html"

        ));

    }

    @PostMapping("/authUser")
    public ResponseEntity authorizationUsers(@RequestBody AuthUserDTO userDTO, HttpSession session){

        UserSessionDTO userSessionDTO = service.authorizationUsers(userDTO);

        session.setAttribute("UserName", userSessionDTO.getUsername());
        session.setAttribute("id", userSessionDTO.getId());
        session.setAttribute("role", userSessionDTO.getRole().name());

        return ResponseEntity.ok(Map.of(

                "message", "Успешная авторизация!",
                "newURL", "index.html")

        );

    }

    @GetMapping("/search")
    public ResponseEntity findByEmail(@RequestParam String email){

        try {

            return ResponseEntity.ok(service.findByEmail(email));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Произошла ошибка во время поиска!"));

        }

    }

    @GetMapping("/searchById/{id}")
    public ResponseEntity findById(Long id){

        try {

            return ResponseEntity.ok(service.findById(id));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Произошла ошибка во время поиска!"));

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
