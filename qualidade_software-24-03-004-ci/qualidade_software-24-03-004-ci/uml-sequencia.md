# UML de Sequencia dos Testes Criados

Este documento consolida os fluxos de sequencia da aplicacao exercitados pelos testes criados recentemente:
- `TeacherServiceTest`
- `StudentControllerTest`
- `TeacherControllerTest`
- `StudentApiE2ETest`
- `TeacherApiE2ETest`

## 1) TeacherServiceTest

```mermaid
sequenceDiagram
    participant T as Teste
    participant S as TeacherService
    participant R as TeacherRepository (mock)

    alt shouldReturnAllTeachers
        T->>S: getAllTeachers()
        S->>R: findAll()
        R-->>S: List<Teacher>
        S-->>T: List<Teacher>
    else shouldGetTeacherById
        T->>S: getTeacherById("t1")
        S->>R: findById("t1")
        R-->>S: Optional<Teacher>
        S-->>T: Optional<Teacher>
    else shouldSaveTeacher
        T->>S: saveTeacher(teacher)
        S->>R: save(teacher)
        R-->>S: Teacher salvo
        S-->>T: Teacher salvo
    else shouldDeleteTeacherById
        T->>S: deleteTeacher("t2")
        S->>R: deleteById("t2")
        R-->>S: ok
    else shouldCheckTeacherExistsByEmail
        T->>S: existsByEmail("prof@escola.edu")
        S->>R: existsByEmail("prof@escola.edu")
        R-->>S: true
        S-->>T: true
    end
```

## 2) StudentControllerTest

```mermaid
sequenceDiagram
    participant T as Teste MockMvc
    participant C as StudentController
    participant S as StudentService (mock)
    participant V as View Resolver

    alt shouldListStudents
        T->>C: GET /students
        C->>S: getAllStudents()
        S-->>C: lista de estudantes
        C->>V: render "student/list"
        V-->>T: 200 + model.students
    else shouldOpenCreateForm
        T->>C: GET /students/new
        C->>V: render "student/form" com objeto vazio
        V-->>T: 200 + model.student
    else shouldCreateStudentAndRedirect
        T->>C: POST /students (form)
        C->>S: saveStudent(student)
        S-->>C: ok
        C-->>T: 302 redirect:/students
    else shouldOpenEditForm
        T->>C: GET /students/{id}/edit
        C->>S: getStudentById(id)
        S-->>C: Optional<Student>
        C->>V: render "student/form"
        V-->>T: 200 + model.student
    else shouldUpdateStudentKeepingId
        T->>C: POST /students/{id} (form)
        C->>C: student.setId(id)
        C->>S: saveStudent(student com id original)
        S-->>C: ok
        C-->>T: 302 redirect:/students
    else shouldDeleteStudentAndRedirect
        T->>C: GET /students/{id}/delete
        C->>S: deleteStudent(id)
        S-->>C: ok
        C-->>T: 302 redirect:/students
    end
```

## 3) TeacherControllerTest

```mermaid
sequenceDiagram
    participant T as Teste MockMvc
    participant C as TeacherController
    participant S as TeacherService (mock)
    participant V as View Resolver

    alt shouldListTeachers
        T->>C: GET /teachers
        C->>S: getAllTeachers()
        S-->>C: lista de professores
        C->>V: render "teacher/list"
        V-->>T: 200 + model.teachers
    else shouldOpenCreateForm
        T->>C: GET /teachers/new
        C->>V: render "teacher/form" com objeto vazio
        V-->>T: 200 + model.teacher
    else shouldCreateTeacherAndRedirect
        T->>C: POST /teachers (form)
        C->>S: saveTeacher(teacher)
        S-->>C: ok
        C-->>T: 302 redirect:/teachers
    else shouldOpenEditForm
        T->>C: GET /teachers/{id}/edit
        C->>S: getTeacherById(id)
        S-->>C: Optional<Teacher>
        C->>V: render "teacher/form"
        V-->>T: 200 + model.teacher
    else shouldUpdateTeacherKeepingId
        T->>C: POST /teachers/{id} (form)
        C->>C: teacher.setId(id)
        C->>S: saveTeacher(teacher com id original)
        S-->>C: ok
        C-->>T: 302 redirect:/teachers
    else shouldDeleteTeacherAndRedirect
        T->>C: GET /teachers/{id}/delete
        C->>S: deleteTeacher(id)
        S-->>C: ok
        C-->>T: 302 redirect:/teachers
    end
```

## 4) StudentApiE2ETest

```mermaid
sequenceDiagram
    participant T as Teste E2E (MockMvc)
    participant API as StudentApiController
    participant S as StudentService
    participant R as StudentRepository (mock)

    alt shouldReturnStudentsList
        T->>API: GET /api/students
        API->>S: getAllStudents()
        S->>R: findAll()
        R-->>S: lista de estudantes
        S-->>API: lista de estudantes
        API-->>T: 200 OK
    else shouldCreateStudent
        T->>API: POST /api/students
        API->>S: existsByEmail(email)
        S->>R: existsByEmail(email)
        R-->>S: false
        API->>S: existsByRegistrationNumber(registrationNumber)
        S->>R: existsByRegistrationNumber(registrationNumber)
        R-->>S: false
        API->>S: saveStudent(student)
        S->>R: save(student)
        R-->>S: student salvo
        S-->>API: student salvo
        API-->>T: 201 Created
    else shouldReturnConflictWhenEmailAlreadyExists
        T->>API: POST /api/students (email duplicado)
        API->>S: existsByEmail(email)
        S->>R: existsByEmail(email)
        R-->>S: true
        API-->>T: 409 Conflict
    else shouldReturnNotFoundWhenUpdatingMissingStudent
        T->>API: PUT /api/students/missing
        API->>S: getStudentById("missing")
        S->>R: findById("missing")
        R-->>S: Optional.empty
        API-->>T: 404 Not Found
    else shouldDeleteStudent
        T->>API: DELETE /api/students/s1
        API->>S: getStudentById("s1")
        S->>R: findById("s1")
        R-->>S: Optional<Student>
        API->>S: deleteStudent("s1")
        S->>R: deleteById("s1")
        API-->>T: 204 No Content
    else challengeTestShouldFailOnPurpose
        T->>API: GET /api/students
        API->>S: getAllStudents()
        S->>R: findAll()
        R-->>S: lista de estudantes
        API-->>T: 200 OK (real)
        Note over T: Teste espera 201 Created e falha propositalmente
    end
```

## 5) TeacherApiE2ETest

```mermaid
sequenceDiagram
    participant T as Teste E2E (MockMvc)
    participant API as TeacherApiController
    participant S as TeacherService
    participant R as TeacherRepository (mock)

    alt shouldReturnTeachersList
        T->>API: GET /api/teachers
        API->>S: getAllTeachers()
        S->>R: findAll()
        R-->>S: lista de professores
        API-->>T: 200 OK
    else shouldCreateTeacher
        T->>API: POST /api/teachers
        API->>S: existsByEmail(email)
        S->>R: existsByEmail(email)
        R-->>S: false
        API->>S: saveTeacher(teacher)
        S->>R: save(teacher)
        R-->>S: teacher salvo
        API-->>T: 201 Created
    else shouldReturnConflictWhenEmailAlreadyExists
        T->>API: POST /api/teachers (email duplicado)
        API->>S: existsByEmail(email)
        S->>R: existsByEmail(email)
        R-->>S: true
        API-->>T: 409 Conflict
    else shouldReturnNotFoundWhenUpdatingMissingTeacher
        T->>API: PUT /api/teachers/missing
        API->>S: getTeacherById("missing")
        S->>R: findById("missing")
        R-->>S: Optional.empty
        API-->>T: 404 Not Found
    else shouldDeleteTeacher
        T->>API: DELETE /api/teachers/t1
        API->>S: getTeacherById("t1")
        S->>R: findById("t1")
        R-->>S: Optional<Teacher>
        API->>S: deleteTeacher("t1")
        S->>R: deleteById("t1")
        API-->>T: 204 No Content
    end
```

## Alternativa: PlantUML (site)

Voce tambem pode renderizar os mesmos fluxos usando PlantUML no site oficial:

1. Acesse `https://www.plantuml.com/plantuml/uml/`.
2. Converta o fluxo para sintaxe PlantUML entre `@startuml` e `@enduml`.
3. Clique em `Submit`.
4. Baixe a imagem (PNG/SVG) e salve no projeto (ex.: `docs/uml/`).
