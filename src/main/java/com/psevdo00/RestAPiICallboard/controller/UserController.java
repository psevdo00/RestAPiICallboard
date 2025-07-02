package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.dto.request.AuthUserDTO;
import com.psevdo00.RestAPiICallboard.dto.response.UserDTO;
import com.psevdo00.RestAPiICallboard.dto.response.UserSessionDTO;
import com.psevdo00.RestAPiICallboard.service.UserService;
import com.psevdo00.RestAPiICallboard.dto.request.CreateUserDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService service;

    public UserController (UserService service){
        
        this.service = service;
        
    }
    
    @PostMapping
    public ResponseEntity createUsers(@Valid @RequestBody CreateUserDTO request){

        UserDTO response = service.createUser(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity loginUsers(@RequestBody AuthUserDTO request){

        UserDTO response = service.loginUser(request);
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity getUser(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long id
    ){

        return ResponseEntity.ok(service.findWithFilters(email, id));

    }

    @DeleteMapping
    public ResponseEntity deleteUserById(@RequestParam Long id){

        try {

            return ResponseEntity.ok("Удаление прошло успешно! ID: " + service.deleteUserById(id));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Произошла ошибка с удалением пользователя!" + e);

        }

    }

}
