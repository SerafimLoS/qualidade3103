package com.example.educationalqualityproject.controller;

import com.example.educationalqualityproject.entity.Teacher;
import com.example.educationalqualityproject.service.TeacherService;
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

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Test
    @DisplayName("Deve listar professores")
    void shouldListTeachers() throws Exception {
        Teacher teacher = new Teacher("Maria", "maria@escola.edu", "Matematica");
        when(teacherService.getAllTeachers()).thenReturn(List.of(teacher));

        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/list"))
                .andExpect(model().attributeExists("teachers"));
    }

    @Test
    @DisplayName("Deve abrir formulario de criacao")
    void shouldOpenCreateForm() throws Exception {
        mockMvc.perform(get("/teachers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/form"))
                .andExpect(model().attributeExists("teacher"));
    }

    @Test
    @DisplayName("Deve criar professor e redirecionar")
    void shouldCreateTeacherAndRedirect() throws Exception {
        mockMvc.perform(post("/teachers")
                        .param("name", "Maria")
                        .param("email", "maria@escola.edu")
                        .param("department", "Matematica"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teachers"));

        verify(teacherService, times(1)).saveTeacher(org.mockito.ArgumentMatchers.any(Teacher.class));
    }

    @Test
    @DisplayName("Deve abrir formulario de edicao")
    void shouldOpenEditForm() throws Exception {
        Teacher teacher = new Teacher("Maria", "maria@escola.edu", "Matematica");
        teacher.setId("t1");
        when(teacherService.getTeacherById("t1")).thenReturn(Optional.of(teacher));

        mockMvc.perform(get("/teachers/t1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/form"))
                .andExpect(model().attributeExists("teacher"));
    }

    @Test
    @DisplayName("Deve atualizar professor com mesmo ID")
    void shouldUpdateTeacherKeepingId() throws Exception {
        mockMvc.perform(post("/teachers/t1")
                        .param("name", "Maria Silva")
                        .param("email", "maria.silva@escola.edu")
                        .param("department", "Fisica"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teachers"));

        ArgumentCaptor<Teacher> captor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherService).saveTeacher(captor.capture());
        assertEquals("t1", captor.getValue().getId());
    }

    @Test
    @DisplayName("Deve excluir professor e redirecionar")
    void shouldDeleteTeacherAndRedirect() throws Exception {
        mockMvc.perform(get("/teachers/t1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/teachers"));

        verify(teacherService, times(1)).deleteTeacher("t1");
    }
}
