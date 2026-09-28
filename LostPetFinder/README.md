# LostPetFinder — Lost and Found Pet Reporting Platform

A clean Spring Boot + MySQL backend for reporting lost pets, reporting found animals, searching by locality, finding possible matches, and resolving reports.

## 1. Requirements
- Java 21
- IntelliJ IDEA
- XAMPP (Apache is optional; MySQL is required)
- MySQL running on port 3306
- Postman

## 2. Run MySQL with XAMPP
1. Open XAMPP Control Panel.
2. Start **MySQL**.
3. The application automatically creates the `lost_pet_finder` database through `createDatabaseIfNotExist=true`.
4. Default XAMPP MySQL settings are username `root` and blank password. If your MySQL password is different, edit `src/main/resources/application.properties`.

## 3. Run in IntelliJ
1. Open this folder in IntelliJ IDEA.
2. Wait for Maven dependencies to download.
3. Open `LostPetFinderApplication.java`.
4. Click the green Run button.
5. Open http://localhost:8080 in a browser for the dashboard.

## 4. Main API endpoints
Base URL: `http://localhost:8080`

### Users
- `POST /api/users`
- `GET /api/users`
- `GET /api/users/{id}`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

POST body:
```json
{
  "name": "Mohan",
  "email": "mohan@example.com",
  "phone": "9876543210"
}
```

### Lost pets
- `POST /api/lost-pets`
- `GET /api/lost-pets`
- `GET /api/lost-pets/{id}`
- `PUT /api/lost-pets/{id}`
- `DELETE /api/lost-pets/{id}`
- `PATCH /api/lost-pets/{id}/resolve`
- `GET /api/lost-pets/search?locality=Coimbatore`

POST body:
```json
{
  "species": "Dog",
  "breed": "Labrador",
  "color": "Golden",
  "lastSeenLocation": "Coimbatore",
  "userId": 1
}
```

### Found animals
- `POST /api/found-animals`
- `GET /api/found-animals`
- `GET /api/found-animals/{id}`
- `PUT /api/found-animals/{id}`
- `DELETE /api/found-animals/{id}`
- `PATCH /api/found-animals/{id}/resolve`
- `GET /api/found-animals/search?locality=Coimbatore`

POST body:
```json
{
  "species": "Dog",
  "breed": "Labrador",
  "color": "Golden",
  "foundLocation": "Coimbatore",
  "description": "Friendly dog wearing a blue collar",
  "userId": 1
}
```

### Matching
- `GET /api/matches/lost/{lostPetId}`

Matching rule: the found report must be **ACTIVE** and match **species + locality**. Colour adds 25 points and breed adds 15 points. A resolved lost report returns no active matches.

### Combined locality search
- `GET /api/reports/search?locality=Coimbatore`

### Dashboard
- `GET /api/dashboard/summary`

## 5. Business rules implemented
- Resolved reports are excluded from active match suggestions.
- Species + locality are mandatory for a match.
- Validation uses `@NotBlank`, `@NotNull`, `@Positive`, `@Email`, `@Pattern` and `@Size`.
- `@RestControllerAdvice` returns clear validation/not-found/conflict errors.
- Appropriate HTTP status codes are used: 201, 200, 400, 404, 409, 500.

## 6. Suggested Postman demo order
1. Create a user.
2. Create one lost pet using the returned user ID.
3. Create one found animal with the same species and locality.
4. Call `GET /api/matches/lost/1`.
5. Resolve the lost report with `PATCH /api/lost-pets/1/resolve`.
6. Call the match endpoint again and show that no active matches are returned.
7. Test locality search.
8. Test validation by sending an empty species or invalid user ID.
9. Open the dashboard at http://localhost:8080.
