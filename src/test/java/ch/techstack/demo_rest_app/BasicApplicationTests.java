package ch.techstack.demo_rest_app;

import ch.techstack.demo_rest_app.controller.TodoController;
import ch.techstack.demo_rest_app.model.Todo;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.util.Lists;
import org.json.JSONException;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.json.JSONObject;

import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BasicApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TodoController controller;

	@Test
	void shouldReturnTodoNull() throws JSONException {
		JSONObject json = new JSONObject(new JSONTokener("""
				{"id":null,"title":null,"description":null,"isDone":false,"userId":null}
			"""));

		ResponseEntity<String> response = restTemplate.getForEntity("/todo?id=1977", String.class);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		String title = documentContext.read("$.title");

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

		// assertThat(id).isNotNull();
		assertThat(id).isNull();

		// assertThat(title).isEqualTo("Test");
		assertThat(title).isEqualTo(null);
	}

	@Test
	void shouldReturnTodoAll() throws JSONException {
		ResponseEntity<Iterable> response = restTemplate.getForEntity("/todo/all", Iterable.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		Iterable<Todo> todos = response.getBody();

		assertThat(todos).isNotNull();

		assertThat(Lists.newArrayList(todos.iterator()).size()).isNotEqualTo(0);
	}

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
}
