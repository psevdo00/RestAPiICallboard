package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService servise;

    public CategoryController(CategoryService servise) {
        this.servise = servise;
    }

    @GetMapping("/getAllCategory")
    public ResponseEntity getAllCategory(){

        try{

            return ResponseEntity.ok(Map.of(
                    "message", "Список категорий получен!",
                    "listCategory", servise.getAllCategories()
            ));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(Map.of("message", "Ошибка с получением категорий! " + e));

        }

    }
}
