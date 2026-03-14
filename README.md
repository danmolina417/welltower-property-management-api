# WellTower Property Management API

A comprehensive Spring Boot REST API for managing multifamily residential properties, units, residents, and generating rent roll reports.

## Features

- **Property Management**: Create and manage multifamily properties
- **Unit Management**: Track and manage individual units within properties, including unit status (active/inactive, occupied/vacant)
- **Resident Management**: Handle resident move-ins, move-outs, and rent changes
- **Rent Roll Reports**: Generate daily occupancy and revenue reports

## Technology Stack

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL / MySQL**
- **Maven**

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL or MySQL database

### Setup Instructions

- Java 21 (JDK): [Eclipse Temurin Downloads](https://adoptium.net/temurin/releases/?version=21)
- Maven: [Apache Maven - Installing Maven](https://maven.apache.org/install.html)
- PostgreSQL: [PostgreSQL Download and Installation](https://www.postgresql.org/download/)
- MySQL: [MySQL Community Server Download](https://dev.mysql.com/downloads/mysql/)

#### OS-specific Package Manager / Installation Tools

- macOS: [Homebrew Installation](https://brew.sh/)
- Windows (PC): [WinGet Documentation](https://learn.microsoft.com/windows/package-manager/winget/) or [Chocolatey Installation](https://chocolatey.org/install)
- Linux: [APT (Debian/Ubuntu)](https://wiki.debian.org/Apt), [DNF (Fedora/RHEL)](https://docs.fedoraproject.org/en-US/quick-docs/dnf/), or [Snapcraft](https://snapcraft.io/docs/installing-snapd)

### Bruno API Setup

- I recommend using Bruno for API testing as I have created a collection for this project with all the endpoints and example requests/responses. You can import this collection into your local Bruno instance to quickly test the API once it's running.
- For Postman user, I've also included a Postman collection export that you can import into Postman if you prefer that tool.

#### Install Bruno

- Official download page: [Bruno Downloads](https://www.usebruno.com/downloads)
- Windows package manager option: `winget install Bruno.Bruno`
- macOS package manager option: `brew install --cask bruno`

#### Collection Import

- 1. Unzip the Bruno_WellTower_Collection.zip.
- 2. Open Bruno.
- 3. Click **Open Collection** or **Import Collection**.
- 4. Open  Bruno collection folder in this repo, select that folder.


## Configuration

The application listens on **port 8892**.

### Setup Checklist (Recommended Order)

- 1. Install prerequisites: Java 21+, Maven, and PostgreSQL or MySQL.
- 2. Verify tools are available in your terminal:
  - `java -version`
  - `mvn -version`
  - `psql --version` or `mysql --version`
- 3. Create the `property_management` database.
- 4. Update database credentials in `src/main/resources/application.yml`.
- 5. Build the project with `mvn clean install`.
- 6. Start the API with `mvn spring-boot:run`.
- 7. Confirm the API is reachable at `http://localhost:8892/api`.

### Database Setup

#### PostgreSQL

```bash
createdb property_management
```

Update `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/property_management
    username: postgres
    password: your_password
```

#### MySQL

```bash
mysql -u root -p
CREATE DATABASE property_management;
exit
```

Update `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/property_management
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
```

## Building and Running

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The API will be available at: `http://localhost:8892/api`

## API Endpoints

- Note: The database should be seeded for your quick testing with:
  - One manager - John Doe
  - One property - Happy Homes
  - Three units - 101, 102, and 103 (as inactive)
  - Two residents - Robert Smith in unit 101 and Jane Summers in unit 102

### Properties

- `POST /api/properties` - Create a property
- `GET /api/properties` - Get all active properties
- `GET /api/properties/{propertyId}` - Get a specific property
- `PUT /api/properties/{propertyId}` - Update a property
- `DELETE /api/properties/{propertyId}` - Delete (deactivate) a property

### Units

- `POST /api/units/property/{propertyId}` - Create a unit
- `GET /api/units/{unitId}` - Get a specific unit
- `GET /api/units/property/{propertyId}` - Get all units in a property
- `PUT /api/units/{unitId}` - Update a unit
- `PUT /api/units/{unitId}/deactivate` - Deactivate a unit
- `PUT /api/units/{unitId}/reactivate` - Reactivate a unit

### Residents

- `POST /api/residents/move-in` - Move in a resident
- `GET /api/residents/{residentId}` - Get a specific resident
- `GET /api/residents/property/{propertyId}` - Get all residents in a property
- `GET /api/residents/unit/{unitId}` - Get all residents in a unit
- `PUT /api/residents/{residentId}/rent` - Update resident rent
- `PUT /api/residents/{residentId}/move-out` - Move out a resident (today)
- `PUT /api/residents/{residentId}/move-out-on-date?moveOutDate=YYYY-MM-DD` - Move out a resident on specific date

### Rent Roll Reports

- `GET /api/reports/rent-roll/property/{propertyId}/date/{date}` - Get rent roll for a specific date (YYYY-MM-DD)
- `GET /api/reports/rent-roll/property/{propertyId}/range?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` - Get rent roll for date range
- `GET /api/reports/rent-roll/property/{propertyId}/date/{date}/summary` - Get rent roll summary for a specific date

## Example Usage

### Create a Property

```bash
curl -X POST http://localhost:8892/api/properties \
  -H "Content-Type: application/json" \
  -d '{
    "property_name": "Sunset Gardens",
    "address": "123 Main St",
    "city": "Springfield",
    "state": "IL",
    "zip_code": "62701"
  }'
```

### Create a Unit

```bash
curl -X POST http://localhost:8892/api/units/property/1 \
  -H "Content-Type: application/json" \
  -d '{
    "unit_number": "P1-U01",
    "bedrooms": "2",
    "bathrooms": "1",
    "square_feet": 850.0
  }'
```

### Move in a Resident

```bash
curl -X POST http://localhost:8892/api/residents/move-in \
  -H "Content-Type: application/json" \
  -d '{
    "property_id": 1,
    "unit_id": 1,
    "first_name": "John",
    "last_name": "Doe",
    "email": "john.doe@example.com",
    "phone_number": "555-1234",
    "monthly_rent": 3000.00,
    "move_in_date": "2024-01-01"
  }'
```

### Get Rent Roll

```bash
curl http://localhost:8892/api/reports/rent-roll/property/1/date/2024-01-01
```

## Project Structure

```
src/
├── main/
│   ├── java/com/welltower/propertymanagement/
│   │   ├── controller/      # REST API controllers
│   │   ├── service/         # Business logic
│   │   ├── repository/      # Data access layer
│   │   ├── model/           # Entity models
│   │   ├── dto/             # Data transfer objects
│   │   └── PropertyManagementApplication.java
│   └── resources/
│       └── application.yml   # Configuration
└── test/
    └── java/com/welltower/propertymanagement/
```

## Database Schema

The application uses Hibernate ORM with JPA for data persistence. Tables are created automatically on first run:

- `managers` - Store manager information
- `properties` - Store property information
- `units` - Store unit information for each property
- `residents` - Store resident information with occupation history

All tables include indexes on frequently queried columns for performance optimization.

#### Quick Validation via Bruno or Postman

- Start API: `mvn spring-boot:run`
- Send `GET {{baseUrl}}/properties`
- Send `GET {{baseUrl}}/reports/rent-roll/property/{{propertyId}}/date/2026-02-02`


