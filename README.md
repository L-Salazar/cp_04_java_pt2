## 👨‍💻 Autores

Leonardo Salazar - 557484

Alexsandro Macedo - 557068

# Deploy e vídeo da explicação

Site com deploy: https://cp-java-557484.azurewebsites.net/brinquedos

Video: https://youtu.be/E141I8tXXYc?feature=shared

Foi utilizado Azure Web Apps para o deploy

# Performance Kids — Spring Boot (Thymeleaf + JPA)

Aplicação web para **gestão de brinquedos, categorias e funcionários**, construída com **Spring Boot**, **Thymeleaf**, **Spring Data JPA**, **Bean Validation** e **Lombok**. O visual das páginas usa **Tailwind CSS (CDN)** com um padrão consistente (header em gradiente, cards com sombra, botões com gradiente).

## ✨ Funcionalidades
- **Brinquedos**: CRUD completo (lista, novo, editar, excluir) com vínculo a **Categoria**.
- **Categorias**: CRUD completo; usado para classificar brinquedos.
- **Funcionários**: CRUD completo com validação de **senha**, **e-mail** e **cargo**.
- **Templates Thymeleaf** padronizados (listagem + formulário) em todas as entidades.
- **Validações** no lado do servidor com `jakarta.validation`.
- **Persistência** com JPA/Hibernate + HikariCP.

> Obs.: Caso queira expor API REST, mantenha/controladores separados com `@RestController`. Nesta versão, os controladores são **MVC/Thymeleaf**.

---

## 🧱 Stack & Dependências
- **Java 17**
- **Spring Boot 3.x**
  - `spring-boot-starter-web`
  - `spring-boot-starter-thymeleaf`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-validation`
- **Lombok** (gera getters/setters/constructors via anotações)
- **Banco**: Oracle (driver **ojdbc11**)
- **Tailwind CSS** via CDN (nos templates)
- (Opcional) **spring-boot-devtools** para hot-reload

### POM — exemplo de dependência do Oracle
```xml
<dependency>
  <groupId>com.oracle.database.jdbc</groupId>
  <artifactId>ojdbc11</artifactId>
  <version>23.7.0.25.01</version>
</dependency>
```

---

## 📁 Estrutura de Pastas (resumo)
```
src/
  main/
    java/br/com/fiap/performancekids/
      controller/
        BrinquedoController.java
        CategoriaController.java
        FuncionarioController.java
      entity/
        Brinquedo.java
        Categoria.java
        Funcionario.java
      repository/
        BrinquedoRepository.java
        CategoriaRepository.java
        FuncionarioRepository.java
      service/
        BrinquedoService.java
        CategoriaService.java
        FuncionarioService.java
        ResourceNotFoundException.java
    resources/
      templates/
        brinquedos/
          listagem.html
          formulario.html
        categorias/
          listagem.html
          formulario.html
        funcionarios/
          listagem.html
          formulario.html
      application.properties
```

---

## 🗄️ Modelagem (Entidades)

### `Funcionario`
```java
@Entity @Table(name="tb_performance_kids_funcionarios",
  uniqueConstraints=@UniqueConstraint(name="uk_func_email", columnNames="email_funcionario"))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
class Funcionario {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id_funcionario") private Long id;

  @NotBlank @Size(max=100)
  @Column(name="nm_funcionario", nullable=false, length=100) private String nome;

  @NotBlank @Email @Size(max=150)
  @Column(name="email_funcionario", nullable=false, length=150) private String email;

  @NotBlank @Size(min=6, max=60)
  @Column(name="senha_funcionario", nullable=false, length=60) private String senha;

  @NotBlank @Size(max=50)
  @Column(name="cargo_funcionario", nullable=false, length=50) private String cargo;
}
```

### `Categoria`
```java
@Entity @Table(name="tb_performance_kids_categorias")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
class Categoria {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id_categoria") private Long id;

  @NotBlank @Size(min=3, max=100)
  @Column(name="nm_categoria", nullable=false, length=100) private String nome;
}
```

### `Brinquedo`
```java
@Entity @Table(name="tb_performance_kids_brinquedos")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
class Brinquedo {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id_brinquedo") private Long id;

  @NotBlank @Size(min=3, max=100)
  @Column(name="nm_brinquedo", nullable=false, length=100) private String nome;

  @ManyToOne(optional=false)
  @JoinColumn(name="id_categoria", nullable=false)
  private Categoria categoria;

  @NotBlank @Size(max=10)
  @Column(name="cl_brinquedo", nullable=false, length=10) private String classificacao;

  @NotBlank @Size(max=10)
  @Column(name="tm_brinquedo", nullable=false, length=10) private String tamanho;

  @NotNull @DecimalMin("0.01") @Digits(integer=8, fraction=2)
  @Column(name="vl_preco", nullable=false, precision=10, scale=2) private BigDecimal preco;
}
```

> **Dica**: Ative o *annotation processing* no IDE para Lombok.

---

## 🧭 Rotas (MVC/Thymeleaf)

### Brinquedos
- `GET /brinquedos` → lista (templates/brinquedos/listagem.html)
- `GET /brinquedos/novo` → formulário vazio
- `POST /brinquedos/salvar` → cria
- `GET /brinquedos/editar/{id}` → formulário preenchido
- `POST /brinquedos/alterar/{id}` → atualiza
- `GET /brinquedos/excluir/{id}` → exclui

### Categorias
- `GET /categorias` → lista
- `GET /categorias/novo` → formulário vazio
- `POST /categorias/salvar` → cria
- `GET /categorias/editar/{id}` → formulário preenchido
- `POST /categorias/alterar/{id}` → atualiza
- `GET /categorias/excluir/{id}` → exclui

### Funcionários
- `GET /funcionarios` → lista
- `GET /funcionarios/novo` → formulário vazio
- `POST /funcionarios/salvar` → cria
- `GET /funcionarios/editar/{id}` → formulário preenchido
- `POST /funcionarios/alterar/{id}` → atualiza
- `GET /funcionarios/excluir/{id}` → exclui

> **HTML não envia PUT/DELETE** diretamente; por isso o padrão `POST /alterar/{id}` e `GET /excluir/{id}`.

---

## ⚙️ Configuração

### `application.properties` (exemplo)
```properties
# Porta
server.port=8080

# Oracle
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=PERFKIDS
spring.datasource.password=secret
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
# Hibernate 6 escolhe o dialect automaticamente para Oracle;
# Se quiser, force:
# spring.jpa.database-platform=org.hibernate.dialect.OracleDialect

# DevTools (opcional)
spring.devtools.restart.enabled=true
```

### Pré-requisitos
- **JDK 17+**
- **Maven 3.9+** (se não usar o wrapper)
- **Oracle** (XE/19c) em execução; criar usuário/schema e permissões.

> Se o **Maven Wrapper** (`mvnw`) não estiver versionado no projeto, use o Maven instalado do sistema (`mvn`) ou gere o wrapper.

---

## 🏃‍♀️ Como Rodar

### 1) Via Maven (dev)
```bash
mvn clean spring-boot:run
# ou (se o wrapper existir)
./mvnw clean spring-boot:run
```

### 2) Build JAR e executar
```bash
mvn clean package
java -jar target/performancekids-*.jar
```

Acesse:
- **http://localhost:8080/brinquedos**
- **http://localhost:8080/categorias**
- **http://localhost:8080/funcionarios**

---

## 🧩 Notas de Template & Formulários

- Use `th:object` condizente com o nome da variável do `Model`:
  - Ex.: `model.addAttribute("brinquedo", …)` ⇒ `<form th:object="${brinquedo}">`.
- Para relacionamentos `@ManyToOne` (ex.: Brinquedo → Categoria), envie **o ID** no form:
  ```html
  <select th:field="*{categoria.id}"> … </select>
  ```
- **Atualização** via `formaction`:
  ```html
  <!-- novo -->
  <button th:if="${entidade.id} == null"
          th:formaction="@{/entidades/salvar}">Salvar</button>
  <!-- editar -->
  <button th:unless="${entidade.id} == null"
          th:formaction="@{/entidades/alterar/{id}(id=${entidade.id})}">Atualizar</button>
  ```

---

## 🧰 Troubleshooting

### Porta ocupada (8080/8081/9090)
- **Windows (PowerShell)**:
  ```powershell
  netstat -ano | findstr :8080
  taskkill /PID <PID> /F
  ```
- Ou mude a porta em `application.properties` (`server.port=8081`).

### Maven Wrapper ausente
- Se o IntelliJ tentar rodar `mvnw` e der erro `.mvn/wrapper/...` ausente, rode com o Maven do sistema:
  ```bash
  mvn clean spring-boot:run
  ```
  (ou gere/adicione o wrapper ao repositório)

### Erro 400 ao salvar Brinquedo (conversão de categoria)
- Garanta que o form envia `categoria.id` (select) **e** que a categoria existe no banco.

### Lombok não gera getters/setters
- Ative **Annotation Processing** no IDE: *Settings → Build Tools → Annotation Processors*.


---

## 🧪 Seeds (opcional)
Crie algumas categorias primeiro para facilitar o cadastro de brinquedos:
```sql
INSERT INTO tb_performance_kids_categorias (id_categoria, nm_categoria) VALUES (1, 'Educativos');
INSERT INTO tb_performance_kids_categorias (id_categoria, nm_categoria) VALUES (2, 'Montagem');
INSERT INTO tb_performance_kids_categorias (id_categoria, nm_categoria) VALUES (3, 'Ao ar livre');
```
> Ajuste os nomes das colunas/tabelas conforme seu mapeamento real.

---

## 📜 Licença
Projeto acadêmico. Defina a licença de sua preferência (ex.: MIT).

---

## 🙌 Créditos
Desenvolvido no contexto **FIAP – Performance Kids**. Interfaces criadas com **Tailwind CSS (CDN)**.
