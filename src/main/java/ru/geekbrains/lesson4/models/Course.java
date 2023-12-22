package ru.geekbrains.lesson4.models;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "courses")
public class Course {
    private static final String[] courses = new String[] { "Математкика", "Химия", "История", "Литература",
            "Физика", "Технология", "Алгебра", "Геометрия", "Физкультура", "Биология" };
    private static final Random random = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nameCourse;
    private int duration;

    public static Course create(){
        return new Course(courses[random.nextInt(courses.length)], random.nextInt(4, 10)*10);
    }


    public Course(int id, String course, int duration) {
        this.id = id;
        this.nameCourse = course;
        this.duration = duration;
    }

    public Course(String course, int duration) {
        this.nameCourse = course;
        this.duration = duration;
    }
    public Course() {

    }

    public int getId() {
        return id;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", course='" + nameCourse + '\'' +
                ", duration=" + duration +
                '}';
    }
}
