package se.lexicon.dao.impl;

import se.lexicon.dao.MyCalendarDAO;
import se.lexicon.model.MyCalendar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyCalendarDAOImpl implements MyCalendarDAO {

    private final Connection connection;

    public MyCalendarDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public MyCalendar save(MyCalendar calendar) {
        MyCalendar calendarToSave = null;
        String sql = "INSERT INTO my_calendar (person_id, name, description) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, calendar.getPerson_id());
            statement.setString(2, calendar.getName());
            statement.setString(3, calendar.getDescription());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    calendarToSave = findById(generatedKeys.getInt(1)).get();
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR during save my_calendar : " + e.getMessage());
            e.printStackTrace();
        }
        return calendarToSave;
    }

    @Override
    public Optional<MyCalendar> findById(int id) {
        Optional<MyCalendar> optionalMyCalenderToReturn = Optional.empty();
        String sql = "SELECT * FROM my_calendar WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    optionalMyCalenderToReturn = Optional.of(new MyCalendar(
                            resultSet.getInt("id"),
                            resultSet.getInt("person_id"),
                            resultSet.getString("name"),
                            resultSet.getString("description")));
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR during find my_calendar by id: " + e.getMessage());
            e.printStackTrace();
        }

        return optionalMyCalenderToReturn;
    }

    @Override
    public List<MyCalendar> findAllByOwnerId(int ownerId) {
        ArrayList<MyCalendar> listToReturn = new ArrayList<>();
        String sql = "SELECT * FROM my_calendar WHERE person_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ownerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    listToReturn.add(new MyCalendar(
                            resultSet.getInt("id"),
                            resultSet.getInt("person_id"),
                            resultSet.getString("name"),
                            resultSet.getString("description")));
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR during find all by owner id: " + e.getMessage());
            e.printStackTrace();
        }


        return listToReturn;
    }

    @Override
    public void update(MyCalendar calendar) {
        String sql = "UPDATE my_calendar SET person_id = ?, name = ?, description = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, calendar.getPerson_id());
            statement.setString(2, calendar.getName());
            statement.setString(3, calendar.getDescription());
            statement.setInt(4, calendar.getId());
            if (statement.executeUpdate()== 1){
                System.out.println("Updated " + calendar.getName() + "Successfully");
            }
        } catch (SQLException e) {
            System.out.println("ERROR during update my_calendar: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM my_calendar WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            if (statement.executeUpdate() == 1 ) {
                System.out.println("Deleted Calendar Successfully.");
            }

        }catch (SQLException e) {
            System.out.println("ERROR during delete my_calendar: " + e.getMessage());
            e.printStackTrace();
        }


    }
}
