package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity getAllCategory(){

        return ResponseEntity.ok(service.getAllCategories());

    }
}
