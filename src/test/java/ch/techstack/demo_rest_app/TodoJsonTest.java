package ch.techstack.demo_rest_app;

import ch.techstack.demo_rest_app.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.JsonContentAssert;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class TodoJsonTest {
    private final Todo todo = new Todo();

    protected TodoJsonTest() {
        todo.setId(7L);
        todo.setTitle("Hallo test");
        todo.setDescription("Foo bar");
        todo.setIsDone(true);
        todo.setUserId(1977L);
    }

    @Autowired
    private JacksonTester<Todo> json;

    @Test
    void myTest() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    void todoSerializationTest1() throws IOException {
        assertThat(json.write(todo)).isStrictlyEqualToJson("/ch.techstack.demo_rest_app/expected.json");
    }

    @Test
    void todoSerializationTest2() throws IOException {
        JsonContent<Todo> todoJsonContent = json.write(todo);

        JsonContentAssert todoJsonContentAssert = assertThat(todoJsonContent);

        todoJsonContentAssert.hasJsonPathNumberValue("@.id");
        todoJsonContentAssert.hasJsonPathStringValue("@.title");
        todoJsonContentAssert.hasJsonPathStringValue("@.description");
        todoJsonContentAssert.hasJsonPathBooleanValue("@.isDone");
        todoJsonContentAssert.hasJsonPathNumberValue("@.userId");

        todoJsonContentAssert.extractingJsonPathNumberValue("@.id").isEqualTo(7);
        todoJsonContentAssert.extractingJsonPathNumberValue("@.id").isNotEqualTo(13);

        todoJsonContentAssert.extractingJsonPathStringValue("@.title").isEqualTo("Hallo test");
        todoJsonContentAssert.extractingJsonPathStringValue("@.description").isEqualTo("Foo bar");
        todoJsonContentAssert.extractingJsonPathBooleanValue("@.isDone").isEqualTo(true);
        todoJsonContentAssert.extractingJsonPathNumberValue("@.userId").isEqualTo(1977);
    }

    @Test
    void todoDeserializationTest1() throws IOException {
        Todo tempTodo = new Todo();
        tempTodo.setId(7L);
        tempTodo.setTitle("Hallo test");
        tempTodo.setDescription("Foo bar");
        tempTodo.setIsDone(true);

        String expected = """
            {
              "id": 77,
              "title": "Hello world",
              "description": "Foo bar baz",
              "isDone": false,
              "userId": 2024
            }
           """;
        assertThat(json.parseObject(expected).getId()).isEqualTo(77);
        assertThat(json.parseObject(expected).getTitle()).isEqualTo("Hello world");
        assertThat(json.parseObject(expected).getDescription()).isEqualTo("Foo bar baz");
        assertThat(json.parseObject(expected).getIsDone()).isEqualTo(false);
        assertThat(json.parseObject(expected).getUserId()).isEqualTo(2024);
    }
}
