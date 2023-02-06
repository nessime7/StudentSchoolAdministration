package com.studentSara.studentSara.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SchoolClass {

    private UUID id;
    private String name;
    private List<Student> students = new ArrayList<>();

    public SchoolClass(UUID id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public SchoolClass() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
