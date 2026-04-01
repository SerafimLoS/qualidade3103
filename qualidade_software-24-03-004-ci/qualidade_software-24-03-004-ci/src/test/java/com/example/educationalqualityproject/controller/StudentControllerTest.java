package com.example.educationalqualityproject.controller;

import com.example.educationalqualityproject.entity.Student;
import com.example.educationalqualityproject.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    @DisplayName("Deve listar estudantes")
    void shouldListStudents() throws Exception {
        Student student = new Student("Joao", "joao@escola.edu", "R001");
        when(studentService.getAllStudents()).thenReturn(List.of(student));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/list"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    @DisplayName("Deve abrir formulario de criacao")
    void shouldOpenCreateForm() throws Exception {
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/form"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    @DisplayName("Deve criar estudante e redirecionar")
    void shouldCreateStudentAndRedirect() throws Exception {
        mockMvc.perform(post("/students")
                        .param("name", "Joao")
                        .param("email", "joao@escola.edu")
                        .param("registrationNumber", "R001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/students"));

        verify(studentService, times(1)).saveStudent(org.mockito.ArgumentMatchers.any(Student.class));
    }

    @Test
    @DisplayName("Deve abrir formulario de edicao")
    void shouldOpenEditForm() throws Exception {
        Student student = new Student("Joao", "joao@escola.edu", "R001");
        student.setId("s1");
        when(studentService.getStudentById("s1")).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/s1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/form"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    @DisplayName("Deve atualizar estudante com mesmo ID")
    void shouldUpdateStudentKeepingId() throws Exception {
        mockMvc.perform(post("/students/s1")
                        .param("name", "Joao Silva")
                        .param("email", "joao.silva@escola.edu")
                        .param("registrationNumber", "R001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/students"));

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentService).saveStudent(captor.capture());
        assertEquals("s1", captor.getValue().getId());
    }

    @Test
    @DisplayName("Deve excluir estudante e redirecionar")
    void shouldDeleteStudentAndRedirect() throws Exception {
        mockMvc.perform(get("/students/s1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/students"));

        verify(studentService, times(1)).deleteStudent("s1");
    }
}
