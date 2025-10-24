## ProjetoFinalAPI-Serratec.2025.2


<div align="center">
  <h1>Grupo 02</h1>
</div>

    
# 🛍️ API RESTful E-commerce Varejista

## 📋 Descrição do Projeto

Este projeto consiste na construção de um sistema de vendas online para uma empresa varejista, implementado como uma API RESTful robusta usando o framework Spring Boot. O sistema é estruturado em um Padrão em Camadas e utiliza princípios de Domain-Driven Design (DDD), atendendo a requisitos de segurança, persistência complexa (relações N:N), validações de negócio e integração com serviços externos (ViaCEP, E-mail).

---

## 🚀 Tecnologias e Stack Principal

| Categoria        | Tecnologia                                      |
|------------------|--------------------------------------------------|
| Framework        | Spring Boot 3+                                   |
| Persistência     | Spring Data JPA / Hibernate                      |
| Banco de Dados   | PostgreSQL (Recomendado) / Postman (Para Testes)      |
| Segurança        | Spring Security / JWT (JSON Web Tokens)          |
| Comunicação REST | RESTful Web Services / HTTP                      |
| Integrações      | RestTemplate/WebClient (ViaCEP, E-mail Assíncrono) |
| Documentação     | OpenAPI/Swagger                                  |
| Linguagem        | Java 17+                                         |

---

## 🏛️ Arquitetura e Padrões

O projeto segue uma arquitetura em camadas para organizar as responsabilidades:

📦 org.serratec.ecommerce 

├── 📦 config      → Configurações gerais da aplicação (segurança, e-mail, Swagger).  
├── 📦 controller  → Recebe requisições HTTP e expõe os endpoints da API.  
├── 📦 dto         → Objetos de transferência de dados.  
├── 📦 entity      → Modelos de dados mapeados para o banco via JPA.  
├── 📦 exception   → Tratamento de erros e exceções personalizadas.  
├── 📦 repository  → Acesso aos dados com Spring Data JPA.  
├── 📦 security    → Autenticação e autorização com JWT e Spring Security.  
├── 📦 service     → Lógica de negócios e orquestração entre camada.  

---

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
| Pedido        | DELETE | `/pedidos/{id}/status`     | Altera o status do pedido                      | 200, 400, 404      |

---
### Exemplos de Requisições




## ⚙️ Como Rodar o Projeto

### Pré-requisitos

- JDK 17+
- Apache Maven 3.8 ou superior
- PostgreSQL instalado e em execução
- Cliente de API como Postman ou Insomnia (opcional)

 ### 1- Clone o repositório:
```
Bash
git clone https://github.com/seu-usuario/serratec-music-api.git
cd serratec-music-api
```

### 2- Configuração do Banco

1. Crie um banco PostgreSQL.
2. Atualize o `application.properties` ou `application.yml`:

## Properties
```
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco_de_dados
spring.datasource.username=postegres
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
```
## Compilação
```
Bash
mvn clean install
```
## Execução
```
Bash
mvn spring-boot:run
```

## Documentação 
-	API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui/index.html

 ---
## 👨‍💻 Autores

🔐 **Bruno Ireno do Nascimento** – Segurança e Autenticação  
📦 **Diana Souza Ribeiro** – Catálogo de Produtos e documentação do projeto (README)  
📬 **Alexandre Lício da Silva Morais** – Cliente e Integrações  
🧮 **Joao Pedro Breves Massambani de Souza** – Pedidos e Transações  
⚙️ **Enzo** – Lógica de Negócio e Configurações

🎓 Residentes do Serratec 2025.2  
💻 Desenvolvedores Full Stack



 


