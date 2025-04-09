package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SessionController {

    private final SessionService service;

    public SessionController(SessionService service){

        this.service = service;

    }

    @GetMapping("/api/getSession")
    public ResponseEntity getSession(HttpSession session){

        return ResponseEntity.ok().body("Session. \nUsername: " + session.getAttribute("UserName") + ". \nId user: " + session.getAttribute("id") + "\nUser role: " + session.getAttribute("role"));

    }

    @GetMapping("/api/getCurrRoleUser")
    public ResponseEntity getCurrRoleUser(HttpSession session){

        return ResponseEntity.ok(Map.of(
                "role", session.getAttribute("role"),
                "id_user", session.getAttribute("id")
        ));

    }

    @DeleteMapping("/api/deleteSession")
    public ResponseEntity deleteSession(HttpSession session){

        try{

            service.deleteSession(session.getId());

            return ResponseEntity.ok(Map.of("message", "Удаление прошло успешно!", "newURL", "/index.html"));

        } catch (Exception e){

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка удаления сессии!"));

        }

    }

}
