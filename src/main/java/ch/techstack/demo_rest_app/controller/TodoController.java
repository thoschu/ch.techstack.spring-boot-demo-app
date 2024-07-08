package ch.techstack.demo_rest_app.controller;

import ch.techstack.demo_rest_app.model.Todo;
import ch.techstack.demo_rest_app.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TodoController {
    protected static final String hello = "Hello ";
    private final String textHelloWorld = TodoController.hello + "World!";
    private final TodoService todoService;

    protected TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String hello() {
        return this.textHelloWorld;
    }

    // http://localhost:8080/greet?name=Tom&id=325
    @GetMapping("/greet")
    public String greeting(
            @RequestParam(value = "name") String identity,
            @RequestParam() String id
    ) {
        return id + " " + TodoController.hello + identity;
    }

    // >>> body
    //    {
    //        "id": 1,
    //        "title": "Foo bar baz",
    //        "description": "Just a test",
    //        "isDone": true
    //    }
    @PostMapping("/todo")
    public ResponseEntity<Todo> createTodo(@RequestBody() Todo todo) {
        todoService.save(todo);

        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @GetMapping("/todo")
    public ResponseEntity<Todo> fetchTodo(@RequestParam(value = "id") Optional<Long> id) {

        if(id.isPresent()) {
            Optional<Todo> todo = todoService.findById(id.get());

            if(todo.isPresent()) {
                return new ResponseEntity<>(todo.get(), HttpStatus.FOUND);
            }
        }

        return new ResponseEntity<>(new Todo(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/todo/all")
    public ResponseEntity<Iterable<Todo>> fetchAllTodos() {
        Iterable<Todo> todo = todoService.findAll();

        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @DeleteMapping("/todo")
    public ResponseEntity<Boolean> deleteTodo(@RequestParam(value = "id") Long id) {
        Boolean todo = todoService.deleteById(id);

        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PutMapping("/todo")
    public ResponseEntity<Todo> editTodo(@RequestBody() Todo editedTodo) {
        Optional<Todo> todo = todoService.findById(editedTodo.getId());

        if(todo.isPresent()) {
            return new ResponseEntity<>(editedTodo, HttpStatus.OK);
        }

        return new ResponseEntity<>(editedTodo, HttpStatus.NOT_FOUND);
    }

    // http://localhost:8080/hi?name=admin
    @GetMapping("/hi")
    public ResponseEntity<String> hi(@RequestParam() String name) {
        todoService.log(name);

        if(name.equals("admin"))
            return new ResponseEntity<>("Hi " + name, HttpStatus.OK);

        return new ResponseEntity<>("Error: " + name, HttpStatus.BAD_REQUEST);
    }
}
