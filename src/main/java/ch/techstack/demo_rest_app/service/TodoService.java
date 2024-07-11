package ch.techstack.demo_rest_app.service;

import java.util.Optional;

import ch.techstack.demo_rest_app.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.techstack.demo_rest_app.model.Todo;
import ch.techstack.demo_rest_app.repositoty.TodoRepository;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    // private UserRepository userRepository;

    protected TodoService(UserRepository userRepository) {
        // this.userRepository = userRepository;
    }

    public void save(Todo todo) {
        todoRepository.save(todo);
    }

    public Iterable<Todo> findAll(){
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
