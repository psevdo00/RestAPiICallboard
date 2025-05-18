package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.dto.request.CreateAdvtDTO;
import com.psevdo00.RestAPiICallboard.dto.response.AdvtDTO;
import com.psevdo00.RestAPiICallboard.entity.AdvtEntity;
import com.psevdo00.RestAPiICallboard.entity.CategoryEntity;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.repository.AdvtRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdvtService {

    private final AdvtRepository repository;
    private final CategoryService categoryService;
    private final UserService userService;

    public AdvtService(AdvtRepository repository, UserService userService, CategoryService categoryService) {

        this.repository = repository;
        this.categoryService = categoryService;
        this.userService = userService;

    }

    public void createAdvt(CreateAdvtDTO advtDTO, HttpSession session){

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

        repository.save(advt);

    }

    public Long deleteAdvt(Long id){

        repository.deleteById(id);
        return id;

    }

    public boolean updateStatusAdvt(Long id){

        AdvtEntity advt = repository.findById(id).get();
        advt.setCompleted(!advt.getCompleted());
        repository.save(advt);
        return advt.getCompleted();

    }

    public AdvtEntity findById(Long id){

        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(

                HttpStatus.NOT_FOUND,
                "Данного объявления нет!"

        ));

    }

    public List<AdvtDTO> searchAllAdvtByTitle(String title){

        List<AdvtEntity> advts = repository.findByTitle(title);

        return advtEntityToAdvtDTO(advts);

    }

    public List<AdvtDTO> getAllAdvt(){

        List<AdvtEntity> advts = repository.findAll();

        return advtEntityToAdvtDTO(advts);

    }

    public List<AdvtDTO> searchAllAdvtByCategory(Long id) {

        List<AdvtEntity> advts = repository.findByCategory(id);

        return advtEntityToAdvtDTO(advts);

    }

    public void editAdvt(CreateAdvtDTO advtDTO, HttpSession session, Long id){

        AdvtEntity advt = repository.findById(id).get();

        if (advt == null){

            throw new ResponseStatusException(

                    HttpStatus.NOT_FOUND,
                    "Данное объявление не найдено!"

            );

        }

        advt.setTitle(advtDTO.getTitle());
        advt.setInfo(advtDTO.getInfo());
        advt.setPhoto(advtDTO.getPhotoBase64());
        advt.setCost(advtDTO.getCost());

        CategoryEntity category = categoryService.findById(advtDTO.getCategoryId());
        advt.setCategory(category);

        repository.save(advt);

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
