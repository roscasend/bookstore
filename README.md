# Bookstore

A full-stack ecommerce bookstore built as a learning project.

## Stack

- **Backend:** Spring Boot 3 (Java 21) — REST API, JPA, H2 (later: PostgreSQL), Spring Security with JWT
- **Frontend:** Angular (standalone components, signals) — book catalog, cart, checkout, auth

## Repository layout

```
bookstore/
├── backend/      # Spring Boot REST API
└── frontend/     # Angular SPA
```

Each subproject is independent: build, run, and deploy separately.

## Getting started

### Backend

```bash
cd backend
./mvnw spring-boot:run
# API on http://localhost:8080
```

### Frontend

```bash
cd frontend
npm install
npm start
# UI on http://localhost:4200
```

## Status

Work in progress — building incrementally through milestones (REST API → CRUD → Angular UI → cart → checkout → auth).
