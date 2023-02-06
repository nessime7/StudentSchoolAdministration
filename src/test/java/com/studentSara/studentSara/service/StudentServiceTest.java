package com.studentSara.studentSara.service;

import com.studentSara.studentSara.model.*;
import com.studentSara.studentSara.repository.StudentRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class StudentServiceTest {

    private final StudentRepository studentRepository = mock(StudentRepository.class);
    private final StudentService studentService = new StudentService(studentRepository);

    @Test
    public void should_create_a_student() {
        // given
        var request = new CreateStudentRequest("Nowy", "Student", "nowy.student@gmail.com", 1, null);
        var schoolClassId = UUID.randomUUID();
        when(studentRepository.getSchoolClasses()).thenReturn(new ArrayList<>(List.of(new SchoolClass(schoolClassId, "1a", new ArrayList<>(List.of(new Student(UUID.randomUUID(), "dasdme", "dasd", "dsaf@wp.pl", 23)))))));

        // when
        var schoolClass = studentService.createStudent(schoolClassId, request);

        // then
        var student = schoolClass.getStudents().stream().filter(sc -> sc.getName().equals("Nowy")).findFirst().orElseThrow();
        assertEquals("Student", student.getSurname());
        assertEquals("nowy.student@gmail.com", student.getEmail());
        assertEquals(1, student.getAge());
        assertEquals(2, schoolClass.getStudents().size());
    }

    @Test
    public void should_create_a_school_class() {
        // given
        var schoolClassId = UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95471");
        var schoolClass = new SchoolClass(schoolClassId, "1A", null);
        var createSchoolClassRequest = new CreateSchoolClassRequest(schoolClass.getName(), null);

        // when
        studentService.createSchoolClass(createSchoolClassRequest);

        // then
        then(studentRepository).should().saveNewSchoolClass(any(SchoolClass.class));
        // or
        then(studentRepository).should().saveNewSchoolClass(
                argThat(sch -> sch.getName().equals(schoolClass.getName())));
    }

    @Test
    public void should_return_all_students() {
        // given
        when(studentRepository.getSchoolClasses()).thenReturn(new ArrayList<>(List.of(new SchoolClass(UUID.randomUUID(), "1a", new ArrayList<>(List.of(new Student(UUID.randomUUID(), "dasdme", "dasd", "dsaf@wp.pl", 23)))))));

        // when
        var students = studentService.getStudents().size();

        // then
        assertEquals(1, students);
    }

    @Test
    public void should_return_all_class() {
        // given
        when(studentRepository.getSchoolClasses()).thenReturn(new ArrayList<>(List.of(new SchoolClass(UUID.randomUUID(), "1a", new ArrayList<>(List.of(new Student(UUID.randomUUID(), "dasdme", "dasd", "dsaf@wp.pl", 23)))))));

        // when
        var schoolClasses = studentRepository.getSchoolClasses().size();

        // then
        assertEquals(1, schoolClasses);
    }

    @Test
    public void should_remove_student_by_id() {
        // given
        var studentId = UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477");

        // when
        studentService.deleteStudent(studentId);

        // then
        then(studentRepository).should().removeStudentById(studentId);
    }

    @Test
    public void should_remove_student_from_school_class() {
        // given
        var schoolClassId = UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95471");
        var studentId = UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477");

        // when
        studentService.deleteStudentFromClass(schoolClassId, studentId);

        // then
        then(studentRepository).should().removeStudentFromClassById(schoolClassId, studentId);
    }

    @Test
    public void should_edit_a_student() {
        // given
        var studentId = UUID.fromString("3b0485ed-f8c5-4faf-a8ce-fe6f34b95477");
        var request = new EditStudentRequest("Dupa", "Dupa", "dupa.dupa@gmail.com", 1);
        when(studentRepository.getSchoolClasses()).thenReturn(new ArrayList<>(List.of(new SchoolClass(UUID.randomUUID(), "1a", new ArrayList<>(List.of(new Student(studentId, "dasdme", "dasd", "dsaf@wp.pl", 23)))))));

        // when
        var editStudent = studentService.editStudent(studentId, request);

        // then
        assertEquals("Dupa", editStudent.getName());
    }

    @Test(expected = NullPointerException.class)
    public void should_not_edit_student_when_id_is_not_exist() {
        // given
        var request = new EditStudentRequest("Dupa", "Dupa", "dupa.dupa@gmail.com", 1);
        when(studentRepository.getSchoolClasses()).thenReturn(new ArrayList<>(List.of(new SchoolClass(UUID.randomUUID(), "1a", new ArrayList<>(List.of(new Student(null, "dasdme", "dasd", "dsaf@wp.pl", 23)))))));

        // when
        var editStudent = studentService.editStudent(null, request);

        // then
        assertThrows(NullPointerException.class, () -> studentService.editStudent(null,request));
    }
}