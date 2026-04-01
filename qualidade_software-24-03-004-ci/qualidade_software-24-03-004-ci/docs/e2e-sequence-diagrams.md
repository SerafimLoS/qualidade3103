# Diagramas UML de Sequencia - Testes End-to-End

Este documento descreve os fluxos E2E cobertos pelos testes em:
- `src/test/java/com/example/educationalqualityproject/e2e/StudentApiE2ETest.java`
- `src/test/java/com/example/educationalqualityproject/e2e/TeacherApiE2ETest.java`

## 1. Criacao de Student com sucesso (`POST /api/students`)
```mermaid
sequenceDiagram
    participant Test as E2E Test (MockMvc)
    participant API as StudentApiController
    participant Service as StudentService
    participant Repo as StudentRepository (mock)

    Test->>API: POST /api/students {name,email,registrationNumber}
    API->>Service: existsByEmail(email)
    Service->>Repo: existsByEmail(email)
    Repo-->>Service: false
    Service-->>API: false

    API->>Service: existsByRegistrationNumber(registrationNumber)
    Service->>Repo: existsByRegistrationNumber(registrationNumber)
    Repo-->>Service: false
    Service-->>API: false

    API->>Service: saveStudent(student)
    Service->>Repo: save(student)
    Repo-->>Service: student(id preenchido)
    Service-->>API: student salvo
    API-->>Test: 201 Created + body JSON
```

## 2. Conflito de Student por email duplicado (`POST /api/students`)
```mermaid
sequenceDiagram
    participant Test as E2E Test (MockMvc)
    participant API as StudentApiController
    participant Service as StudentService
    participant Repo as StudentRepository (mock)

    Test->>API: POST /api/students {email duplicado}
    API->>Service: existsByEmail(email)
    Service->>Repo: existsByEmail(email)
    Repo-->>Service: true
    Service-->>API: true
    API-->>Test: 409 Conflict
```

## 3. Atualizacao de Teacher inexistente (`PUT /api/teachers/{id}`)
```mermaid
sequenceDiagram
    participant Test as E2E Test (MockMvc)
    participant API as TeacherApiController
    participant Service as TeacherService
    participant Repo as TeacherRepository (mock)

    Test->>API: PUT /api/teachers/missing {payload}
    API->>Service: getTeacherById("missing")
    Service->>Repo: findById("missing")
    Repo-->>Service: Optional.empty
    Service-->>API: Optional.empty
    API-->>Test: 404 Not Found
```

## 4. Exclusao de Teacher com sucesso (`DELETE /api/teachers/{id}`)
```mermaid
sequenceDiagram
    participant Test as E2E Test (MockMvc)
    participant API as TeacherApiController
    participant Service as TeacherService
    participant Repo as TeacherRepository (mock)

    Test->>API: DELETE /api/teachers/t1
    API->>Service: getTeacherById("t1")
    Service->>Repo: findById("t1")
    Repo-->>Service: Teacher encontrado
    Service-->>API: Optional<Teacher>

    API->>Service: deleteTeacher("t1")
    Service->>Repo: deleteById("t1")
    Repo-->>Service: ok
    Service-->>API: ok
    API-->>Test: 204 No Content
```
