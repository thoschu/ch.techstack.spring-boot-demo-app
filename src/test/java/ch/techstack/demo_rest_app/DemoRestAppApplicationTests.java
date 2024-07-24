package ch.techstack.demo_rest_app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoRestAppApplicationTests {

	@Autowired
	private TodoController todoController;

	@Test
	void contextLoads() {
		assertThat(todoController).isNotNull();
	}
}
