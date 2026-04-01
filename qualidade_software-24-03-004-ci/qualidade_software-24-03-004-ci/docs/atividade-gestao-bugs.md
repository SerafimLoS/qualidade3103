# Atividade: Gestão de Bugs

## 🎯 Objetivo

Aprender a identificar, investigar e documentar bugs de forma profissional usando GitHub Issues.

## 📋 Cenário

Um bug foi introduzido propositalmente nesta aplicação. O problema está em um **Service** e faz com que a aplicação **falhe ao iniciar**, mesmo que todos os testes unitários passem.

## 🔍 Sua Missão

### Parte 1: Identificação do Problema

1. Tente rodar a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

2. Observe que a aplicação não inicia corretamente.

3. Analise os logs para identificar a causa raiz do problema.

### Parte 2: Investigação

1. Execute os testes para verificar que eles passam:
   ```bash
   ./mvnw clean verify
   ```

2. Investigue o código para entender por que:
   - Os testes passam ✅
   - A aplicação não inicia ❌

### Parte 3: Documentação

Crie uma **Issue no GitHub** documentando o bug usando o template disponível em:
- `.github/ISSUE_TEMPLATE/bug_report.md`

Sua issue deve conter:

- ✅ **Título descritivo** - Deve deixar claro qual é o problema
- ✅ **Descrição detalhada** - Explique o bug com suas palavras
- ✅ **Passos para reproduzir** - Qualquer pessoa deve conseguir reproduzir seguindo seus passos
- ✅ **Logs de erro** - Cole o stack trace completo
- ✅ **Comportamento esperado vs. atual** - Deixe claro a diferença
- ✅ **Screenshots** (opcional) - Se ajudar no entendimento

## 📊 Critérios de Avaliação

Sua issue será avaliada pelos seguintes critérios:

| Critério | Peso | Descrição |
|----------|------|-----------|
| Clareza do título | 10% | O título descreve bem o problema? |
| Descrição detalhada | 20% | A descrição é completa e fácil de entender? |
| Passos para reproduzir | 20% | Qualquer pessoa consegue reproduzir o bug? |
| Logs e evidências | 25% | Os logs foram incluídos e são relevantes? |
| Análise técnica | 15% | Demonstrou entendimento da causa raiz? |
| Formatação | 10% | Usou corretamente o template e markdown? |

## 💡 Dicas

1. **Leia os logs com atenção** - O stack trace indica exatamente onde está o problema
2. **Entenda o ciclo de vida Spring** - Saiba quando cada método é chamado
3. **Pense nos testes** - Por que os testes não capturaram este bug?
4. **Seja profissional** - Escreva como se estivesse reportando um bug em um projeto real

## 🚀 Entrega

Após criar a issue, envie o link dela para o professor.

**Formato do título da issue:**
```
[BUG] Aplicação falha ao iniciar - StudentService
```

## 📚 Aprendizados Esperados

Ao final desta atividade, você será capaz de:

- ✅ Identificar bugs de inicialização em aplicações Spring Boot
- ✅ Analisar logs e stack traces para encontrar a causa raiz
- ✅ Entender a diferença entre testes unitários e comportamento em runtime
- ✅ Documentar bugs de forma profissional
- ✅ Usar GitHub Issues para rastreamento de problemas
