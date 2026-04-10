# Concessionaria - API Backend

API desenvolvida em Java com Spring Boot para gerenciamento de um sistema de concessionaria (veiculos alojados), permitindo operações de cadastro, consulta, atualização e remoção de dados.

---

## Tecnologias utilizadas

- Java 17
- Maven
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Lombok

---

## Funcionalidades

- Cadastro de veiculos
- Listagem de veiculos
- Atualização de informações
- Remoção de registros
- Regras de negócio aplicadas no backend
- Placas de veiculos unicas

---

## ⚙️ Como executar o projeto

### 1. Clonar o repositório

git clone https://github.com/Thomas-Teo/backend.git

### 2. Acessar o diretório

cd backend

### 3. Configurar o banco de dados

Edite o arquivo application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco
spring.datasource.username=root
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

### 4. Rodar a aplicação

./mvnw spring-boot:run

ou

mvn spring-boot:run

## Exemplo de endpoints disponíveis na API:

- GET     /veiculos
- GET     /veiculos/{id}
- GET     /veiculos?marca=&modelo=&ano=&valorMin=&valorMax=&placa=
- POST    /veiculos
- PUT     //veiculos/{id}
- DELETE  /veiculos/{id}


## Estrutura do projeto

src/main/java/com/backend <br>
├── controller<br>
├── DTO<br>
├── entity<br>
├── exception<br>
├── mapper<br>
├── repository<br>
└── service<br>

## Observações

Projeto desenvolvido para fins de teste tecnico
Backend focado em arquitetura REST
Pode ser integrado com frontend Angular/React

👨‍💻 Autor

Thomas

[GitHub](https://github.com/Thomas-Teo)