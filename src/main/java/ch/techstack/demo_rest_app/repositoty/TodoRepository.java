package ch.techstack.demo_rest_app.repositoty;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ch.techstack.demo_rest_app.model.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long> {
    Set<Todo> findAllByUserId(Long userId);
}
