package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.dto.request.UserCreateRequest;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.exception.VerificationException;
import com.psevdo00.RestAPiICallboard.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService service;
    @Autowired
    private UserRepository repository;

    @Test
    void shouldThrowVerificationException_whenUserAlreadyExists(){

        UserCreateRequest request = new UserCreateRequest();
        request.setFirstName("test");
        request.setFamilyName("test");
        request.setUsername("test");
        request.setPassword("qwerty");
        request.setEmail("test@mail.ru");
        request.setNumPhone("+79789999999");

        VerificationException ex = Assertions.assertThrows(
                VerificationException.class,
                () -> service.validationNewUser(request)
        );

        Map<String, String> errors = ex.getErrors();

        Assertions.assertEquals(3, errors.size());
        Assertions.assertTrue(errors.containsKey("email"));
        Assertions.assertTrue(errors.containsKey("username"));

    }

}
