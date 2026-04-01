package com.example.educationalqualityproject.service;

import com.example.educationalqualityproject.entity.Teacher;
import com.example.educationalqualityproject.repository.TeacherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    @DisplayName("Deve retornar todos os professores")
    void shouldReturnAllTeachers() {
        Teacher teacher = new Teacher("Maria", "maria@escola.edu", "Matematica");
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));

        List<Teacher> result = teacherService.getAllTeachers();

        assertEquals(1, result.size());
        assertEquals("Maria", result.get(0).getName());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar professor por ID")
    void shouldGetTeacherById() {
        Teacher teacher = new Teacher("Carlos", "carlos@escola.edu", "Fisica");
        teacher.setId("t1");
        when(teacherRepository.findById("t1")).thenReturn(Optional.of(teacher));

        Optional<Teacher> result = teacherService.getTeacherById("t1");

        assertTrue(result.isPresent());
        assertEquals("Carlos", result.get().getName());
        verify(teacherRepository, times(1)).findById("t1");
    }

    @Test
    @DisplayName("Deve salvar professor")
    void shouldSaveTeacher() {
        Teacher teacher = new Teacher("Ana", "ana@escola.edu", "Quimica");
        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher result = teacherService.saveTeacher(teacher);

        assertEquals("Ana", result.getName());
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    @DisplayName("Deve deletar professor por ID")
    void shouldDeleteTeacherById() {
        teacherService.deleteTeacher("t2");

        verify(teacherRepository, times(1)).deleteById("t2");
    }

    @Test
    @DisplayName("Deve verificar existencia por email")
    void shouldCheckTeacherExistsByEmail() {
        when(teacherRepository.existsByEmail("prof@escola.edu")).thenReturn(true);

        boolean result = teacherService.existsByEmail("prof@escola.edu");

        assertTrue(result);
        verify(teacherRepository, times(1)).existsByEmail("prof@escola.edu");
    }
}
