package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.dto.request.AdvertisementCreateRequest;
import com.psevdo00.RestAPiICallboard.dto.response.AdvertisementResponse;
import com.psevdo00.RestAPiICallboard.entity.AdvertisementEntity;
import com.psevdo00.RestAPiICallboard.entity.CategoryEntity;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository repository;
    private final CategoryService categoryService;
    private final UserService userService;

    public void create(AdvertisementCreateRequest request){

        AdvertisementEntity advt = new AdvertisementEntity();

        advt.setTitle(request.getTitle());
        advt.setInfo(request.getInfo());
        advt.setPhoto(request.getPhotoBase64());
        advt.setCost(request.getCost());

        CategoryEntity category = categoryService.findById(request.getCategoryId());
        advt.setCategory(category);

        UserEntity user = userService.findEntityWithFilters(null, request.getUser_id());
        advt.setUser(user);

        repository.save(advt);

    }

    public void delete(Long id){

        repository.deleteById(id);

    }

    public boolean updateStatus(Long id){

        AdvertisementEntity advt = repository.findById(id).get();
        advt.setCompleted(!advt.getCompleted());
        repository.save(advt);
        return advt.getCompleted();

    }

    public List<AdvertisementResponse> findWithFilters(String title, Long category){

        Specification<AdvertisementEntity> spec = Specification.where(null);

        if (title != null){

            spec = spec.and((root, query, cb) -> cb.equal(root.get("title"), title));

        }

        if (category != null){

            spec = spec.and((root, query, cb) -> cb.equal(root.get("category").get("id"), category));

        }

        List<AdvertisementEntity> list = repository.findAll(spec);
        System.out.println("Found " + list.size() + " advts");

        return convertorEntityToResponse(list);

    }

    public void edit(AdvertisementCreateRequest advtDTO, Long id){

        AdvertisementEntity advt = repository.findById(id).get();

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

    public List<AdvertisementResponse> convertorEntityToResponse(List<AdvertisementEntity> advts){

        List<AdvertisementResponse> advtsDTO = new ArrayList<>();

        for (int i = 0; i < advts.size(); i++){

            AdvertisementResponse advtDTO = new AdvertisementResponse();

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
