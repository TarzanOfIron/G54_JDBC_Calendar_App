package se.lexicon;

import se.lexicon.dao.impl.MyCalendarDAOImpl;
import se.lexicon.dao.impl.PersonDAOImpl;
import se.lexicon.db.DatabaseManager;
import se.lexicon.model.MyCalendar;
import se.lexicon.model.Person;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = DatabaseManager.getConnection()) {
            PersonDAOImpl personDAO = new PersonDAOImpl(connection);

            // Person
            /*
            Person testPerson = new Person("Botond", "email");
            System.out.println(personDAO.save(testPerson));
            System.out.println(personDAO.findById(1));
            personDAO.update(new Person(1, "aaa", "aaaemail"));
            System.out.println(personDAO.findById(1));
            personDAO.delete(1);
            System.out.println(personDAO.findById(1));
             */

            MyCalendarDAOImpl myCalendarDAO = new MyCalendarDAOImpl(connection);
            //MyCalendar myCalendar = new MyCalendar(1,"My First Calendar", "This is a test :)");
            //System.out.println(myCalendarDAO.save(myCalendar));
            //myCalendarDAO.findAllByOwnerId(1).forEach(System.out::println);
            //MyCalendar myCalendar = new MyCalendar(1,1,"Updated calendar name", "This is a test :)");
            //myCalendarDAO.update(myCalendar);
            //System.out.println(myCalendarDAO.findById(1));
            myCalendarDAO.delete(1);




        } catch (SQLException e) {
            System.out.println("FAAAASZ");
        }

    }
}