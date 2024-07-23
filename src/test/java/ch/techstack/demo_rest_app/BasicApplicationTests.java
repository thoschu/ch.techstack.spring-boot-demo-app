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
import org.springframework.http.*;
import org.json.JSONObject;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @DirtiesContext
// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BasicApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TodoController todoController;

	@Autowired
	private UserController userController;

	@Test
	void shouldReturnTestString() {
		String expectedInt = "77";
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("sarah", "xyz123")
				.getForEntity("/test/" + expectedInt, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("Hello World! -<>- " + expectedInt);
	}

	@Test
	void shouldReturnAllTodosWhenListIsRequested1() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("tom1", "abc123")
				.getForEntity("/todo/all", String.class);

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
	void shouldReturnAllTodosWhenListIsRequested2() {
		ResponseEntity<Todo[]> response = restTemplate
				.withBasicAuth("tom1", "abc123")
				.getForEntity("/todo/all", Todo[].class);

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

		HttpHeaders headers = new HttpHeaders();
		// headers.set("X-COM-PERSIST", "true");

		HttpEntity<User> request = new HttpEntity<>(newUser, headers);

		ResponseEntity<Void> registrationResponse = restTemplate
				.withBasicAuth("sarah", "xyz123")
				.postForEntity("/registration", request, Void.class);

		assertThat(registrationResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewUser = registrationResponse.getHeaders().getLocation();

		ResponseEntity<String> getResponse = restTemplate
				.withBasicAuth("sarah", "xyz123")
				.getForEntity(locationOfNewUser, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		String pw = documentContext.read("$.password");

		assertThat(id).isNotNull();
		assertThat(password).isEqualTo(pw);
	}

	@Test
	void shouldReturnTodoNull1() throws JSONException {
		JSONObject json = new JSONObject(new JSONTokener("""
				{"id":null,"title":null,"description":null,"isDone":false,"userId":null}
			"""));

		ResponseEntity<String> response = restTemplate
				.withBasicAuth("tom1", "abc123")
				.getForEntity("/todo?id=1977", String.class);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Long id = documentContext.read("$.id");
		String title = documentContext.read("$.title");

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

		assertThat(id).isNull();
		Object jsonId = json.get("id");
		assertThat(jsonId).isEqualTo(null);

		assertThat(title).isEqualTo(null);
		Object jsonTitle = json.get("title");
		assertThat(jsonTitle).isEqualTo(null);
	}

	@Test
	void shouldReturnTodoNull2() throws JSONException {
		JSONTokener jsonTokener = new JSONTokener("""
				{"id":null,"title":null,"description":null,"isDone":false,"userId":null}
			""");
		JSONObject json = new JSONObject(jsonTokener);

		ResponseEntity<Todo> response = restTemplate
			.withBasicAuth("tom1", "abc123")
			.getForEntity("/todo?id=131977", Todo.class);

		Todo todo = response.getBody();
		HttpStatusCode responseStatusCode = response.getStatusCode();

		assertThat(todo.getId()).isNull();
		assertThat(todo.getTitle()).isNull();
		assertThat(todo.getDescription()).isNull();
		assertThat(todo.getIsDone()).isFalse();
		assertThat(todo.getUserId()).isNull();
		assertThat(responseStatusCode).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldReturnTodoAll() throws JSONException {
		ResponseEntity<Todo[]>response = restTemplate
			.withBasicAuth("tom1", "abc123")
			.getForEntity("/todo/all", Todo[].class);

		Todo[] todos = response.getBody();

		assertThat(todos).isNotNull();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		assertThat(todos.length).isNotEqualTo(0);

		Iterable<Todo[]> responseIterable =  restTemplate
		.withBasicAuth("tom1", "abc123")
		.getForObject("/todo/all", Iterable.class);

		int responseIterableSize =  Lists.newArrayList(responseIterable).size();

		Iterator<Todo[]> iterator = responseIterable.iterator();

		assertThat(responseIterable).isNotNull();
		assertThat(iterator).isNotNull();
		assertThat(responseIterableSize).isGreaterThan(0);
	}

	@Test
	void shouldReturnHello() throws JSONException {
		String response1 = restTemplate
				.withBasicAuth("tom1", "abc123")
				.getForObject("/greeting?name=Tom&id=325", String.class);

		assertThat(response1).isEqualTo("325 Hello Tom");

		ResponseEntity<String> response2 = restTemplate
				.withBasicAuth("tom1", "abc123")
				.getForEntity("/greeting?name=Tom&id=325", String.class);

 		assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
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
