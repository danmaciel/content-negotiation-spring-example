# Produto API

API REST MVC para demonstração de Content Negotiation com Spring Boot.

## Tecnologias

- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Maven
- Jakarta Validation
- Jackson Dataformat (JSON, XML, YAML)

## Como Executar

```bash
mvn spring-boot:run
```

A aplicação estará disponível em http://localhost:8080

## Swagger com Content Negotiation

### Acessar o Swagger UI

Acesse a interface do Swagger em: http://localhost:8080/swagger-ui.html

### Como Usar Content Negotiation no Swagger

O Swagger UI agora suporta **três formatos de resposta** para cada endpoint:

1. **JSON** (padrão) - `application/json`
2. **XML** - `application/xml`
3. **YAML** - `application/x-yaml`

### Passo a Passo para Testar Diferentes Formatos

#### 1. Listar Produtos em Diferentes Formatos

**Na interface do Swagger:**
1. Expanda o endpoint `GET /api/produtos`
2. Clique em "Try it out"
3. No campo "Response content type", selecione o formato desejado:
   - `application/json` para JSON
   - `application/xml` para XML
   - `application/x-yaml` para YAML
4. Clique em "Execute"
5. Observe a resposta no formato selecionado

#### 2. Criar Produto em Diferentes Formatos

**Na interface do Swagger:**
1. Expanda o endpoint `POST /api/produtos`
2. Clique em "Try it out"
3. No campo "Request body", insira o produto no formato desejado:

**JSON (padrão):**
```json
{
  "nome": "Produto Exemplo",
  "descricao": "Descrição do produto",
  "preco": 99.99,
  "quantidadeEstoque": 10
}
```

**XML:**
```xml
<ProdutoDTO>
  <nome>Produto Exemplo</nome>
  <descricao>Descrição do produto</descricao>
  <preco>99.99</preco>
  <quantidadeEstoque>10</quantidadeEstoque>
</ProdutoDTO>
```

**YAML:**
```yaml
nome: Produto Exemplo
descricao: Descrição do produto
preco: 99.99
quantidadeEstoque: 10
```

4. No campo "Response content type", selecione o formato desejado
5. Clique em "Execute"
6. Observe a resposta no formato selecionado

#### 3. Alternar Entre Formatos Rapidamente

Para testar o mesmo endpoint em diferentes formatos:
1. Execute uma vez com `application/json`
2. Mude o "Response content type" para `application/xml`
3. Execute novamente
4. Mude para `application/x-yaml`
5. Execute novamente

### OpenAPI Specification em Diferentes Formatos

Além do Swagger UI, você pode acessar a especificação OpenAPI em:

- **JSON:** http://localhost:8080/v3/api-docs
- **YAML:** http://localhost:8080/v3/api-docs.yaml

### Nota Importante

O Content Negotiation funciona **sem anotações adicionais nos Controllers**. O `OpenApiCustomizer` configurado em `OpenApiConfig.java` adiciona automaticamente os três media types (JSON, XML, YAML) a todas as respostas da API.

## Content Negotiation

Este projeto demonstra como configurar o Spring Boot para aceitar e retornar dados em diferentes formatos (JSON, XML, YAML) usando apenas headers HTTP.

### Estratégia

- **JSON:** Padrão do Spring Boot (sem configuração adicional)
- **XML:** Suportado via dependência `jackson-dataformat-xml`
- **YAML:** Suportado via dependência `jackson-dataformat-yaml` + configuração customizada

### Headers Utilizados

| Header | Descrição | Quando Usar |
|--------|-----------|-------------|
| `Content-Type` | Define o formato do **corpo da requisição** | POST, PUT |
| `Accept` | Define o formato **esperado na resposta** | GET, POST, PUT |

### Formatos Suportados

| Formato | Media Type | Exemplo |
|---------|------------|---------|
| JSON | `application/json` | Padrão |
| XML | `application/xml` | Via Jackson |
| YAML | `application/x-yaml` | Via Jackson + Config |

### Exemplos de Uso

#### GET - Listar Produtos

**JSON (padrão):**
```bash
curl http://localhost:8080/api/produtos
```

**XML:**
```bash
curl -H "Accept: application/xml" http://localhost:8080/api/produtos
```

**YAML:**
```bash
curl -H "Accept: application/x-yaml" http://localhost:8080/api/produtos
```

#### POST - Criar Produto

**JSON:**
```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "nome": "Produto Exemplo",
    "descricao": "Descrição do produto",
    "preco": 99.99,
    "quantidadeEstoque": 10
  }'
```

**XML:**
```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/xml" \
  -H "Accept: application/xml" \
  -d '<ProdutoDTO>
        <nome>Produto Exemplo</nome>
        <descricao>Descrição do produto</descricao>
        <preco>99.99</preco>
        <quantidadeEstoque>10</quantidadeEstoque>
      </ProdutoDTO>'
```

**YAML:**
```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/x-yaml" \
  -H "Accept: application/x-yaml" \
  -d 'nome: Produto Exemplo
descricao: Descrição do produto
preco: 99.99
quantidadeEstoque: 10'
```

#### PUT - Atualizar Produto

**YAML:**
```bash
curl -X PUT http://localhost:8080/api/produtos/1 \
  -H "Content-Type: application/x-yaml" \
  -H "Accept: application/x-yaml" \
  -d 'id: 1
nome: Produto Atualizado
descricao: Descrição atualizada
preco: 199.99
quantidadeEstoque: 20'
```

#### DELETE - Deletar Produto

```bash
curl -X DELETE http://localhost:8080/api/produtos/1
```
*DELETE sempre retorna 204 No Content (sem corpo)*

#### GET - Buscar por ID

**YAML:**
```bash
curl -H "Accept: application/x-yaml" http://localhost:8080/api/produtos/1
```

### Exemplos de Resposta

**JSON:**
```json
{
  "id": 1,
  "nome": "Produto Exemplo",
  "descricao": "Descrição do produto",
  "preco": 99.99,
  "quantidadeEstoque": 10
}
```

**XML:**
```xml
<ProdutoDTO>
  <id>1</id>
  <nome>Produto Exemplo</nome>
  <descricao>Descrição do produto</descricao>
  <preco>99.99</preco>
  <quantidadeEstoque>10</quantidadeEstoque>
</ProdutoDTO>
```

**YAML:**
```yaml
id: 1
nome: "Produto Exemplo"
descricao: "Descrição do produto"
preco: 99.99
quantidadeEstoque: 10
```

## Configuração

### Dependências (pom.xml)
- `spring-boot-starter-web` - Suporte nativo a JSON
- `jackson-dataformat-xml` - Suporte a XML
- `jackson-dataformat-yaml` - Suporte a YAML

### Configuração Customizada
A classe `ContentNegotiationConfig` configura:
- Media types suportados (json, xml, yaml)
- Strategy baseada em headers (Content-Type e Accept)
- Conversor customizado para YAML com `YAMLFactory`

## Endpoints

- `POST /api/produtos` - Criar produto
- `GET /api/produtos` - Listar todos os produtos
- `GET /api/produtos/{id}` - Buscar produto por ID
- `PUT /api/produtos/{id}` - Atualizar produto
- `DELETE /api/produtos/{id}` - Deletar produto
- `GET /api/produtos/count` - Contar registros cadastrados

## Coleção do Postman

Uma coleção do Postman completa está disponível no arquivo `postman-collection.json`.

### Como Importar

1. Abra o Postman
2. Clique em **Import** no canto superior esquerdo
3. Selecione o arquivo `postman-collection.json` do projeto
4. A coleção "Produto API - Content Negotiation" será importada

### Estrutura da Coleção

A coleção está organizada em pastas por endpoint:

#### 📁 Listar Todos
- `Listar Todos (JSON)` - Resposta em JSON (padrão)
- `Listar Todos (XML)` - Resposta em XML
- `Listar Todos (YAML)` - Resposta em YAML

#### 📁 Criar Produto
- `Criar Produto (JSON)` - Envio e resposta em JSON
- `Criar Produto (XML)` - Envio e resposta em XML
- `Criar Produto (YAML)` - Envio e resposta em YAML

#### 📁 Buscar por ID
- `Buscar por ID 1 (YAML)` - Resposta em YAML (ajuste o ID conforme necessário)

#### 📁 Atualizar Produto
- `Atualizar Produto 1 (JSON)` - Envio e resposta em JSON
- `Atualizar Produto 1 (XML)` - Envio e resposta em XML
- `Atualizar Produto 1 (YAML)` - Envio e resposta em YAML

#### 📁 Deletar Produto
- `Deletar Produto 1` - Deleta o produto (retorna 204)

#### 📁 Contar Registros
- `Contar Registros` - Retorna quantidade de produtos

### Variáveis de Ambiente

A coleção usa as seguintes variáveis:

| Variável | Valor Padrão | Descrição |
|----------|--------------|-----------|
| `baseUrl` | `http://localhost:8080` | URL base da API |
| `apiPath` | `/api/produtos` | Caminho da API |

Você pode alterar essas variáveis no Postman:
1. Clique no ícone de olho no canto superior direito
2. Em "Environments", crie um novo ou edite o atual
3. Altere os valores conforme necessário

### Dicas de Uso

1. **Primeiro, crie um produto** - Execute um dos requests "Criar Produto"
2. **Copie o ID** - Observe o ID retornado na resposta
3. **Atualize os requests** - Substitua `1` pelo ID atual em "Buscar por ID" e "Atualizar Produto"
4. **Experimente os formatos** - Teste JSON, XML e YAML para ver como o Content Negotiation funciona

### Exemplo Visual

**Request YAML no Postman:**
```
POST http://localhost:8080/api/produtos

Headers:
Content-Type: application/x-yaml
Accept: application/x-yaml

Body (raw):
nome: Produto Exemplo
descricao: Descrição do produto
preco: 99.99
quantidadeEstoque: 10
```

**Resposta YAML:**
```yaml
id: 1
nome: "Produto Exemplo"
descricao: "Descrição do produto"
preco: 99.99
quantidadeEstoque: 10
```
