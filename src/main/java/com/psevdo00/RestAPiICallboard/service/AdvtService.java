package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.entity.AdvtEntity;
import com.psevdo00.RestAPiICallboard.exception.UserAlreadyExistsException;
import com.psevdo00.RestAPiICallboard.repository.AdvtRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvtService {

    private final AdvtRepository repository;

    public AdvtService(AdvtRepository repository) {
        this.repository = repository;
    }

    public AdvtEntity createAdvt(AdvtEntity advt){

        return repository.save(advt);

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

        return repository.findById(id).orElseThrow(() -> new UserAlreadyExistsException("Данного объявления нет!"));

    }

    public List<AdvtEntity> getAllAdvt(){

        return repository.findAll();

    }

}
