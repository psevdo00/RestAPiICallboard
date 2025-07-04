package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.dto.request.UserAuthorizationRequest;
import com.psevdo00.RestAPiICallboard.dto.response.UserResponse;
import com.psevdo00.RestAPiICallboard.service.UserService;
import com.psevdo00.RestAPiICallboard.dto.request.UserCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService service;
    
    @PostMapping
    public ResponseEntity createUsers(@Valid @RequestBody UserCreateRequest request){

        UserResponse response = service.create(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity loginUsers(@RequestBody UserAuthorizationRequest request){

        UserResponse response = service.login(request);
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity getUser(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long id
    ){

        return ResponseEntity.ok(service.findResponseWithFilters(email, id));

    }

    @DeleteMapping
    public void deleteUserById(@RequestParam Long id){

        service.delete(id);

    }

}
