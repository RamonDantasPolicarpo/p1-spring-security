# P1 Spring Security 🛡️

## Objetivo do Sistema
Este é um projeto de estudo focado na implementação de um sistema de autenticação e autorização para APIs REST. O objetivo principal é consolidar na prática os conceitos de segurança no back-end, garantindo que apenas usuários autenticados possam acessar rotas protegidas através de uma abordagem stateless.

O projeto permite a criação de contas de usuário (com criptografia de senhas) e a emissão de tokens de acesso para consumo de recursos restritos da API.

## Tecnologias Utilizadas
* **Java 21**: Linguagem principal do projeto.
* **Spring Boot**: Framework base para a construção da API.
    * **Spring Web**: Para o desenvolvimento e roteamento da API REST.
    * **Spring Security**: Para a camada de segurança, controle de acesso e filtros de requisição customizados.
    * **Spring Data JPA**: Para a persistência de dados e mapeamento objeto-relacional (ORM).
* **JWT (Auth0)**: Implementação para a geração, assinatura (HMAC256) e validação de JSON Web Tokens.
* **PostgreSQL**: Banco de dados relacional configurado para o armazenamento das credenciais e dados dos usuários.
* **Flyway**: Ferramenta de versionamento e migração de banco de dados (responsável pela criação automática da tabela `users`).
* **Lombok**: Para redução de boilerplate e simplificação da escrita de classes de modelo e DTOs.

## Estrutura e Endpoints

A aplicação expõe os seguintes endpoints:

### Autenticação (Acesso Público)
* `POST /auth/register`: Registra um novo usuário no sistema. A senha recebida no corpo da requisição é criptografada utilizando o algoritmo BCrypt (`BCryptPasswordEncoder`) antes de ser persistida.
* `POST /auth/login`: Autentica o usuário validando o e-mail e a senha informados. Se as credenciais estiverem corretas, o sistema retorna um token JWT configurado com um tempo de expiração de 1 hora.

### Teste de Segurança (Acesso Protegido)
* `GET /test`: Rota restrita. Requer o envio de um token JWT válido no cabeçalho da requisição (`Authorization: Bearer <token>`). O `SecurityFilter` intercepta a chamada, extrai os dados (como o `userId` e `email`) e libera o acesso caso o token seja validado.

## Como Executar o Projeto

1. **Pré-requisitos**: É necessário ter o Java 21 e o PostgreSQL rodando no seu ambiente.
2. **Banco de Dados**: Crie um banco de dados no PostgreSQL chamado `seguranca`.
3. **Configuração de Credenciais**: No arquivo `src/main/resources/application.properties`, a aplicação espera a senha do banco de dados na variável de ambiente `POSTGRES_PASSWORD` (ou utiliza um valor padrão caso não seja fornecida).
4. **Execução**:
   Na raiz do projeto, utilize o Maven Wrapper para rodar a aplicação e disparar as migrations do Flyway automaticamente: