package ch.techstack.demo_rest_app.controller;

import ch.techstack.demo_rest_app.model.Todo;
import ch.techstack.demo_rest_app.model.User;
import ch.techstack.demo_rest_app.repositoty.TodoRepository;
import ch.techstack.demo_rest_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

// @RestController("/todo")
@RestController
public class TodoController {
    protected static final String hello = "Hello ";
    private final String textHelloWorld;
    private final TodoService todoService;

    protected TodoController(TodoService todoService) {
        textHelloWorld = TodoController.hello + "World!";

        this.todoService = todoService;
    }

    @GetMapping("/test/{requestedId}")
    private ResponseEntity<String> testById(@PathVariable Long requestedId) {
        return ResponseEntity.ok(textHelloWorld + " -<>- " + requestedId);
    }

    @GetMapping
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>(this.textHelloWorld, HttpStatus.OK);
    }

    // http://localhost:8080/greet?name=Tom&id=325
    @GetMapping("/greeting")
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
    @PostMapping(value = "/todo")
    public ResponseEntity<Todo> createTodo(@RequestBody() Todo todo) throws URISyntaxException {
        todoService.save(todo);

        return new ResponseEntity<>(todo, HttpStatus.CREATED);

//        return ResponseEntity
//                .created(new URI("/todo/" + todo.getId()))
//                .build();

//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .header(HttpHeaders.LOCATION, "/todo/" + todo.getId())
//                .build();
    }

    @GetMapping(value = "/todo/{requestedId}")
    public ResponseEntity<Todo> fetchTodoByPathVariable(@PathVariable Long requestedId) {
        Optional<Todo> todo = todoService.findById(requestedId);

        if(todo.isPresent()) {
            return new ResponseEntity<>(todo.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Todo(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/todo")
    public ResponseEntity<Todo> fetchTodoByRequestParam(@RequestParam(value = "id") Optional<Long> id) {

        if(id.isPresent()) {
            Optional<Todo> todo = todoService.findById(id.get());

            if(todo.isPresent()) {
                return new ResponseEntity<>(todo.get(), HttpStatus.FOUND);
            }
        }

        return new ResponseEntity<>(new Todo(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/todo/all/secret")
    public ResponseEntity<Iterable<Todo>> fetchAllTodosBySecret(@RequestHeader("api-secret") String secret) {
        // Optional<User> userBySecret = todoService.findBySecret(secret);
        var userBySecret = todoService.findBySecret(secret);

        if (userBySecret.isPresent()) {
            User user = userBySecret.get();
            Long userId = user.getId();

            Set<Todo> todos = todoService.findAllByUserId(userId);

            return new ResponseEntity<>(todos, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN + ": Invalid api secret", HttpStatus.FORBIDDEN);
    }

    @GetMapping(
            value = "/todo/all"
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<Todo[]>  fetchAllTodos() {
        Iterable<Todo> todos = todoService.findAll();
        Iterator<Todo> iterator = todos.iterator();
        List<Todo> result = new ArrayList<>();

        while (iterator.hasNext()) {
            Todo todo = iterator.next();

            result.add(todo);
        }

        Todo[] todoArr = new Todo[result.size()];
        todoArr = result.toArray(todoArr);

        return new ResponseEntity<>(todoArr, HttpStatus.OK);
    }

    // http://localhost:8080/todo/alls?page=1&size=3&sort=id,desc
    // http://localhost:8080/todo/alls?page=1&size=3&sort=id,asc
    // http://localhost:8080/todo/alls?page=1
    @GetMapping("/todo/alls")
    private ResponseEntity<List<Todo>>findAll(Pageable pageable) {
        System.out.println(pageable);

        Page<Todo> page = todoService.findAll(
                pageable
//                PageRequest.of(
//                        pageable.getPageNumber(),
//                        pageable.getPageSize(),
//                        pageable.getSortOr(
////                                Sort.by(
////                                        Sort.Direction.ASC,
////                                        "id"
////                                )
//                                pageable.getSort()
//                        )
//                )
        );

        List<Todo> todoList = page.getContent();

        return ResponseEntity.ok(todoList);
    }

    @DeleteMapping("/todo")
    public ResponseEntity<Boolean> deleteTodo(@RequestParam(value = "id") Long id) {
        Boolean todo = todoService.deleteById(id);

        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PutMapping("/todo/{requestedId}")
    private ResponseEntity<Void> putTodo(@PathVariable Long requestedId, @RequestBody Todo todoUpdate) {
        System.out.println(todoUpdate.getId());

        Optional<Todo> todo = todoService.findById(requestedId);

        if(todo.isPresent()) {
            Long id = todo.get().getId();
            String title = (todoUpdate.getTitle() != null) ? todoUpdate.getTitle() : todo.get().getTitle();
            String description = (todoUpdate.getDescription() != null) ? todoUpdate.getDescription() : todo.get().getDescription();

            Todo editedTodo = new Todo();
            editedTodo.setId(id);
            editedTodo.setTitle(title);
            editedTodo.setDescription(description);
            editedTodo.setIsDone(todo.get().getIsDone());
            editedTodo.setUserId(todo.get().getUserId());

            todoService.save(editedTodo);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

//    @PutMapping("/todo")
//    public ResponseEntity<Todo> editTodo(@RequestBody() Todo editedTodo) {
//        Optional<Todo> todo = todoService.findById(editedTodo.getId());
//
//        if(todo.isPresent()) {
//            todoService.save(editedTodo);
//
//            return new ResponseEntity<>(editedTodo, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(editedTodo, HttpStatus.NOT_FOUND);
//    }

    @PatchMapping("/todo/done")
    public ResponseEntity<Todo> toggleTodoIsDone(@RequestParam(value = "id") Long id) {
        Optional<Todo> todo = todoService.findById(id);

        if(todo.isPresent()) {
            Todo tempTodo = todo.get();
            boolean currentIsDone = tempTodo.getIsDone();
            boolean newIsDone = !currentIsDone;

            tempTodo.setIsDone(newIsDone);

            todoService.save(tempTodo);

            return new ResponseEntity<>(tempTodo, HttpStatus.OK);
        }

        return new ResponseEntity<>(new Todo(), HttpStatus.NOT_FOUND);
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
