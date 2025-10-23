# ProjetoFinalAPI-Serratec.2025.2
Grupo 2

# 🛍️ API RESTful E-commerce Varejista

## 📋 Descrição do Projeto

Este projeto consiste na construção de um sistema de vendas online para uma empresa varejista, implementado como uma API RESTful robusta usando o framework Spring Boot. O sistema é estruturado em um Padrão em Camadas e utiliza princípios de Domain-Driven Design (DDD), atendendo a requisitos de segurança, persistência complexa (relações N:N), validações de negócio e integração com serviços externos (ViaCEP, E-mail).

---

## 🚀 Tecnologias e Stack Principal

| Categoria        | Tecnologia                                      |
|------------------|--------------------------------------------------|
| Framework        | Spring Boot 3+                                   |
| Persistência     | Spring Data JPA / Hibernate                      |
| Banco de Dados   | PostgreSQL (Recomendado) / H2 (Para Testes)      |
| Segurança        | Spring Security / JWT (JSON Web Tokens)          |
| Comunicação REST | RESTful Web Services / HTTP                      |
| Integrações      | RestTemplate/WebClient (ViaCEP, E-mail Assíncrono) |
| Documentação     | OpenAPI/Swagger                                  |
| Linguagem        | Java 17+                                         |

---

## 🏛️ Arquitetura e Padrões

O projeto segue rigorosamente o Padrão em Camadas (Layers):

- **Controller (Recursos):** Interface HTTP, mapeia URIs e retorna DTOs.
- **Service (Serviços):** Lógica de negócios e transações (@Transactional).
- **Repository (Repositórios):** Acesso a dados via Spring Data JPA.
- **Entity (Entidades):** Modelo de dados mapeado com JPA.

**Padrões Aplicados:**

- DTOs para desacoplamento entre entidades e API.
- Tratamento global de exceções via `@ControllerAdvice`.
- Assincronicidade com `@Async` para envio de e-mails.

---

## 🔑 Endpoints Principais (RESTful CRUD)

| Domínio       | Método | URI                        | Descrição                                      | Status Codes       |
|---------------|--------|----------------------------|------------------------------------------------|--------------------|
| Autenticação  | POST   | `/auth/login`              | Gera o token JWT para acesso                   | 200, 403           |
| Catálogo      | POST   | `/categorias`              | Cria nova categoria                            | 201, 400           |
| Catálogo      | POST   | `/produtos`                | Cria novo produto (requer `categoriaId`)       | 201, 404, 400      |
| Catálogo      | GET    | `/produtos`                | Lista todos os produtos com suas categorias    | 200                |
| Cliente       | POST   | `/clientes`                | Cadastra cliente e aciona ViaCEP + E-mail      | 201, 400           |
| Pedido        | POST   | `/pedidos`                 | Cria novo pedido (requer autenticação)         | 201, 404, 400      |
| Pedido        | GET    | `/pedidos/{id}`            | Busca pedido por ID com valor total            | 200, 404           |
| Pedido        | PUT    | `/pedidos/{id}/status`     | Altera o status do pedido                      | 200, 400, 404      |

---

## ⚙️ Como Rodar o Projeto

### Pré-requisitos

- JDK 17+
- Maven ou Gradle

### Configuração do Banco

1. Crie um banco PostgreSQL.
2. Atualize o `application.properties` ou `application.yml`:


properties
```http
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco_de_dados
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```



