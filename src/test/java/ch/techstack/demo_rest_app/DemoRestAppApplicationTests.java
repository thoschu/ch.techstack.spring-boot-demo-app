package ch.techstack.demo_rest_app;

import ch.techstack.demo_rest_app.rest.FunRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoRestAppApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private FunRestController controller;

	@Value("${developer.name}")
	private String name;

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${team.name}")
	private String teamName;

	@Test
	void shouldReturnTestString() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("tom1", "abc123")
				.getForEntity("/name", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(applicationName
				.toUpperCase()
				.concat(" by ")
				.concat(this.name)
				.concat(" and: ")
				.concat(this.teamName)
		);
	}

	@Test
	void shouldReturnHelloString() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("tom1", "abc123")
				.getForEntity("/", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("Hello World!");
	}

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
