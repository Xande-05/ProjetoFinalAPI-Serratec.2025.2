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
## 📊 Diagrama do Banco de Dados

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

## 1. Login
```http

 "email": "grupo2@gmail.com", // Ou outro email cadastrado
 "senha": "123456" // Ou a senha correspondente

```


   <img width="933" height="483" alt="login" src="https://github.com/user-attachments/assets/bb064dad-ea25-47d6-9bde-7e3c173ecec7" /> 

   
--------------

## 2. Criar um cliente

   
```http
POST /clientes
   {
    "nome": "João Teste",
    "email": "joao.teste@email.com",
    "telefone": "21999998888",
    "cpf": "12345678901",
    "cep": "25665290" // Exemplo de CEP, use um válido
}
```

<img width="928" height="606" alt="postcliente" src="https://github.com/user-attachments/assets/b003b73d-f2db-4ed0-a9c4-1000d0dbe090" />

---------------------------

## 3. Atualizar

      
```http
PUT /clientes
{
    "nome": "João Teste Atualizado",
    "email": "joao.teste.upd@email.com", // Email pode ser atualizado
    "telefone": "21999997777",
    "cpf": "12345678901", // CPF geralmente não é atualizável, mas DTO permite
    "cep": "20000000" // Novo CEP válido
}
```

<img width="932" height="611" alt="putcliente" src="https://github.com/user-attachments/assets/44ade682-6ba6-491d-bfcb-62b3655908a7" />


-----------------------------

## 4. Deletar

![deletecliente](https://github.com/user-attachments/assets/aafba931-85fd-49e4-a489-8127e702d0fd)


----------------------

## 5. Email de confirmação de cadatsro

<img width="1036" height="467" alt="image cadatsrado" src="https://github.com/user-attachments/assets/77f81870-30e2-4684-a540-0b96432cf58d" />

--------------------------

## 6. Email de confirmação de atualização de cadastro

<img width="557" height="218" alt="image cadastro" src="https://github.com/user-attachments/assets/da9738bb-b084-4b3c-b158-51e7e1154359" />

------------------------

## 7. Email de confirmação de desconto

<img width="1034" height="305" alt="image email desconto" src="https://github.com/user-attachments/assets/7b2ca25a-c1cb-4606-9490-80240520e2b2" />

---


## ⚙️ Como Rodar o Projeto

### Pré-requisitos

- JDK 17+
- Apache Maven 3.8 ou superior
- PostgreSQL instalado e em execução
- Cliente de API como Postman ou Insomnia (opcional)

 ### 1- Clone o repositório:
```
Bash
https://github.com/Xande-05/ProjetoFinalAPI-Serratec.2025.2.git
```

### 2- Configuração do Banco

1. Crie um banco PostgreSQL.
2. Atualize o `application.properties` ou `application.yml`:

## Properties
```
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_api
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


<img width="1842" height="966" alt="Captura de tela 2025-10-28 203248" src="https://github.com/user-attachments/assets/b2d486ba-3839-4f6e-8476-a85553349ba6" />

 ---
## 👨‍💻 Autores

- 🔐 **Bruno Ireno do Nascimento** 
- 📦 **Diana Souza Ribeiro**
- 📬 **Alexandre Lício da Silva Morais** 
- 🧮 **Joao Pedro Breves Massambani de Souza** 
- ⚙️ **Enzo Converso** 
  

🎓 **Residentes do Serratec 2025.2**  
💻 **Desenvolvedores Full Stack**






 


