package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.dto.request.CreateAdvtDTO;
import com.psevdo00.RestAPiICallboard.dto.response.AdvtDTO;
import com.psevdo00.RestAPiICallboard.dto.response.UserDTO;
import com.psevdo00.RestAPiICallboard.entity.AdvtEntity;
import com.psevdo00.RestAPiICallboard.entity.CategoryEntity;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.service.AdvtService;
import com.psevdo00.RestAPiICallboard.service.CategoryService;
import com.psevdo00.RestAPiICallboard.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/advt")
public class AdvtController {

    private final AdvtService service;
    private final UserService userService;

    public AdvtController(AdvtService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity createAdvt(@RequestBody CreateAdvtDTO advtDTO, HttpSession session){

        try{

            service.createAdvt(advtDTO, session);

            return ResponseEntity.ok().body(Map.of(

                    "message", "Создание объявление прошло успешно!",
                    "newURL", "/index.html"

            ));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка в создании нового объявления! "));

        }

    }

    @DeleteMapping
    public ResponseEntity deleteAdvt(@RequestParam Long id){

        try {

            service.deleteAdvt(id);
            return ResponseEntity.ok(Map.of("message", "Объявление успешно удалено!"));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка с удалением объявлением"));

        }

    }

    @PutMapping
    public ResponseEntity updateStatusAdvt(@RequestParam Long id){

        try {

            if (service.updateStatusAdvt(id)){

                return ResponseEntity.ok(Map.of(
                        "message", "Объявление успешно закрыто!",
                        "status", "not visible"
                ));

            } else {

                return ResponseEntity.ok(Map.of(
                        "message", "Объявление успешно открыто!",
                        "status", "visible"
                ));

            }

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка обновления статуса объявления! " + e));

        }

    }

    @GetMapping
    public ResponseEntity getAdvt(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long id_category
    ){

        return ResponseEntity.ok(service.findWithFilters(title, id_category));

    }

    @PutMapping("/editAdvt/{id}")
    public ResponseEntity editAdvt(@RequestBody CreateAdvtDTO advtDTO, HttpSession session, @PathVariable Long id){

        try {

            service.editAdvt(advtDTO, id);

            return ResponseEntity.ok(Map.of(
                    "message", "Изменения успешно сохранены!",
                    "newURL", "/index.html"
            ));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка в сохранении объявлении"));

        }

    }

}
