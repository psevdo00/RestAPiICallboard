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
    private final UserService userService;
    private final CategoryService categoryService;

    public AdvtController(AdvtService service, UserService userService, CategoryService categoryService) {
        this.service = service;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping("/createAdvt")
    public ResponseEntity createAdvt(@RequestBody CreateAdvtDTO advtDTO, HttpSession session){

        try{

            AdvtEntity advt = new AdvtEntity();
            advt.setTitle(advtDTO.getTitle());
            advt.setInfo(advtDTO.getInfo());
            advt.setPhoto(advtDTO.getPhotoBase64());
            advt.setCost(advtDTO.getCost());

            CategoryEntity category = categoryService.findById(advtDTO.getCategoryId());
            advt.setCategory(category);

            Long id = (Long) session.getAttribute("id");

            UserEntity user = userService.findById(id);
            advt.setUser(user);

            service.createAdvt(advt);
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

            return ResponseEntity.badRequest().body("Ошибка обновления статуса объявления! " + e);

        }

    }

    @GetMapping("/search/{id}")
    public ResponseEntity findById(@PathVariable Long id){

        try {

            AdvtEntity advt = service.findById(id);

            UserEntity user = userService.findById(advt.getUser().getId());
            
            List<AdvtEntity> advts = new ArrayList<>();
            advts.add(advt);
            
            List<AdvtDTO> advtsDTO = advtEntityToAdvtDTO(advts);
            AdvtDTO advtDTO = advtsDTO.get(0);

            return ResponseEntity.ok(Map.of(
                    "entity", advtDTO,
                    "user", user,
                    "message", "Объявление найдено!"));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка поиска!"));

        }

    }

    @GetMapping("/searchAllAdvtByTitle/{title}")
    public ResponseEntity searchAllAdvtByTitle(@PathVariable String title){

        try {

            List<AdvtEntity> advts = service.findAllByTitle(title);
            List<AdvtDTO> advtsDTO = advtEntityToAdvtDTO(advts);

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

            List<AdvtEntity> advts = service.getAllAdvt();
            List<AdvtDTO> advtsDTO = advtEntityToAdvtDTO(advts);

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

            List<AdvtEntity> advts = service.searchAllAdvtByCategory(id);
            List<AdvtDTO> advtsDTO = advtEntityToAdvtDTO(advts);

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

            AdvtEntity advt = service.findById(id);

            if (advt == null){

                return ResponseEntity.badRequest().body(Map.of("message", ""));

            }

            advt.setTitle(advtDTO.getTitle());
            advt.setInfo(advtDTO.getInfo());
            advt.setPhoto(advtDTO.getPhotoBase64());
            advt.setCost(advtDTO.getCost());

            CategoryEntity category = categoryService.findById(advtDTO.getCategoryId());
            advt.setCategory(category);

            service.editAdvt(advt);

            return ResponseEntity.ok(Map.of(
                    "message", "Изменения успешно сохранены!",
                    "newURL", "/index.html"
            ));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка в сохранении объявлении"));

        }

    }

    public List<AdvtDTO> advtEntityToAdvtDTO(List<AdvtEntity> advts){

        List<AdvtDTO> advtsDTO = new ArrayList<>();

        for (int i = 0; i < advts.size(); i++){

            AdvtDTO advtDTO = new AdvtDTO();

            advtDTO.setId(advts.get(i).getId());
            advtDTO.setTitle(advts.get(i).getTitle());
            advtDTO.setInfo(advts.get(i).getInfo());
            advtDTO.setPhotoBase64(advts.get(i).getPhoto());
            advtDTO.setCost(advts.get(i).getCost());
            advtDTO.setCompleted(advts.get(i).getCompleted());
            advtDTO.setCategory(advts.get(i).getCategory().getName());
            advtDTO.setUser_id(advts.get(i).getIdUser());

            advtsDTO.add(advtDTO);

        }

        return advtsDTO;

    }

}
