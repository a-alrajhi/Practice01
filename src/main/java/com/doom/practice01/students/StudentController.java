package com.doom.practice01.students;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

  private static final List<Student> STUDENTS =
      Arrays.asList(
          new Student(1, "John Smith"),
          new Student(2, "Jane Doe"),
          new Student(3, "Michael Johnson"),
          new Student(4, "Emily Davis"),
          new Student(5, "David Wilson"),
          new Student(6, "Sophia Martinez"),
          new Student(7, "James Brown"),
          new Student(8, "Olivia Taylor"),
          new Student(9, "Daniel Anderson"),
          new Student(10, "Emma Thomas"));

  @GetMapping(path = "{studentId}")
  public Student getStudent(@PathVariable("studentId") Integer studentId) {
    return STUDENTS.stream()
        .filter(student -> studentId.equals(student.getStudentId()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Student not found!"));
  }

  @GetMapping("/all")
  public List<Student> getStudents() {
    System.out.println("Page accessed");
    return STUDENTS;
  }
}
