package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.entity.AdvtEntity;
import com.psevdo00.RestAPiICallboard.repository.AdvtRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdvtService {

    private final AdvtRepository repository;

    public AdvtService(AdvtRepository repository) {
        this.repository = repository;
    }

    public void createAdvt(AdvtEntity advt){

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

    public List<AdvtEntity> findAllByTitle(String title){

        return repository.findByTitle(title);

    }

    public List<AdvtEntity> getAllAdvt(){

        return repository.findAll();

    }

    public List<AdvtEntity> searchAllAdvtByCategory(Long id) {

        return repository.findByCategory(id);

    }

    public void editAdvt(AdvtEntity advt){

        repository.save(advt);

    }
}
