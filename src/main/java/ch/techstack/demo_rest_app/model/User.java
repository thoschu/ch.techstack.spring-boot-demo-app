package ch.techstack.demo_rest_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany
    @JoinColumn(name = "userId")
    private Set<Todo> todos;

    private String secret;

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Todo> getTodos() {
        return todos;
    }
    public void setTodos(Set<Todo> todos) {
        this.todos = todos;
    }

    public String getSecret() {
        return secret;
    }
    @JsonIgnore
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
