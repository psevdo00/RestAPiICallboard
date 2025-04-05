package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.dto.request.CreateAdvtDTO;
import com.psevdo00.RestAPiICallboard.dto.response.AdvtDTO;
import com.psevdo00.RestAPiICallboard.entity.AdvtEntity;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.service.AdvtService;
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

    @PostMapping("/createAdvt")
    public ResponseEntity createAdvt(@RequestBody CreateAdvtDTO advtDTO, HttpSession session){

        try{

            AdvtEntity advt = new AdvtEntity();
            advt.setTitle(advtDTO.getTitle());
            advt.setInfo(advtDTO.getInfo());
            advt.setPhoto(advtDTO.getPhotoBase64());
            advt.setCost(advtDTO.getCost());
            advt.setCategory(advtDTO.getCategory());

            Long id = (Long) session.getAttribute("id");

            UserEntity user = userService.findById(id);
            advt.setUser(user);

            service.createAdvt(advt);
            return ResponseEntity.ok().body(Map.of(

                    "message", "Создание объявление прошло успешно!",
                    "newURL", "/mainPage.html"

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

    @GetMapping("/getAllAdvt")
    public ResponseEntity getAllAdvt(){

        try{

            List<AdvtEntity> advts = service.getAllAdvt();
            List<AdvtDTO> advtsDTO = new ArrayList<>();

            for (int i = 0; i < advts.size(); i++){

                AdvtDTO advtDTO = new AdvtDTO();

                advtDTO.setId(advts.get(i).getId());
                advtDTO.setTitle(advts.get(i).getTitle());
                advtDTO.setInfo(advts.get(i).getInfo());
                advtDTO.setPhotoBase64(advts.get(i).getPhoto());
                advtDTO.setCost(advts.get(i).getCost());
                advtDTO.setCompleted(advts.get(i).getCompleted());
                advtDTO.setCategory(advts.get(i).getCategory());

                advtsDTO.add(advtDTO);

            }

            return ResponseEntity.ok(Map.of("message", "Ответ получен!", "list", advtsDTO));

        } catch (Exception e){

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка на стороне сервера!"));

        }

    }

}
