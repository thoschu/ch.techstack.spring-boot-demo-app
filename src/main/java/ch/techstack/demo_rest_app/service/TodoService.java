package ch.techstack.demo_rest_app.service;

import java.util.Optional;
import java.util.Set;

import ch.techstack.demo_rest_app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ch.techstack.demo_rest_app.model.Todo;
import ch.techstack.demo_rest_app.repositoty.TodoRepository;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    private final UserService userService;

    protected TodoService(UserService userService) {
        this.userService = userService;
    }

    public Set<Todo> findAllByUserId(Long userId) {
        return todoRepository.findAllByUserId(userId);
    }

    public Optional<User> findBySecret(String secret) {
        return userService.findBySecret(secret);
    }

    public void save(Todo todo) {
        todoRepository.save(todo);
    }

    public Page<Todo> findAll(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    public Iterable<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        if(todoRepository.existsById(id)) {
            todoRepository.deleteById(id);

            return true;
        }

        return false;
    }

    public void log(Object message) {
        System.out.println(message);
    }
}
