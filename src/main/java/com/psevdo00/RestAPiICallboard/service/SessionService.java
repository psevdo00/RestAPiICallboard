package com.psevdo00.RestAPiICallboard.service;

import jakarta.websocket.Session;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final FindByIndexNameSessionRepository<? extends Session> repository;

    public SessionService(FindByIndexNameSessionRepository repository) {
        this.repository = repository;
    }

    public void deleteSession(String id){

        repository.deleteById(id);

    }
}
