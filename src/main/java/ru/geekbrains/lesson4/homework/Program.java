package ru.geekbrains.lesson4.homework;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.geekbrains.lesson4.models.Course;
import ru.geekbrains.lesson4.models.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Задание
 * =======
 * Создайте базу данных (например, SchoolDB).
 * В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
 * Настройте Hibernate для работы с вашей базой данных.
 * Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
 * Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
 * Убедитесь, что каждая операция выполняется в отдельной транзакции.
 */

public class Program {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:12345";
        String user = "root";
        String password = "root";

        try {
            Connection connection = DriverManager.getConnection(url,user,password);
//            // Создание базы данных школа
//            connectToDB(connection);
//            System.out.println("Data Base school is successfully");

//            // Использование таблицы
//            useDatabase(connection);
//
//            // Создание таблицы
//            createTable(connection);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            addCourseToSchoolDB(session);
            List list = readAllCoursesFromDB(session);
            list.forEach(System.out::println);
            updateByIdCourse(session, (Course) list.get(3), 500);
            deleteCourseFromSchoolDB(session, 5);
            session.getTransaction().commit();
        } finally {
            // Закрытие фабрики сессий
            sessionFactory.close();
        }

    }

    private static void connectToDB (Connection connection) throws SQLException {
        String createDatabaseSQL =  "CREATE DATABASE IF NOT EXISTS schoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " +
                "courses (id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nameCourse VARCHAR(255), duration INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

    private static void useDatabase(Connection connection) throws SQLException {
        String useDatabaseSQL =  "USE schoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void addCourseToSchoolDB (Session session) {
//        session.beginTransaction();
        Course course = Course.create();
        session.save(course);
        System.out.println("Course save successfully");
//        session.getTransaction().commit();
    }

    /**
     * Получение всех курсов из базы данных
     * @param session - текущая сессия
     * @return - лист курсов.
     */
    private static List readAllCoursesFromDB (Session session) {
//        session.beginTransaction();
        List courses = session.createSQLQuery("SELECT * FROM courses").addEntity(Course.class).list();
//        session.getTransaction().commit();
        return courses;
    }

    private static void updateByIdCourse (Session session, Course course, int duration) {
//        session.beginTransaction();
        Course retrievedCourse = session.get(Course.class, course.getId());
        retrievedCourse.setDuration(duration);
        session.update(retrievedCourse);
        System.out.println("Course was updated");
//        session.getTransaction().commit();
    }

    private static void deleteCourseFromSchoolDB (Session session, int id) {
//        session.beginTransaction();
        Course retrivedCourse = session.get(Course.class, id);
        session.delete(retrivedCourse);
        System.out.println("Course was deleted...");
//        session.getTransaction().commit();
    }


}
