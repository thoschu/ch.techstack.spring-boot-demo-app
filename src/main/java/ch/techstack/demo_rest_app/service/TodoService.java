package ch.techstack.demo_rest_app.service;

import ch.techstack.demo_rest_app.model.Todo;
import ch.techstack.demo_rest_app.repositoty.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public void save(Todo todo) {
        todoRepository.save(todo);
    }

    public Iterable<Todo> findAll(){
        return todoRepository.findAll();
    }

    public void log(String message) {
        System.out.println(message);
    }
}
