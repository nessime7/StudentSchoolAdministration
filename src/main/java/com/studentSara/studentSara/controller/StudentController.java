package com.studentSara.studentSara.controller;

import com.studentSara.studentSara.model.*;
import com.studentSara.studentSara.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("students")
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping("classes")
    public ResponseEntity<List<SchoolClass>> getStudentsInClass() {
        return ResponseEntity.ok(studentService.getSchoolClasses());
    }

    @PostMapping("classes/{id}/students")
    public ResponseEntity<SchoolClass> createStudent(@PathVariable UUID id, @RequestBody CreateStudentRequest request) {
        final var student = studentService.createStudent(id, request);
        return ResponseEntity.ok(student);
    }

    @PostMapping("classes")
    public ResponseEntity<Void> addSchoolClass(@RequestBody CreateSchoolClassRequest request) {
        studentService.createSchoolClass(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("students/{id}")
    public ResponseEntity<Student> editStudent(@PathVariable UUID id, @RequestBody EditStudentRequest request) {
        final var student = studentService.editStudent(id, request);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("classes/{classId}/students/{studentsId}")
    public ResponseEntity<Void> deleteStudentFromClass(@PathVariable("classId") UUID classId,
                                                       @PathVariable("studentsId") UUID studentsId) {
        studentService.deleteStudentFromClass(classId, studentsId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
