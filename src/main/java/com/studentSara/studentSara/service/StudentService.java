package com.studentSara.studentSara.service;

import com.studentSara.studentSara.model.*;
import com.studentSara.studentSara.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.getSchoolClasses().stream()
                .flatMap(a -> a.getStudents().stream())
                .collect(Collectors.toList());
    }

    public List<SchoolClass> getSchoolClasses() {
        return studentRepository.getSchoolClasses();
    }

    public SchoolClass createStudent(UUID id, CreateStudentRequest request) {
        return studentRepository.getSchoolClasses().stream()
                .filter(schoolClass -> schoolClass.getId().equals(id))
                .peek(a -> a.getStudents().add(new Student(UUID.randomUUID(),
                        request.getName(),
                        request.getSurname(),
                        request.getEmail(),
                        request.getAge())))
                .findFirst()
                .orElseThrow();
    }

    public Student editStudent(UUID id, EditStudentRequest request){
        return studentRepository.getSchoolClasses().stream()
                .flatMap(a -> a.getStudents().stream())
                .filter(a -> a.getId().equals(id))
                .peek(a -> editStudent(request, a))
                .findFirst()
                .orElseThrow();
    }

    private void editStudent(EditStudentRequest request, Student a) {
        if (request.getName() != null) {
            a.setName(request.getName());
        }

        if (request.getAge() != 0) {
            a.setAge(request.getAge());
        }

        if (request.getEmail() != null) {
            a.setEmail(request.getEmail());
        }

        if (request.getSurname() != null) {
            a.setSurname(request.getSurname());
        }
    }

    public void deleteStudent(UUID id) {
        studentRepository.removeStudentById(id);
    }

    public void createSchoolClass(CreateSchoolClassRequest request) {
        studentRepository.saveNewSchoolClass(new SchoolClass(UUID.randomUUID(), request.getName(), new ArrayList<>()));
    }

    public void deleteStudentFromClass(UUID classId, UUID studentId) {
        studentRepository.removeStudentFromClassById(classId, studentId);
    }
}
