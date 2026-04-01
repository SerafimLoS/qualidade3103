# CHANGELOG

## [0.3.0] - 2026-03-15

### Nova Atividade: Gestão de Bugs

#### Objetivo
Ensinar os alunos a identificar, documentar e reportar bugs de forma profissional usando GitHub Issues.

#### Descrição da Atividade
Um bug foi introduzido propositalmente no código da aplicação. O problema está localizado em um **Service** e faz com que a aplicação falhe ao iniciar, mesmo que **todos os testes passem**.

Os alunos deverão:
1. Identificar que a aplicação não inicia corretamente
2. Investigar a causa raiz do problema
3. Criar uma **Issue no GitHub** documentando corretamente o bug, incluindo:
   - Título descritivo
   - Descrição do problema
   - Passos para reproduzir
   - Comportamento esperado vs. comportamento atual
   - Logs de erro relevantes
   - Screenshots (se aplicável)

#### Competências Desenvolvidas
- Debugging de aplicações Spring Boot
- Análise de logs e stack traces
- Identificação de bugs em tempo de inicialização (startup)
- Documentação profissional de bugs
- Uso adequado do GitHub Issues para rastreamento de problemas

#### Arquivos Relacionados
- Service com bug: `src/main/java/com/example/educationalqualityproject/service/`
- Issue template: `.github/ISSUE_TEMPLATE/bug_report.md`

---

## [0.2.0] - 2026-02-25

### Atualizado
- Cobertura de testes expandida com novos cenarios unitarios, web MVC e end-to-end.
- Configuracao de cobertura com JaCoCo adicionada ao Maven.
- Relatorio HTML de cobertura gerado em `target/site/jacoco/index.html`.
- Documentacao de diagramas UML de sequencia dos testes E2E criada em `docs/e2e-sequence-diagrams.md`.
- Remocao do arquivo `rtm.md`.

### Novos Testes
- `src/test/java/com/example/educationalqualityproject/service/TeacherServiceTest.java`
  - Cobre `getAllTeachers`, `getTeacherById`, `saveTeacher`, `deleteTeacher`, `existsByEmail`.
- `src/test/java/com/example/educationalqualityproject/controller/StudentControllerTest.java`
  - Cobre listagem, formulario de criacao, criacao, edicao, atualizacao e exclusao.
- `src/test/java/com/example/educationalqualityproject/controller/TeacherControllerTest.java`
  - Cobre listagem, formulario de criacao, criacao, edicao, atualizacao e exclusao.
- `src/test/java/com/example/educationalqualityproject/e2e/StudentApiE2ETest.java`
  - Cobre fluxos E2E de API para lista, criacao com sucesso, conflito por email, atualizacao inexistente e exclusao.
- `src/test/java/com/example/educationalqualityproject/e2e/TeacherApiE2ETest.java`
  - Cobre fluxos E2E de API para lista, criacao com sucesso, conflito por email, atualizacao inexistente e exclusao.
- `src/test/java/com/example/educationalqualityproject/e2e/StudentApiE2ETest.java`
  - Adicionado 1 teste E2E de desafio (`challengeTestShouldFailOnPurpose`) quebrado propositalmente, para o aluno corrigir a expectativa de status HTTP.
  - Comando para executar somente o teste quebrado: `mvn -q -Dtest=StudentApiE2ETest#challengeTestShouldFailOnPurpose test`

### Ajustes Tecnicos
- `pom.xml`
  - Inclusao do plugin `jacoco-maven-plugin` com `prepare-agent` e `report` na fase `verify`.

### Execucao e Resultado
- Comando executado: `mvn clean verify`
- Status: **SUCESSO**
- Cobertura global (JaCoCo):
  - Instrucoes: **74.23%** (484/652)
  - Branches: **30.00%** (12/40)
  - Metodos: **93.24%** (69/74)
