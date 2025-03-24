package se.lexicon.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {
    private int id;
    private int calendar_id;
    private String title;
    private String description;
    private LocalDateTime dateTime;


    // Constructor
    public Event(int calendar_id, String title, String description, LocalDateTime dateTime) {
        setCalendar_id(calendar_id);
        setTitle(title);
        setDescription(description);
        setDateTime(dateTime);
    }

    public Event(int id, int calendar_id, String title, String description, LocalDateTime dateTime) {
        this(calendar_id, title, description, dateTime);
        setId(id);
    }

    // Getters

    public int getId() {
        return id;
    }

    public int getCalendar_id() {
        return calendar_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setCalendar_id(int calendar_id) {
        this.calendar_id = calendar_id;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new NullPointerException("title is either null or empty.");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateTime(LocalDateTime dateTime) {
        Objects.requireNonNull(dateTime);
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", calendar_id=" + calendar_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
