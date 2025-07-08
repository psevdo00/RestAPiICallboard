package com.psevdo00.RestAPiICallboard.controller;

import com.psevdo00.RestAPiICallboard.dto.request.CategoryCreateRequest;
import com.psevdo00.RestAPiICallboard.dto.request.CategoryEditRequest;
import com.psevdo00.RestAPiICallboard.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity getAllCategory(){

        return ResponseEntity.ok(service.getAllCategories());

    }

    @PostMapping
    public void category_create(@Valid @RequestBody CategoryCreateRequest request){

        service.create(request);

    }

    @PatchMapping
    public void editCategory(@Valid @RequestBody CategoryEditRequest request){

        service.edit(request);

    }
}
