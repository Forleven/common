# common

Forleven common libraries for Spring and the JVM

# Features

- `com.forleven.common.domain`: Base of fields in some tables
- `com.forleven.common.event`: Generic events in spring https://www.baeldung.com/spring-events
- `com.forleven.common.exception`: Basic exceptions to REST APIs https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
- `com.forleven.common.fn`: Sugar and shortcuts to Java 8
- `com.forleven.common.i18n`: Basic abstract on top MessageSource of Spring
- `com.forleven.common.json`: Every serializers to helpful return in Jackson different types
- `com.forleven.common.specification`: Common queries with specification https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/
- `com.forleven.common.sqs`: A simple model to send/receive entities inside AWS SQS
- `com.forleven.common.validation`: Convert bean validations errors to Forleven way JSON
- `com.forleven.common.web`: Forleven way to return entities, errors, links, pagination.

# Libraries

- *vavr*: Try a way more functional programming http://www.vavr.io
- *spring-data-jpa*: This commons is to write APIs with relational databases https://projects.spring.io/spring-data-jpa/
- *spring-cloud-starter-netflix-hystrix*: Think about errors and which paths to fix https://spring.io/guides/gs/circuit-breaker/
- *spring-cloud-starter-openfeign*: Simple REST client to other services or external APIs to integration https://www.baeldung.com/intro-to-feign
- *lombok*: Please forget to write getter/setter and equals methods https://projectlombok.org

# IDE Plugins

- https://github.com/izhangzhihao/intellij-rainbow-brackets
- https://plugins.jetbrains.com/plugin/6317-lombok-plugin
- https://plugins.jetbrains.com/plugin/1065-checkstyle-idea
- https://plugins.jetbrains.com/plugin/7973-sonarlint

# Example

Using a some features of common https://github.com/Forleven/basic-microservice-example
