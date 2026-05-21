# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Test Commands

```bash
# Build and install to local Maven repo
mvn clean install

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=ExceptionHandlerControllerTest

# Package without installing
mvn package
```

## Project Overview

A shared Java 8 / Spring Boot 2 library (`com.forleven.common`) used across **all Forleven microservices** as a Maven dependency. Because every downstream project imports this library, changes to public APIs, exception types, JSON serialization behavior, or Spring auto-configuration can be **breaking changes** across the entire platform — treat this repo like a published SDK. Consumers activate it via `@EnableForlevenCommon`, which auto-imports core beans: `MessageUtil`, `ExceptionHandlerController`, `FormErrors`, and `UploadUtil`.

## Architecture

### Package Map

| Package | Purpose |
|---|---|
| `exception` | `HttpException` hierarchy (`BadRequestException`, `NotFoundException`, `ForbiddenException`, `ConflictException`) + centralized `ExceptionHandlerController` |
| `web` | REST response wrappers — `Resources<T>` (paginated HAL-style), `ResourceErrors`, `Links` |
| `domain` | `LogFields` MappedSuperclass — adds `createDate`, `updateDate`, `status` to JPA entities |
| `validation` | `FormErrors` — converts JSR-303 `BindingResult` errors into Forleven JSON format |
| `i18n` | `MessageUtil` — thin wrapper around Spring `MessageSource` for i18n message resolution |
| `json` | Jackson annotation-based serializers/deserializers for dates, money, and case transformations |
| `specification` | `GeneralSpecification` — fluent Spring Data JPA `Specification` DSL |
| `hateoas` | `LinkBuilder` / `LinkBuilderFactory` / `MethodLinkBuilderFactory` for HATEOAS link construction |
| `s3` | `UploadUtil` / `UploadDsl` — AWS S3 upload helpers with configurable ACLs; `UploadError` for failures |
| `ses` | `NotificationSes` — model for AWS SES bounce/complaint events |
| `sqs` | `SqsQueue` — model for AWS SQS entities |
| `event` | Spring application events: `CreationEvent`, `UpdatingEvent`, `DeletingEvent`, `OrderedProcessEvent` |
| `fn` | `Lambda` — Java 8 functional programming helpers |

### Exception Handling Pattern

All HTTP errors extend `HttpException` (a `RuntimeException`). Constructors accept varargs `args` for i18n message interpolation. `ExceptionHandlerController` (`@ControllerAdvice`) catches these and maps them to structured JSON error responses.

### Key Dependencies

- **Spring Boot 2.0.4 / Spring Cloud Finchley.SR1** — BOM for all Spring dependencies
- **Lombok** — used pervasively for boilerplate reduction (`@Data`, `@Builder`, etc.)
- **Vavr** — functional data types used in some utilities
- **Hibernate JPA Model Generator** — runs during `generate-sources` phase to produce metamodel classes in `target/generated-sources/annotations`
- **Test stack**: JUnit 4, Mockito, PowerMock (for static mocking)
