¡Perfecto! Vamos a escribir un **README.md profesional** para tu proyecto del examen de *Taller de Aplicaciones Distribuidas*. Este documento describe tu aplicación, cómo funciona y cómo levantarla localmente. Puedes copiarlo tal cual a tu archivo `README.md` del repositorio GitHub.

---

## BlockChain Financiera - Registro de Préstamos a PYMES

Este proyecto es un sistema web desarrollado con **Spring Boot** que permite registrar préstamos a pequeñas y medianas empresas (PYMES), asegurando **trazabilidad e integridad de datos** mediante una **estructura de blockchain** simple almacenada en una base de datos PostgreSQL.

---

### Características

* Registro de nuevos préstamos con:

  * Nombre de empresa
  * Monto
  * Tasa de interés
  * Fecha de vencimiento
* Cada préstamo se encapsula en un bloque con su **hash** único y el **hash del bloque anterior**.
* Validación de la cadena de bloques para detectar modificaciones o alteraciones.
* Visualización de todos los bloques creados.

---

###  Arquitectura del Proyecto

* **Backend**: Spring Boot (Web, Data JPA, Thymeleaf)
* **Base de datos**: PostgreSQL
* **Vista web**: Thymeleaf + Bootstrap
* **Hashing**: Algoritmo SHA-256
* **ORM**: Hibernate (JPA)

---

###  Estructura de Datos

#### `Block.java`

```java
@Entity
public class Block {
    @Id @GeneratedValue private Long id;
    private LocalDateTime timestamp;
    private String previousHash;
    private String currentHash;
    private String companyName;
    private BigDecimal amount;
    private Double interestRate;
    private LocalDate dueDate;
}
```

---

###  Cálculo del Hash (SHA-256)

```java
String data = previousHash + companyName + amount + interestRate + dueDate + timestamp;
String currentHash = SHA256Helper.hash(data);
```

---

###  Lógica del Blockchain

1. Se obtiene el último bloque de la cadena.
2. Se crea un nuevo bloque con los datos del préstamo.
3. Se calcula el hash actual basado en el bloque anterior.
4. Se guarda el nuevo bloque en la base de datos.
5. Se puede verificar la integridad de la cadena con un clic.

---

###  Interfaz Web

* `/nuevo-prestamo`: Formulario para registrar nuevos préstamos.
* `/listar-bloques`: Lista todos los bloques ordenados.
* `/verificar-cadena`: Verifica si la cadena ha sido alterada o es válida.

---

### Flujo de Usuario

1. Ingresar a `/nuevo-prestamo`.
2. Llenar formulario con los datos del préstamo.
3. Al enviarlo, se registra un nuevo bloque y se calcula su hash.
4. Ir a `/listar-bloques` para visualizar todos.
5. Visitar `/verificar-cadena` para comprobar que la cadena no ha sido manipulada.

---

### 🧪 Prueba Local

#### 1. Requisitos

* Java 17
* Maven
* PostgreSQL

#### 2. Crear la base de datos

```sql
CREATE DATABASE blockloan_db;
```

#### 3. Configurar `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/blockloan_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### 4. Ejecutar el proyecto

```bash
mvn spring-boot:run
```

---

### Decisiones Importantes

* Se usó un hash simple SHA-256 sin mecanismo de minería para facilitar el propósito educativo.
* Toda la lógica está encapsulada en un único servicio (`BlockchainService`) para mantener claridad.
* Se usó `Thymeleaf` por integración nativa con Spring Boot y facilidad de prueba web.

---

###  Desafíos Técnicos

* Asegurar que los hashes se calculen correctamente incluyendo todos los campos necesarios.
* Validar la integridad entre bloques sin errores lógicos.
* Diseñar una estructura web simple pero informativa.

---

### Estructura del Código

```
src/
├─ model/
│   └─ Block.java
├─ controller/
│   └─ BlockController.java
├─ service/
│   └─ BlockchainService.java
├─ repository/
│   └─ BlockRepository.java
├─ util/
│   └─ SHA256Helper.java
├─ templates/
│   ├─ nuevo_prestamo.html
│   ├─ listar_bloques.html
│   └─ verificar_cadena.html
└─ resources/
    └─ application.properties
```

