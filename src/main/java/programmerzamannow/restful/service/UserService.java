package programmerzamannow.restful.service;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programmerzamannow.restful.model.RegisterUserRequest;
import programmerzamannow.restful.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    public void register(RegisterUserRequest request) {

    }

}
