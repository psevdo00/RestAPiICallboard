package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.dto.request.AdvertisementCreateRequest;
import com.psevdo00.RestAPiICallboard.service.AdvertisementService;
import com.psevdo00.RestAPiICallboard.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/advt")
public class AdvertisementController {

    private final AdvertisementService service;

    @PostMapping
    public void createAdvt(@RequestBody AdvertisementCreateRequest request){

            service.create(request);

    }

    @DeleteMapping
    public void deleteAdvt(@RequestParam Long id){

        service.delete(id);

    }

    @PatchMapping
    public ResponseEntity updateStatusAdvt(@RequestParam Long id){

        if (service.updateStatus(id)){

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

    }

    @GetMapping
    public ResponseEntity getAdvt(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long category
    ){

        return ResponseEntity.ok(service.findWithFilters(title, category));

    }

    @PutMapping
    public ResponseEntity edit(@RequestBody AdvertisementCreateRequest advtDTO, @RequestParam Long id){

        service.edit(advtDTO, id);

        return ResponseEntity.ok(Map.of(
                "message", "Изменения успешно сохранены!",
                "newURL", "/index.html"
        ));

    }

}
