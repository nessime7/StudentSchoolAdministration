package com.studentSara.studentSara.model;

import java.util.ArrayList;
import java.util.List;

public class CreateStudentRequest {

    private String name;
    private String surname;
    private String email;
    private int age;
    private List<SchoolClass> schoolClasses = new ArrayList<>();

    public CreateStudentRequest() {
    }

    public CreateStudentRequest(String name, String surname, String email, int age, List<SchoolClass> schoolClasses) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.schoolClasses = schoolClasses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }
}
