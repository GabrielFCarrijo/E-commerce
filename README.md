<p align="right">
  <img src="https://github.com/user-attachments/assets/b16bcdb3-b27d-4cf5-ba55-788cf9793977" alt="Amazun Logo" width="120">
</p>

# 📦 Sistema de Gerenciamento de pedidos (Amazun)
Este projeto é uma API RESTful desenvolvida em **Java com Spring Boot**, que permite o **cadastro de usuários**, **gerenciamento de produtos**, **realização de pedidos**, **pagamentos** e **dashboard analítico** com metricas de vendas

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- Gradle 
- Swagger 
- JUnit 5 e Mockito

---

## Autenticação

O projeto usa **JWT** para autenticação. Após o login, utilize o token nos endpoints protegidos via header:

```http
Authorization: Bearer COLAR_TOKEN_AQUI
```
---

## Executando Localmente
 - Todo o projeto foi construido com docker entao quando executar ele já ira criar toda a estrutura das querys necessarias, um ponto muito importante deixei a toogle para sempre que subir o projeto localmente ele recriar todas as tabelas
   então caso queira que ele apenas suba sem regerar basta colocar a toogle em update na properties do projeto igual abaixo:
<img width="339" height="35" alt="image" src="https://github.com/user-attachments/assets/7eecb5aa-ff2e-448e-958c-9873ab6c9fc6" />

- Antes de acessar os endpoints protegidos, é necessário criar usuários no sistema. Esses usuários servirão como base para autenticação e autorizacao
  Todos os endpoints (exceto o de registro e login) exigem um token JWT com uma role válidas sem esse token de acesso, o sistema bloqueará qualquer requisição aos recursos protegidos

- Caso esteja rodando no IntelliJ, , clique nas configurações do gradlew para ele pegar a versão do java setada no IntelliJ, como padrao para evitar erros de versao como abaixo:
<img width="1093" height="789" alt="image" src="https://github.com/user-attachments/assets/99e2fb1c-102a-4bd8-89d9-e71e004fad61" />

---

## Endpoints
- Todos os endipoints e seus exemplos se encontram no Swegger da aplicação, para acessar o swegger basta acessar o link abaixo após executar a aplicação:
  [Swegger](http://localhost:8080/swagger-ui/index.html)

## Testes
- A fim de testar a logica da aplicação criei os testes unitarios das services então para executar basta rodar:
  ```

   ./gradlew test

  ```
