package se.lexicon.model;


public class MyCalendar {
    private int id;
    private int person_id;
    private String name;
    private String description;

    // Constructor
    public MyCalendar(int person_id, String name, String description) {
        setPerson_id(person_id);
        setName(name);
        setDescription(description);
    }

    public MyCalendar(int id, int person_id, String name, String description) {
        this(person_id, name, description);
        setId(id);
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException("Name is either null or empty.");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MyCalendar{" +
                "id=" + id +
                ", person_id=" + person_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
