package com.studentSara.studentSara.repository;

import com.studentSara.studentSara.model.SchoolClass;
import com.studentSara.studentSara.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {

    private final List<SchoolClass> schoolClasses = new ArrayList<>(List.of(
            new SchoolClass(UUID.randomUUID(), "1A", new ArrayList<>(Set.of(
                    new Student(UUID.randomUUID(), "Sara", "Przebinda", "sara.przebinda@gmail.com", 28),
                    new Student(UUID.randomUUID(), "Albert", "Podraza", "albert.podraza@gmail.com", 28),
                    new Student(UUID.randomUUID(), "Julia", "Przebinda", "julia.przebinda@gmail.com", 17)
            ))),
            new SchoolClass(UUID.randomUUID(), "1B", new ArrayList<>(Set.of(
                    new Student(UUID.randomUUID(), "Basia", "Koks", "basia.koks@gmail.com", 22),
                    new Student(UUID.randomUUID(), "Karol", "Fox", "karol.fox@gmail.com", 28))))));


    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public void saveNewSchoolClass(SchoolClass schoolClass) {
        schoolClasses.add(schoolClass);
    }

    public void removeStudentById(UUID id) {
        final var student = getSchoolClasses().stream()
                .flatMap(a -> a.getStudents().stream())
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow();

        getSchoolClasses()
                .forEach(a -> a.getStudents().remove(student));
    }

    public void removeStudentFromClassById(UUID classId, UUID studentId) {
        final var student =
                getSchoolClasses().stream()
                        .filter(a -> a.getId().equals(classId))
                        .flatMap(a -> a.getStudents().stream())
                        .filter(a -> a.getId().equals(studentId))
                        .findFirst()
                        .orElseThrow();

        getSchoolClasses().stream()
                .filter(a -> a.getId().equals(classId))
                .forEach(a -> a.getStudents().remove(student));
    }
}
