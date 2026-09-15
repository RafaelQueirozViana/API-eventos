# Sistema de Gerenciamento de Eventos e Inscrições

Este sistema em Spring Boot gerencia eventos, participantes e inscrições com controle de vagas limitadas.

## Tecnologias
* Java
* Spring Boot (Web e Data JPA)
* Banco de Dados H2 / PostgreSQL / MySQL
* Maven

## Fluxo de Git
Um dos requisitos obrigatórios para essa atividade foi a proibição de commits realizados diretamente na branch main. Nesse cenário, a integração aconteceu através de Pull Requests:
1. git checkout -b feature/nome-da-funcionalidade
2. git commit -m "Descricao do ajuste"
3. git push origin feature/nome-da-funcionalidade
4. Abra o Pull Request na main

## Como Executar
1. Clone o repositório: git clone <url-do-repositorio>
2. Entre na pasta do projeto pelo IntelliJ
3. Rode o arquivo: src/main/java/com/eventos/EventosApplication.java
4. Acesse em: http://localhost:8080/swagger-ui/index.html#/

## Diagrama (DER)
O Diagrama Entidade-Relacionamento está localizado na raiz do projeto como der.png ou der.pdf.

## Endpoints da API

### Eventos
* POST /api/eventos - Cadastra um evento
* GET /api/eventos - Lista todos os eventos
* GET /api/eventos/{id} - Detalha o evento e exibe as vagas restantes

### Participantes
* POST /api/participantes - Cadastra um participante

### Inscrições
* POST /api/inscricoes - Inscreve participante em um evento
* GET /api/eventos/{id}/participantes - Lista participantes de um evento
* DELETE /api/inscricoes/{id} - Cancela inscrição e libera a vaga

## Regras de Negócio
* RN01: Inscrição permitida apenas se houver vaga. Retorna HTTP 400 se lotado.
* RN02: Proibido inscrever o mesmo participante duas vezes no mesmo evento.
* RN03: Cada participante deve ter um e-mail único.
* RN04: O cancelamento da inscrição libera a vaga imediatamente.
