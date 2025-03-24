package se.lexicon.model;

import java.util.Objects;

public class Person {

    private int id;
    private String name;
    private String email;


    // Constructors
    public Person(int id, String name, String email) {
        this(name, email);
        setId(id);
    }

    public Person(String name, String email) {
        setName(name);
        setEmail(email);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException("Name is either null or empty.");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new NullPointerException("Email is either null or empty.");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
