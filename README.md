# Tenpista FullStack Challenge

## Tenpista – Transaction Management App

Aplicación fullstack para la gestión de transacciones, desarrollada como parte de una prueba técnica.

Incluye backend en Java *Spring Boot*, frontend en *React + Vite + Tailwind CSS* y *PostgreSQL*.

## Stack Tecnológico

### Backend

- Java 21
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Flyway (migraciones)
- PostgreSQL
- OpenAPI / Swagger
- JUnit 5 + Mockito

### Frontend

- React
- Vite
- Tailwind CSS v4
- Axios
- Arquitectura por componentes

### Infraestructura

- Docker
- Docker Compose

## Arquitectura del proyecto

### Backend (Arquitectura por capas)

El backend sigue una arquitectura clásica por capas, con separación clara de responsabilidades:

```text
backend/
├── api
│   ├── TransactionController.java
│   ├── dto
│   │   ├── CreateTransactionRequest.java
│   │   └── TransactionResponse.java
│   └── error
│       ├── ErrorResponse.java
│       └── GlobalExceptionHandler.java
├── domain
│   └── Transaction.java
├── repository
│   └── TransactionRepository.java
├── service
│   ├── TransactionService.java
│   └── impl
│       └── TransactionServiceImpl.java
├── exception
│   ├── BusinessException.java
│   └── GlobalExceptionHandler.java
├── config
│   ├── OpenApiConfig.java
│   └── WebConfig.java
└── resources
    ├── db/migration
    └── application.yml

```

#### Principios aplicados

- Validaciones de negocio en la capa service
- Controladores delgados
- Manejo global de errores
- DTOs para desacoplar API ↔ dominio
- Paginación estándar con Pageable

### Frontend (Arquitectura por responsabilidades)

El frontend está organizado por tipo de responsabilidad, manteniendo componentes simples y reutilizables:

```text
frontend/
├── src
│   ├── api
│   │   └── transactionsApi.js
│   ├── components
│   │   ├── GlobalLoading.jsx
│   │   ├── Toast.jsx
│   │   ├── TransactionForm.jsx
│   │   ├── TransactionList.jsx
│   │   └── TransactionListSkeleton.jsx
│   ├── pages
│   │   └── TransactionsPage.jsx
│   ├── utils
│   │   └── date.js
│   ├── App.css
│   ├── App.jsx
│   ├── index.css
│   └── main.jsx
└── vite.config.js
```

#### Principios aplicados

- Estado levantado al nivel de página
- Componentes presentacionales reutilizables
- Feedback visual (toast, loading, skeleton)
- Responsive design (mobile first)
- UX cuidada sin sobreingeniería

## Ejecución

El proyecto utiliza variables de entorno para configurar la base de datos y la comunicación entre frontend y backend.

En la raíz del repositorio existe un archivo de referencia:

```bash
.env.example
```

Este archivo no debe modificarse directamente.

Para ejecutar el proyecto, es necesario crear los archivos `.env` correspondientes.

### Ejecución con Docker (recomendado)

Docker Compose lee automáticamente el archivo .env ubicado en la raíz del proyecto.

```bash
cp .env.example .env
```

Luego, editar `.env` si se desea cambiar puertos o credenciales.

Ejemplo:

```env
#BACK
POSTGRES_DB=tenpista_db
POSTGRES_USER=tenpista_user
POSTGRES_PASSWORD=tenpista_pass
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/tenpista_db

#FRONT
VITE_API_URL=http://localhost:8080
```

Desde la raíz del proyecto:

```bash
docker compose up --build
```

Esto levanta:

- PostgreSQL
- Backend
- Frontend

URLs

- Frontend: <http://localhost:3000>
- Backend API: <http://localhost:8080>
- Swagger: <http://localhost:8080/swagger-ui.html>

### Ejecución Local

#### Back

El backend puede ejecutarse localmente usando variables de entorno o el archivo application.yml.

Opción recomendada:

```bash
cd backend
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/tenpista_db
export POSTGRES_USER=tenpista_user
export POSTGRES_PASSWORD=tenpista_pass

./mvnw clean install
./mvnw spring-boot:run
```

- API disponible en: <http://localhost:8080>
- Swagger: <http://localhost:8080/swagger-ui.html>

#### Front

El frontend utiliza variables de entorno de Vite.

```bash
cd frontend
cp ../.env.example .env.local
```

Asegurarse de que exista la variable:

```env
VITE_API_URL=http://localhost:8080
```

Luego:

```bash
npm install
npm run dev
```

- App disponible en: <http://localhost:5173>

### Notas importantes

- `.env.example` se incluye únicamente como referencia
- `.env` y `.env.local` no deben versionarse
- Docker Compose utiliza automáticamente el .env de la raíz
- Vite solo expone variables que comienzan con `VITE_`

## Back - Test unitarios

```bash
//Local
cd backend
./mvnw test

//Docker
docker exec -it tenpista-backend ./mvnw test
```

## Documentación de la API (Swagger)

La API REST está documentada con OpenAPI / Swagger.

Acceso: <http://localhost:8080/swagger-ui.html>

Incluye:

- Endpoints `Create`, `Read` y `Delete` de transacciones
- Paginación (page, size, sort)
- Códigos de error HTTP
- Ejemplos de respuestas

### Endpoints Principales

#### Obtener transacciones (paginado)

```bash
GET /transactions?page=0&size=10
```

#### Crear transacción

```bash
POST /transactions
```

Body ejemplo:

```json
{
  "amount": 25000,
  "merchant": "Amazon",
  "tenpistaName": "Juan Perez",
  "transactionDate": "2024-12-10T14:30:00"
}
```

#### Eliminar transacción

```bash
DELETE /transactions/{id}
```

### Reglas de Negocio Implementadas

- El monto de la transacción no puede ser negativo
- La fecha de la transacción no puede ser futura
- Validaciones realizadas en la capa de servicio
- Errores de negocio manejados mediante BusinessException
- Respuestas HTTP claras y consistentes
