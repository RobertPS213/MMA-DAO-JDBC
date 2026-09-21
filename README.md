<div align="center">

# 🥊 MMA DAO JDBC

### Sistema de gerenciamento de lutadores e categorias de peso

![Java](https://img.shields.io/badge/Java-JDBC-orange?logo=openjdk&logoColor=white)
![Database](https://img.shields.io/badge/MySQL%20%2F%20MariaDB-Database-4479A1?logo=mysql&logoColor=white)
![Architecture](https://img.shields.io/badge/Architecture-DAO-6C63FF)
![Interface](https://img.shields.io/badge/Interface-CLI-222222?logo=gnubash&logoColor=white)

Aplicação Java de linha de comando para cadastro e gerenciamento de lutadores de MMA, suas estatísticas e respectivas categorias de peso.

</div>

Projeto desenvolvido para estudo e prática de **orientação a objetos**, **padrão DAO**, **JDBC** e persistência em banco de dados relacional.

## 📑 Índice

- [Sobre o projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Modelo de dados](#modelo-de-dados)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do banco](#configuração-do-banco)
- [Como executar](#como-executar)
- [Utilização](#utilização)

## Sobre o projeto

O **MMA DAO JDBC** é uma aplicação de terminal que permite administrar um pequeno cadastro de lutadores e categorias de peso. O sistema utiliza uma arquitetura baseada em interfaces DAO e implementações JDBC, mantendo a lógica de acesso ao banco separada da interação com o usuário.

```text
Usuário no terminal → Program.java → DaoFactory → DAOs JDBC → MySQL / MariaDB
```

## Funcionalidades

### 🥋 Gerenciamento de lutadores

- cadastro, consulta por ID, listagem e exclusão;
- atualização de dados cadastrais e estatísticas;
- filtro de lutadores por categoria de peso;
- armazenamento de peso, vitórias, derrotas e empates.

### 🏆 Gerenciamento de categorias

- cadastro, consulta por ID e listagem;
- atualização do nome da categoria;
- exclusão de categoria pelo ID.

### 🗄️ Persistência e acesso a dados

- implementação do padrão **DAO (Data Access Object)**;
- uso de `PreparedStatement` nas operações SQL;
- recuperação de IDs gerados automaticamente;
- relacionamento por chave estrangeira;
- consultas com `INNER JOIN` para carregar a categoria do lutador;
- tratamento de exceções específicas de banco de dados;
- fechamento de `Statement` e `ResultSet` após as operações.

## Tecnologias

- **Java**;
- **JDBC**;
- **MySQL ou MariaDB**;
- **MySQL Connector/J** ou driver JDBC equivalente;
- **SQL**;
- **Git**.

> O projeto não utiliza Maven ou Gradle. O driver JDBC deve ser adicionado manualmente ao classpath durante a compilação e a execução.

## Arquitetura

```text
src/
├── application/
│   └── Program.java              # Menu e interação com o usuário
├── db/
│   ├── DB.java                   # Conexão e recursos JDBC
│   ├── DbException.java          # Exceção geral de banco
│   └── DbIntegrityException.java # Exceção de integridade
└── model/
    ├── entities/
    │   ├── Categoria.java        # Entidade de categoria
    │   └── Lutador.java          # Entidade de lutador
    └── dao/
        ├── CategoriaDao.java     # Contrato de categorias
        ├── LutadorDao.java       # Contrato de lutadores
        ├── DaoFactory.java       # Fábrica de DAOs
        └── impl/
            ├── CategoriaDaoJDBC.java
            └── LutadorDaoJDBC.java

bd.sql                            # Estrutura e carga inicial
```

| Camada | Responsabilidade |
|---|---|
| `application` | Apresentar o menu e receber dados do usuário |
| `entities` | Representar `Categoria` e `Lutador` |
| `dao` | Definir os contratos de persistência |
| `dao.impl` | Executar operações SQL via JDBC |
| `db` | Gerenciar conexão, recursos e exceções |

## Modelo de dados

O script [`bd.sql`](bd.sql) cria o relacionamento `categoria (1) ─── (N) lutador`.

### `categoria`

| Coluna | Tipo | Regra |
|---|---|---|
| `Id` | `INT` | Chave primária e auto incremento |
| `Nome` | `VARCHAR(100)` | Obrigatório |

### `lutador`

| Coluna | Tipo | Regra |
|---|---|---|
| `Id` | `INT` | Chave primária e auto incremento |
| `Nome` | `VARCHAR(100)` | Obrigatório |
| `Peso` | `DOUBLE` | Obrigatório |
| `Vitorias` | `INT` | Padrão `0` |
| `Derrotas` | `INT` | Padrão `0` |
| `Empates` | `INT` | Padrão `0` |
| `CategoriaId` | `INT` | Obrigatório e chave estrangeira |

O script fornece dados iniciais para facilitar os testes: Peso Pena, Peso Médio, Peso Pesado e Charles Oliveira.

## Pré-requisitos

- JDK instalado;
- MySQL ou MariaDB instalado e em execução;
- driver JDBC compatível com o banco escolhido;
- `java` e `javac` disponíveis no `PATH`.

```bash
java -version
javac -version
```

## Configuração do banco

Crie o banco e execute o script inicial:

```sql
CREATE DATABASE mma_dao_jdbc
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE mma_dao_jdbc;
SOURCE caminho/para/bd.sql;
```

Na raiz do projeto, crie o arquivo `db.properties`:

```properties
dburl=jdbc:mysql://localhost:3306/mma_dao_jdbc?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
user=seu_usuario
password=sua_senha
```

| Propriedade | Finalidade |
|---|---|
| `dburl` | URL de conexão JDBC |
| `user` | Usuário do banco |
| `password` | Senha do banco |

Para MariaDB, ajuste a URL e utilize o driver correspondente:

```properties
dburl=jdbc:mariadb://localhost:3306/mma_dao_jdbc
user=seu_usuario
password=sua_senha
```

> ⚠️ `db.properties` está no `.gitignore`. Nunca publique credenciais reais no GitHub.

## Como executar

### Eclipse

1. Importe o projeto como um projeto Java existente;
2. adicione o JAR do driver JDBC ao **Build Path**;
3. deixe `db.properties` na raiz do diretório de execução;
4. execute `src/application/Program.java` como aplicação Java.

### Windows

Considerando o driver em `lib/mysql-connector-j.jar`:

```bat
mkdir bin
javac -cp "lib\mysql-connector-j.jar" -d bin src\db\*.java src\model\entities\*.java src\model\dao\*.java src\model\dao\impl\*.java src\application\Program.java
java -cp "bin;lib\mysql-connector-j.jar" application.Program
```

### Linux ou macOS

```bash
mkdir -p bin
javac -cp "lib/mysql-connector-j.jar" -d bin src/db/*.java src/model/entities/*.java src/model/dao/*.java src/model/dao/impl/*.java src/application/Program.java
java -cp "bin:lib/mysql-connector-j.jar" application.Program
```

## Utilização

O menu principal oferece:

```text
1 - LUTADOR
2 - CATEGORIA
3 - SAIR
```

No menu de **LUTADOR**:

```text
1 - Inserir um novo lutador
2 - Deletar um lutador
3 - Atualizar um lutador
4 - Encontrar um lutador pelo ID
5 - Listar todos os lutadores
6 - Listar lutadores pela categoria
7 - Voltar
```

No menu de **CATEGORIA**:

```text
1 - Inserir uma nova categoria
2 - Deletar uma categoria
3 - Atualizar uma categoria
4 - Encontrar uma categoria pelo ID
5 - Listar todas as categorias
6 - Voltar
```

Para cadastrar ou atualizar um lutador, informe o ID de uma categoria existente.

