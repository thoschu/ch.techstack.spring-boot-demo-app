package ch.techstack.demo_rest_app;

import ch.techstack.demo_rest_app.controller.TodoController;
import ch.techstack.demo_rest_app.controller.UserController;
import ch.techstack.demo_rest_app.model.Todo;
import ch.techstack.demo_rest_app.model.User;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
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
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
class BasicApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TodoController todoController;

	@Autowired
	private UserController userController;

	@Test
	void shouldReturnAllCashCardsWhenListIsRequested1() {
		ResponseEntity<String> response = restTemplate.getForEntity("/todo/all", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int todoCount = documentContext.read("$.length()");
		assertThat(todoCount).isNotEqualTo(0);
		assertThat(todoCount).isGreaterThan(2);

		JSONArray ids = documentContext.read("$..id");
//		assertThat(ids).containsExactlyInAnyOrder(1, 2, 3);

		JSONArray amounts = documentContext.read("$..title");
//		assertThat(amounts).containsExactlyInAnyOrder("aaa", "bbb", "ccc", "xxx");
	}

	@Test
	void shouldReturnAllCashCardsWhenListIsRequested2() {
		ResponseEntity<Todo[]> response = restTemplate.getForEntity("/todo/all", Todo[].class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().length).isNotEqualTo(0);
	}

	@Test
	void shouldCreateANewUser() {
		String password = "mamapapa";
		User newUser = new User();
		// newUser.setId(10000000L);
		newUser.setUsername("John Smith");
		newUser.setEmail("john.smith@" + this.getRandowmString() + ".com");
		newUser.setPassword(password);
		newUser.setSecret("xxx-aaa-iii");

		ResponseEntity<Void> registrationResponse = restTemplate.postForEntity("/registration", newUser, Void.class);
		assertThat(registrationResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewUser = registrationResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewUser, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		String pw = documentContext.read("$.password");

		assertThat(id).isNotNull();
		assertThat(password).isEqualTo(pw);
	}

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
		assertThat(todoController).isNotNull();
		assertThat(userController).isNotNull();
	}

	private String getRandowmString() {
		return UUID.randomUUID()
				.toString()
				.replaceAll("-", "")
				.substring(0, 7);
	}
}
