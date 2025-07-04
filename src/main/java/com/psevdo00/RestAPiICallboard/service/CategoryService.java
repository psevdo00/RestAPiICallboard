package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.dto.response.CategoryResponse;
import com.psevdo00.RestAPiICallboard.entity.CategoryEntity;
import com.psevdo00.RestAPiICallboard.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<CategoryResponse> getAllCategories(){

        List<CategoryEntity> categories = repository.findAll();
        List<CategoryResponse> categoryDTO = new ArrayList<>();

        for (CategoryEntity cat : categories){

            Long idParent = null;
            CategoryEntity parent = cat.getParent();

            if (parent != null){

                idParent = parent.getId();

            }

            categoryDTO.add(new CategoryResponse(cat.getId(), cat.getName(), idParent));

        }

        return categoryDTO;

    }

    public String findNameById(Long id){

        return repository.findNameById(id);

    }

    public CategoryEntity findById(Long id){

        return repository.findById(id).orElseThrow();

    }

}
