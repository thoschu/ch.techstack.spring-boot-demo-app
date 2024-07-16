package ch.techstack.demo_rest_app;

import ch.techstack.demo_rest_app.model.Todo;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.JsonContentAssert;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class TodoJsonTest {
    private final Todo todo = new Todo();

    private Todo[] todos;

    @Autowired
    private JacksonTester<Todo> json;

    @Autowired
    private JacksonTester<Todo[]> jsonList;

    protected TodoJsonTest() {
        todo.setId(7L);
        todo.setTitle("Hallo test");
        todo.setDescription("Foo bar");
        todo.setIsDone(true);
        todo.setUserId(1977L);
    }

    @BeforeEach
    void setUp() {
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setTitle("Green Eggs and Ham with john");
        todo1.setDescription("Test todo by Tom S.");
        todo1.setIsDone(false);
        todo1.setUserId(1L);

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("Hallo test 2");
        todo2.setDescription("Foo bar B");
        todo2.setIsDone(false);
        todo2.setUserId(2L);

        Todo todo3 = new Todo();
        todo3.setId(3L);
        todo3.setTitle("Hallo test 3");
        todo3.setDescription("Foo bar C");
        todo3.setIsDone(false);
        todo3.setUserId(2L);

        todos = Arrays.array(todo1, todo2,todo3);
    }

    @Test
    void todoListDeserializationTest() throws IOException {
        String expected = """
                [
                     {
                       "id": 1,
                       "title": "Green Eggs and Ham with john",
                       "description": "Test todo by Tom S.",
                       "isDone": false,
                       "userId": 1
                     },
                     {
                       "id": 2,
                       "title": "Hallo test 2",
                       "description": "Foo bar B",
                       "isDone": false,
                       "userId": 2
                     },
                     {
                       "id": 3,
                       "title": "Hallo test 3",
                       "description": "Foo bar C",
                       "isDone": false,
                       "userId": 2
                     }
                ]
                """;
        Todo[] content = jsonList.parseObject(expected);

        Todo contentTodo = content[0];
        Todo listTodo = todos[0];
        assertThat(contentTodo.getId()).isEqualTo(listTodo.getId());
        assertThat(contentTodo.getTitle()).isEqualTo(listTodo.getTitle());
        assertThat(contentTodo.getDescription()).isEqualTo(listTodo.getDescription());
        assertThat(contentTodo.getIsDone()).isEqualTo(listTodo.getIsDone());
        assertThat(contentTodo.getUserId()).isEqualTo(listTodo.getUserId());

        contentTodo = content[1];
        listTodo = todos[1];
        assertThat(contentTodo.getId()).isEqualTo(listTodo.getId());
        assertThat(contentTodo.getTitle()).isEqualTo(listTodo.getTitle());
        assertThat(contentTodo.getDescription()).isEqualTo(listTodo.getDescription());
        assertThat(contentTodo.getIsDone()).isEqualTo(listTodo.getIsDone());
        assertThat(contentTodo.getUserId()).isEqualTo(listTodo.getUserId());

        contentTodo = content[2];
        listTodo = todos[2];
        assertThat(contentTodo.getId()).isEqualTo(listTodo.getId());
        assertThat(contentTodo.getTitle()).isEqualTo(listTodo.getTitle());
        assertThat(contentTodo.getDescription()).isEqualTo(listTodo.getDescription());
        assertThat(contentTodo.getIsDone()).isEqualTo(listTodo.getIsDone());
        assertThat(contentTodo.getUserId()).isEqualTo(listTodo.getUserId());
    }

    @Test
    void todoListSerializationTest() throws IOException {
        JsonContent<Todo[]> content = jsonList.write(todos);

        assertThat(content).isStrictlyEqualToJson("/ch.techstack.demo_rest_app/todo/list.json");
    }

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
