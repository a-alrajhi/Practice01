package com.doom.practice01.students;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {

  private static final List<Student> STUDENTS =
      Arrays.asList(
          new Student(1, "John Smith"),
          new Student(2, "Jane Doe"),
          new Student(3, "Michael Johnson"),
          new Student(4, "Emily Davis"));

  // Matches GET /management/api/v1/students
  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'ADMIN_TRAINEE')")
  public List<Student> getAllStudents() {
    System.out.println("getAllStudents");
    return STUDENTS;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('student:write')")
  public void registerNewStudent(@RequestBody Student student) {
    System.out.println("registerNewStudent");

    System.out.println(student);
  }

  @DeleteMapping(path = "{studentId}")
  @PreAuthorize("hasAuthority('student:write')")
  public void deleteStudent(@PathVariable("studentId") int studentId) {
    System.out.println("deleteStudent");
    System.out.println(studentId);
  }

  @PutMapping(path = "{studentId}")
  @PreAuthorize("hasAuthority('student:write')")
  public void updateStudent(
      @PathVariable("studentId") Integer studentId, @RequestBody Student student) {
    System.out.println(String.format("%s %s", student, studentId));
  }
}
