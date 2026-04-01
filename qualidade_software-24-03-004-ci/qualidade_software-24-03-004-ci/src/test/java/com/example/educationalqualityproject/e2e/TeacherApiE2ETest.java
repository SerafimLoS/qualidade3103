package com.example.educationalqualityproject.e2e;

import com.example.educationalqualityproject.entity.Teacher;
import com.example.educationalqualityproject.repository.StudentRepository;
import com.example.educationalqualityproject.repository.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeacherApiE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeacherRepository teacherRepository;

    @MockBean
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        reset(teacherRepository, studentRepository);
    }

    @Test
    @DisplayName("E2E API Teacher - GET /api/teachers retorna lista")
    void shouldReturnTeachersList() throws Exception {
        Teacher teacher = new Teacher("Maria", "maria@escola.edu", "Matematica");
        teacher.setId("t1");
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));

        mockMvc.perform(get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("t1"))
                .andExpect(jsonPath("$[0].department").value("Matematica"));
    }

    @Test
    @DisplayName("E2E API Teacher - POST /api/teachers cria professor")
    void shouldCreateTeacher() throws Exception {
        Teacher request = new Teacher("Paulo", "paulo@escola.edu", "Fisica");
        Teacher saved = new Teacher("Paulo", "paulo@escola.edu", "Fisica");
        saved.setId("t2");

        when(teacherRepository.existsByEmail("paulo@escola.edu")).thenReturn(false);
        when(teacherRepository.save(any(Teacher.class))).thenReturn(saved);

        mockMvc.perform(post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("t2"))
                .andExpect(jsonPath("$.name").value("Paulo"));

        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }

    @Test
    @DisplayName("E2E API Teacher - POST /api/teachers retorna 409 para email duplicado")
    void shouldReturnConflictWhenEmailAlreadyExists() throws Exception {
        Teacher request = new Teacher("Paulo", "paulo@escola.edu", "Fisica");

        when(teacherRepository.existsByEmail("paulo@escola.edu")).thenReturn(true);

        mockMvc.perform(post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(teacherRepository, never()).save(any(Teacher.class));
    }

    @Test
    @DisplayName("E2E API Teacher - PUT /api/teachers/{id} retorna 404 quando nao existe")
    void shouldReturnNotFoundWhenUpdatingMissingTeacher() throws Exception {
        Teacher request = new Teacher("Novo Nome", "novo@escola.edu", "Biologia");

        when(teacherRepository.findById("missing")).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/teachers/missing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("E2E API Teacher - DELETE /api/teachers/{id} remove professor")
    void shouldDeleteTeacher() throws Exception {
        Teacher teacher = new Teacher("Maria", "maria@escola.edu", "Matematica");
        teacher.setId("t1");
        when(teacherRepository.findById("t1")).thenReturn(Optional.of(teacher));

        mockMvc.perform(delete("/api/teachers/t1"))
                .andExpect(status().isNoContent());

        verify(teacherRepository, times(1)).deleteById("t1");
    }
}
