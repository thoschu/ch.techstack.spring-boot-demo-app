package ch.techstack.demo_rest_app.service;

import ch.techstack.demo_rest_app.repositoty.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository todoRepository;

    public UserService(UserRepository userRepository) {
       this.todoRepository = userRepository;

       this.log(this.todoRepository);
    }

    public void log(Object text) {
        System.out.println(text);
    }
}
