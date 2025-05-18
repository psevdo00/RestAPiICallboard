package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.dto.request.CreateAdvtDTO;
import com.psevdo00.RestAPiICallboard.dto.response.AdvtDTO;
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

    public AdvtController(AdvtService service) { this.service = service; }

    @PostMapping("/createAdvt")
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

    @DeleteMapping("/deleteAdvt/{id}")
    public ResponseEntity deleteAdvt(@PathVariable Long id){

        try {

            service.deleteAdvt(id);
            return ResponseEntity.ok(Map.of("message", "Объявление успешно удалено!"));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка с удалением объявлением"));

        }

    }

    @PutMapping("/updateStatusAdvt/{id}")
    public ResponseEntity updateStatusAdvt(@PathVariable Long id){

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

    @GetMapping("/searchAllAdvtByTitle/{title}")
    public ResponseEntity searchAllAdvtByTitle(@PathVariable String title){

        try {

            List<AdvtDTO> advtsDTO = service.searchAllAdvtByTitle(title);

            return ResponseEntity.ok(Map.of(

                    "message", "Поиск прошел успешно!",
                    "list", advtsDTO

            ));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка с поиском! " + e));

        }

    }

    @GetMapping("/getAllAdvt")
    public ResponseEntity getAllAdvt(){

        try{

            List<AdvtDTO> advtsDTO = service.getAllAdvt();

            return ResponseEntity.ok(Map.of(

                    "message", "Ответ получен!",
                    "list", advtsDTO

            ));

        } catch (Exception e){

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка на стороне сервера!"));

        }

    }

    @GetMapping("/searchAllAdvtByCategory/{id}")
    public ResponseEntity searchAllAdvtByCategory(@PathVariable Long id){

        try{

            List<AdvtDTO> advtsDTO = service.searchAllAdvtByCategory(id);

            return ResponseEntity.ok(Map.of(

                    "message", "Ответ получен!",
                    "list", advtsDTO

            ));

        } catch (Exception e){

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка на стороне сервера!"));

        }

    }

    @PutMapping("/editAdvt/{id}")
    public ResponseEntity editAdvt(@RequestBody CreateAdvtDTO advtDTO, HttpSession session, @PathVariable Long id){

        try {

            service.editAdvt(advtDTO, session, id);

            return ResponseEntity.ok(Map.of(
                    "message", "Изменения успешно сохранены!",
                    "newURL", "/index.html"
            ));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка в сохранении объявлении"));

        }

    }

}
