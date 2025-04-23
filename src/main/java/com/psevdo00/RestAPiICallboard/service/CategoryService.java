package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.dto.response.CategoryDTO;
import com.psevdo00.RestAPiICallboard.entity.CategoryEntity;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<CategoryDTO> getAllCategories(){

        List<CategoryEntity> categories = repository.findAll();
        List<CategoryDTO> categoryDTO = new ArrayList<>();

        for (CategoryEntity cat : categories){

            Long idParent = null;
            CategoryEntity parent = cat.getParent();

            if (parent != null){

                idParent = parent.getId();

            }

            categoryDTO.add(new CategoryDTO(cat.getId(), cat.getName(), idParent));

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
