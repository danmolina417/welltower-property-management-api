<!-- Workspace-specific Copilot Instructions -->

## Project Overview

This is a Java Spring Boot REST API for WellTower Property Management System. The application manages multifamily properties, units, residents, and generates rent roll reports.

## Key Project Information

- **Language**: Java 21
- **Framework**: Spring Boot 3.2.0
- **Database**: PostgreSQL (default) or MySQL
- **Port**: 8892
- **API Context Path**: /api
- **Build Tool**: Maven

## Project Checklist Progress

- [x] Verify copilot-instructions.md file exists
- [x] Clarify project requirements (multifamily property management API)
- [x] Scaffold the Java Spring Boot project
- [x] Create entity models (Property, Unit, Resident)
- [x] Create DTOs for API requests/responses
- [x] Create repository layer (database access)
- [x] Create service layer (business logic)
- [x] Create REST controllers
- [x] Create configuration files

## Database Configuration

The application is configured for PostgreSQL by default. Before running:

1. Create database:
   ```bash
   createdb property_management
   ```

2. Update `src/main/resources/application.yml` with your database credentials if needed

3. Tables are automatically created on first run via Hibernate

## Building and Running

1. **Build Project**:
   ```bash
   mvn clean install
   ```

2. **Run Application**:
   ```bash
   mvn spring-boot:run
   ```

3. **Test API**: The API will be available at `http://localhost:8892/api`

## API Structure

- **Property Management**: `/api/properties` - CRUD operations for properties
- **Unit Management**: `/api/units` - CRUD operations for units
- **Resident Management**: `/api/residents` - Move-in/out, rent updates
- **Rent Roll Reports**: `/api/reports/rent-roll` - Generate occupancy and revenue reports

## Key Features Implemented

1. **Property Management**: Create, read, update, delete properties
2. **Unit Management**: Create units, track occupancy status, deactivate/reactivate units
3. **Resident Management**: Move residents in/out, update rent, track move-in/out dates
4. **Rent Roll Reporting**: Generate daily rent rolls and summaries showing unit occupancy and revenue

## Next Steps

1. Test endpoints locally
2. Set up GitHub repository
3. Configure CI/CD pipeline (if needed)
4. Add authentication and authorization
5. Add integration tests
6. Deploy to production environment

## Documentation

- See README.md for complete API documentation and usage examples
- See pom.xml for dependencies
- See src/main/resources/application.yml for configuration options
