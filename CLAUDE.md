# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

muService is a personal security/monitoring system (proof-of-concept) that collects data from remote "Test Heads" (IoT sensors like temperature probes), stores measurements in PostgreSQL, and displays them via a secured web UI. Authentication is via Google OAuth2.

## Build Commands

```bash
# Build all modules
./gradlew build

# Build and produce the release zip (fat jar + config + certs)
./gradlew build release

# Run the application (requires a .properties config file)
./gradlew :mu-service-application:mu-service-engine:run

# Run tests for all modules
./gradlew test

# Run tests for a single module
./gradlew :mu-service-application:mu-service-engine:test
./gradlew :mu-service-application:mu-service-webapp:test
./gradlew :mu-service-application:mu-service-database:test

# Run a single test class
./gradlew :mu-service-application:mu-service-engine:test --tests "website.magyar.muservice.bootstrap.BootstrapTest"

# Checkstyle
./gradlew checkstyle

# Jacoco coverage report
./gradlew jacocoTestReport
```

## Architecture

The project is a multi-module Gradle build with a single deployable fat jar produced by the Shadow plugin.

### Module dependency chain

```
mu-service-engine  (main entry point, Bootstrap, scheduler)
    ├── mu-service-webapp  (Jetty server, Spring MVC controllers, Spring Security)
    │       └── mu-service-database  (Hibernate/PostgreSQL, business logic)
    │               └── mu-service-core  (configuration base, exceptions, helpers)
    └── mu-service-database
```

### Key architectural decisions

**Startup flow:** `Application.main()` → `Bootstrap.bootstrap()` reads a `.properties` file passed as `args[0]`, initialises Hibernate (`SessionFactoryHelper`), then creates and starts an embedded Jetty server (`WebAppServer`). The Spring application context is loaded via `engine-application-context.xml` which component-scans `website.magyar.muservice`.

**Web layer pattern:** Each feature follows Controller → Provider → Business → Hibernate table:
- Controllers (`web/controller/`) handle HTTP, delegate to Providers
- Providers (`web/provider/`) assemble JSON responses using Business classes
- Business classes (`database/business/`) wrap Hibernate sessions
- Table entities (`database/tables/`) are JPA/Hibernate-mapped POJOs

**Test Heads:** Remote IoT devices that POST sensor data (temperature, WAN IP, etc.) to the service. Each device is registered in the `public.head` DB table with a `headId`, `type` (e.g. `ds18b20`), and `active` flag. Measurements are stored in `TestHeadData`.

**Scheduled cleanup:** `RegularTask` runs on a cron expression (`regularTask.cron.task` in the properties file, default: daily at 3am). It deletes sensor readings older than 2 years and removes out-of-range values (temperature outside ±60°C) from `ds18b20` heads.

**Security:** Spring Security + Google OAuth2. The keystore for HTTPS lives in `config/security/`. The demo keystore password is `Rf7856op`. For local development, HTTPS can be disabled via `isHttpsInUse=false` in the properties file.

### Configuration

The runtime requires a single `.properties` file (see `mu-service-application/modules/mu-service-engine/mu-service.conf.properties` for the template). Key properties:
- `webapp.port`, `isHttpsInUse`, `keyStoreFile`, `keyStorePassword`
- `hibernate.connection.url/username/password` — PostgreSQL connection
- `google_client_id`, `google_client_secret`, `google_redirect_url` — OAuth2
- `regularTask.cron.task` — cron expression for the cleanup scheduler

### Release artifact

`./gradlew release` produces a zip under `release/` containing the fat jar (`mu-service-<version>.jar`), the conf template, and the keystore files. The jar is self-contained (Shadow plugin merges Spring's `spring.handlers`/`spring.schemas`).

## Code conventions

- Java 11, Spring 5 (XML config, not Spring Boot), JUnit 4 + Mockito + PowerMock
- Checkstyle enforced (config in `config/checkstyle/`); failures are non-fatal (`ignoreFailures=true`) but reported
- Base classes `BusinessBase`, `ProviderBase`, `ControllerBase` centralise session/auth access — extend these rather than going directly to Hibernate/Spring
- `PropertyDto` classes exist per module to carry configuration values; do not add fields to the wrong module's DTO
