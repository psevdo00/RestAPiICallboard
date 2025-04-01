package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.dto.request.CreateAdvtDTO;
import com.psevdo00.RestAPiICallboard.dto.response.AdvtDTO;
import com.psevdo00.RestAPiICallboard.entity.AdvtEntity;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.service.AdvtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/advt")
public class AdvtController {

    private final AdvtService service;

    public AdvtController(AdvtService service) {
        this.service = service;
    }

    @PostMapping("/createAdvt")
    public ResponseEntity createAdvt(@RequestBody CreateAdvtDTO advtDTO){

        try{

            AdvtEntity advt = new AdvtEntity();
            advt.setTitle(advtDTO.getTitle());
            advt.setInfo(advtDTO.getInfo());
            advt.setPhoto(advtDTO.getPhotoBase64());
            advt.setCost(advtDTO.getCost());
            advt.setCategory(advtDTO.getCategory());

            UserEntity user = new UserEntity();
            user.setId(1L);
            advt.setUser(user);

            service.createAdvt(advt);
            return ResponseEntity.ok().body(Map.of(

                    "message", "Создание объявление прошло успешно! Переданное фото: " + advt.getPhoto(),
                    "newURL", ""

            ));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка в создании нового объявления! "));

        }

    }

    @DeleteMapping("/deleteAdvt/{id}")
    public ResponseEntity deleteAdvt(@PathVariable Long id){

        try {

            return ResponseEntity.ok("Объявление успешно удалено! ID: " + service.deleteAdvt(id));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Ошибка с удалением объявлением");

        }

    }

    @PutMapping("/updateStatusAdvt/")
    public ResponseEntity updateStatusAdvt(@RequestParam Long id){

        try {

            if (service.updateStatusAdvt(id)){

                return ResponseEntity.ok("Объявление успешно закрыто!");

            } else {

                return ResponseEntity.ok("Объявление успешно открыто!");

            }

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Ошибка обновления статуса объявления! " + e);

        }

    }

    @GetMapping("/search")
    public ResponseEntity findById(@RequestParam Long id){

        try {

            AdvtEntity advt = service.findById(id);
            AdvtDTO advtDTO = new AdvtDTO();

            advtDTO.setId(advt.getId());
            advtDTO.setTitle(advt.getTitle());
            advtDTO.setInfo(advt.getInfo());
            advtDTO.setPhotoBase64(advt.getPhoto());
            advtDTO.setCost(advt.getCost());
            advtDTO.setCompleted(advt.getCompleted());
            advtDTO.setCategory(advt.getCategory());

            return ResponseEntity.ok(Map.of(
                    "entity", advtDTO,
                    "message", "Объявление найдено!"));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка поиска!"));

        }

    }

}
