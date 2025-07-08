package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.dto.request.CategoryCreateRequest;
import com.psevdo00.RestAPiICallboard.dto.request.CategoryEditRequest;
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

    public CategoryEntity findById(Long id){

        return repository.findById(id).orElseThrow();

    }

    public void edit(CategoryEditRequest request){

        CategoryEntity category = repository.findById(request.getId()).get();

        category.setName(request.getName());

        Long id_parent = request.getId_parent();

        if (id_parent != 0){

            category.setParent(repository.findById(id_parent).get());

        } else {

            category.setParent(null);

        }

        repository.save(category);

    }

    public void create(CategoryCreateRequest response){

        CategoryEntity category = new CategoryEntity();

        category.setName(response.getName());

        Long id_parent = response.getId_parent();

        if (id_parent != 0){

            category.setParent(repository.findById(id_parent).get());

        } else {

            category.setParent(null);

        }

        repository.save(category);

    }

}
