package com.doom.practice01.students;

public class Student {

  public int getStudentId() {
    return studentId;
  }

  public String getStudentName() {
    return studentName;
  }

  private final int studentId;
  private final String studentName;

  public Student(int studentId, String studentName) {
    this.studentId = studentId;
    this.studentName = studentName;
  }

  @Override
  public String toString() {
    return "Student{" + "studentId=" + studentId + ", studentName='" + studentName + '\'' + '}';
  }
}
