package ch.techstack.demo_rest_app.service;

import ch.techstack.demo_rest_app.model.User;
import ch.techstack.demo_rest_app.repositoty.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
       this.userRepository = userRepository;
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void log(Object text) {
        System.out.println(text);
    }
}
