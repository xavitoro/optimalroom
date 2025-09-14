# Room Reservation API

## Resum i arquitectura
Servei REST per gestionar reserves de sales. Està organitzat amb una **arquitectura hexagonal lleugera**: el domini conté les regles de negoci (`domain`), la capa d'aplicació ofereix *use cases* (`application/usecases`) i la infraestructura exposa adaptadors REST, seguretat i persistència (`infrastructure`).

## Requisits
- Java 21
- Maven 3.x
- Docker i *docker-compose* (opcional per Postgres)

## Com executar

### H2 per defecte
```bash
mvn spring-boot:run
```
El servei quedarà disponible a `http://localhost:8080` i el panell de la base de dades H2 a `http://localhost:8080/h2-console`.

## Endpoints

Tots els endpoints (excepte el registre) requereixen autenticació *Basic*.

### Autenticació
- `POST /auth/register`
  ```json
  {"email": "usuari@example.com", "password": "secret"}
  ```
  Resposta: `201 Created` o `409 Conflict` si el correu ja existeix.

### Reserves
- **Crear reserva** – `POST /reservations`
  ```json
  {
    "roomId": "<uuid sala>",
    "userId": "<uuid usuari>",
    "start": "2025-09-11T16:00:00Z",
    "end": "2025-09-11T17:00:00Z"
  }
  ```
  Resposta `201 Created` amb la reserva creada.

- **Llistar reserves** – `GET /reservations?roomId=...&userId=...&date=2025-09-11&page=0&size=20`
  Retorna una pàgina amb les reserves i el total.

- **Cancel·lar reserva** – `DELETE /reservations/{id}`
  Resposta `204 No Content`.

## Regles de negoci
- Les reserves no poden solapar-se en una mateixa sala.
- Un usuari no pot acumular més de **dues hores contínues**; intervals separats per menys de 30 minuts es consideren continuats.
- La sala i l'usuari han d'existir prèviament.
- Cancel·lar una reserva n'actualitza l'estat a `CANCELLED`.

## Autenticació
Es fa servir **HTTP Basic**. Per a proves ràpides hi ha un usuari per defecte `admin/admin` o bé es pot registrar un nou usuari amb `/auth/register`.

En un entorn real es recomanaria substituir-ho per **JWT Bearer tokens** amb expiració, *refresh tokens* i revocació.

## Decisions i limitacions
- Projecte simplificat pensat per a demostracions.
- Persistència H2 per defecte.
- No hi ha gestió de rols ni funcionalitats avançades de notificació.
- L'autenticació bàsica no és apta per producció. S'hauria d'afegir autenticació JWT i es podria afegir l'autenticació de Google

## Com executar tests
```bash
mvn test
```