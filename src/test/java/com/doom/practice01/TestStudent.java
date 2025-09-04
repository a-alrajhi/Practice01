package com.doom.practice01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.doom.practice01.students.StudentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class TestStudent {
  @Autowired WebTestClient web;

  @Autowired StudentController controller;

  @Test
  void testGetStudentById() {
    controller = new StudentController();
    assertEquals(1, controller.getStudent(1).getStudentId());
    assertEquals(2, controller.getStudent(2).getStudentId());
    assertEquals(3, controller.getStudent(3).getStudentId());
  }

  @Test
  void allStudents_sizeIs4() {
    web.get()
        .uri("/management/api/v1/students")
        .headers(h -> h.setBasicAuth("mo", "mo"))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.length()")
        .isEqualTo(4);
  }

  @Test
  void firstStudent_matches() {
    web.get()
        .uri("/management/api/v1/students")
        .headers(h -> h.setBasicAuth("mo", "mo"))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].studentId")
        .isEqualTo(1)
        .jsonPath("$[0].studentName")
        .isEqualTo("John Smith");
  }
}
