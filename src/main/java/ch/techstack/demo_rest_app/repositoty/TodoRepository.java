package ch.techstack.demo_rest_app.repositoty;

import ch.techstack.demo_rest_app.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long> {}
