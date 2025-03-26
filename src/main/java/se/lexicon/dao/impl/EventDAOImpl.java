package se.lexicon.dao.impl;

import se.lexicon.dao.EventDAO;
import se.lexicon.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAOImpl implements EventDAO {
    private final Connection connection;

    public EventDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Event save(Event event) {
        Event eventToSave = null;
        String sql = "INSERT INTO event (calendar_id, title, description, date_time) VALUES (?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, event.getCalendar_id());
            statement.setString(2, event.getTitle());
            statement.setString(3, event.getDescription());
            statement.setDate(4, java.sql.Date.valueOf(event.getDateTime().toLocalDate()));
            // Participants Collection to the event_participants table

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {

                    eventToSave = event;
                    eventToSave.setId(generatedKeys.getInt(1));
                    addParticipants(event.getParticipants(), eventToSave.getId());
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR during saving event: " + e.getMessage());
            e.printStackTrace();
        }
        return eventToSave;
    }

    @Override
    public List<Event> findAllByCalendarId(int calendarId) {
        ArrayList<Event> eventsToFind = new ArrayList<>();
        String sql = "SELECT * FROM event WHERE calendar_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, calendarId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    eventsToFind.add(new Event(
                            resultSet.getInt("id"),
                            resultSet.getInt("calendar_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("date_time").toLocalDate().atStartOfDay()
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR during find by calendar id: " + e.getMessage());
            e.printStackTrace();
        }
        return eventsToFind;
    }

    @Override
    public void update(Event event) {
        String sql = "UPDATE event Set calendar_id = ?, title = ?, description = ?, date_time = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, event.getCalendar_id());
            statement.setString(2, event.getTitle());
            statement.setString(3, event.getDescription());
            statement.setDate(4, java.sql.Date.valueOf(event.getDateTime().toLocalDate()));
            statement.setInt(5, event.getId());


            if (statement.executeUpdate() == 1) {
                System.out.println("Updated Event Successfully");
                addParticipants(event.getParticipants(), event.getId());
            }

        } catch (SQLException e) {
            System.out.println("ERROR during updating event: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM event WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            if (statement.executeUpdate() == 1) {
                System.out.println("Event Deleted Successfully");
            }

        } catch (SQLException e) {
            System.out.println("ERROR during delete event: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void deleteParticipants(int eventId) {
        String sql = "Delete FROM event_participants WHERE event_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addParticipants(ArrayList<String> participants, int eventId) {
        deleteParticipants(eventId);
        String sql = "INSERT INTO event_participants (event_id, email) VALUES (?,?)";
        for (String email : participants) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, eventId);
                statement.setString(2, email);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}