package ch.techstack.demo_rest_app.service;

import ch.techstack.demo_rest_app.model.User;
import ch.techstack.demo_rest_app.repositoty.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository userRepository) {
       this.repository = userRepository;
    }

    public User save(User user) {
        return repository.save(user);
    }

    public User fetchUser(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void log(Object text) {
        System.out.println(text);
    }
}
