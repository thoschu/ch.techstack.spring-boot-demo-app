package ch.techstack.demo_rest_app.repositoty;

import ch.techstack.demo_rest_app.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {}
