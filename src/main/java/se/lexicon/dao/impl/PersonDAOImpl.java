package se.lexicon.dao.impl;

import se.lexicon.dao.PersonDAO;
import se.lexicon.exception.EmailAlreadyExistsException;
import se.lexicon.model.Person;

import java.sql.*;
import java.util.Optional;

public class PersonDAOImpl implements PersonDAO {
    private final Connection connection;

    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person save(Person person) {
        Person personToSave = null;
        String sql = "INSERT INTO person (name, email) VALUES (?,?)";
        try {
            emailAvailable(person.getEmail());
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, person.getName());
                statement.setString(2, person.getEmail());
                statement.executeUpdate();

                try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                    if (generatedKey.next()) {
                        personToSave = findById(generatedKey.getInt(1)).orElse(null);
                    }
                }

            }
        } catch (SQLException | EmailAlreadyExistsException e) {
            System.out.println("ERROR during saving person: " + e.getMessage());
            e.printStackTrace();
        }
        return personToSave;
    }

    @Override
    public Optional<Person> findById(int id) {
        Optional<Person> optionalPersonToReturn = Optional.empty();
        String sql = "SELECT * FROM person WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    optionalPersonToReturn = Optional.of(new Person(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email")));
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR during find person by id: " + e.getMessage());
            e.printStackTrace();
        }

        return optionalPersonToReturn;
    }

    @Override
    public void update(Person person) {
        String sql = "UPDATE person set name = ?, email = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getName());
            statement.setString(2, person.getEmail());
            statement.setInt(3, person.getId());
            if (statement.executeUpdate() == 1) {
                System.out.println("Updated Person Successfully");
            }
        } catch (SQLException e) {
            System.out.println("ERROR during update person: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM person WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            if (statement.executeUpdate() == 1) {
                System.out.println("Deleted Person Successfully");
            }

        } catch (SQLException e) {
            System.out.println("ERROR during delete person: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void emailAvailable(String email) throws EmailAlreadyExistsException, SQLException{
        String sql = "SELECT * FROM person WHERE email = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return;
                }
            }
        }
        throw new EmailAlreadyExistsException("Provided email already taken.");
    }

}
