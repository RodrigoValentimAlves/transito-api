# transito-api

API simples para gerenciamento de proprietários, veículos e autuações (Projeto de estudo).

## Visão geral
Projeto Spring Boot (Java 17, Maven) que expõe endpoints REST para cadastrar e consultar proprietários, veículos e autuações. Usa MySQL como banco e Flyway para migrações.

## Pré-requisitos
- Java 17
- Maven 3.6+
- MySQL (local ou remoto)

## Configuração do banco
As configurações padrão estão em `src/main/resources/application.properties`.

IMPORTANTE: Não comite credenciais (usuários/senhas) no repositório público.
Este projeto suporta o uso de variáveis de ambiente para configurar a conexão — isso evita expor credenciais no código.

Variáveis suportadas (substituem valores no `application.properties`):
- `DB_URL` — JDBC URL (ex.: jdbc:mysql://localhost/transito?createDatabaseIfNotExist=true&serverTimezone=UTC)
- `DB_USERNAME` — usuário do banco
- `DB_PASSWORD` — senha do banco

Exemplo: no PowerShell (Windows)

```powershell
$env:DB_URL = 'jdbc:mysql://localhost/transito?createDatabaseIfNotExist=true&serverTimezone=UTC'
$env:DB_USERNAME = 'root'
$env:DB_PASSWORD = 'mysecret'
mvn spring-boot:run
```

Também incluí `src/main/resources/application.properties.example` — copie e preencha este arquivo localmente se preferir não usar variáveis de ambiente:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
# editar src/main/resources/application.properties com credenciais locais
```

O projeto usa Flyway para aplicar as migrações encontradas em `src/main/resources/db/migration` (ex.: cria as tabelas `proprietario`, `veiculo` e `autuacao`).

Observação: a migration `V4__cria-tabela-autuacao.sql` cria a coluna `valor_multa`, por isso a entidade `Autuacao` mapeia este nome de coluna.

## Como compilar
No diretório raiz do projeto execute:

```bash
mvn clean package
```

Ou para rodar diretamente com o plugin do Spring Boot:

```bash
mvn spring-boot:run
```

## Endpoints principais
Os controllers presentes no projeto expõem os seguintes recursos (base URL: `http://localhost:8080`):

- `GET /proprietarios` — lista todos os proprietários
- `GET /proprietarios/{id}` — busca proprietário por id
- `POST /proprietarios` — cria um proprietário
  - Exemplo JSON:
    ```json
    {
      "nome": "Josefa",
      "email": "josefa@example.com",
      "telefone": "123456789"
    }
    ```
- `PUT /proprietarios/{id}` — atualiza um proprietário
- `DELETE /proprietarios/{id}` — remove um proprietário

- `GET /veiculos` — lista todos os veículos
- `GET /veiculos/{id}` — busca veículo por id
- `POST /veiculos` — cadastra veículo
  - Exemplo JSON (observe que o `proprietario` deve ter `id`):
    ```json
    {
      "proprietario": { "id": 1 },
      "marca": "Fiat",
      "modelo": "Uno",
      "placa": "ABC1D23"
    }
    ```

- `POST /autuacoes` — registrar uma autuação (controller/service presente no projeto)
  - Exemplo JSON (campo `valor` é mapeado para a coluna `valor_multa`):
    ```json
    {
      "veiculo": { "id": 1 },
      "descricao": "Estacionamento irregular",
      "valor": 150.00,
      "dataOcorrencia": "2026-03-11T10:15:30Z"
    }
    ```

> Nota: Validações JSR-380 são aplicadas (ex.: campos obrigatórios). Use `Content-Type: application/json`.

## Logs e depuração
- Para ver as queries SQL do Hibernate, adicione no `application.properties`:

```
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

## Testes
O projeto inclui testes de contexto. Rode com:

```bash
mvn test
```

## Problemas comuns
- Erro "Unknown column 'valor' in 'field list'": significa que o nome da coluna no DB difere do nome do campo da entidade; a migration cria `valor_multa`, por isso a entidade deve usar `@Column(name = "valor_multa")` no campo `valor`.
- Erro de desserialização JSON ao enviar apenas um valor string para um endpoint que espera um objeto (ex.: enviar `"Josefa"` em vez de `{ "nome": "Josefa" }`).

## Estrutura relevante
- `src/main/java/.../api/controller` — controllers REST
- `src/main/java/.../domain/model` — entidades JPA
- `src/main/resources/db/migration` — migrações Flyway
