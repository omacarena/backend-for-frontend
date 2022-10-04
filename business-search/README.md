# business-search

## Quick Start

- start the `BusinessSearchApplication` using your IDE or using Gradle - `./gradlew bootRun -i`
- go to `Postman`, `curl`, ... and make some requests:
  - successful request #1 - http://localhost:8080/business/GXvPAor1ifNfpF0U5PTG0w
  - successful request #2 - http://localhost:8080/business/ohGSnJtMIC5nPfYRi_HTAga
  - failed request #1 - key not found - http://localhost:8080/business/noop
  - Swagger UI - http://localhost:8080/swagger
  - OpenAPI - http://localhost:8080/open-api

## Tech

- `JDK 17`
- `Gradle` - for build
- `IntelliJ Utlimate 2022.x.x` - as IDE, but any other IDE will work too
- `Spring Boot 2.7.4`
- `spring-cloud-starter-openfeign` - for Feign Clients
- `springdoc-openapi-ui` - for OpenAPI and Swagger UI
- `Jackson` - for JSON and XML SerDe
- `JUnit 5`, `AssertJ`, `Mockito`, `MockMvc`, `WireMock` - for tests
- `Lombok` - to eliminate some boilerplate

## Local Development

| Command                   | Description                                                              |
|---------------------------|--------------------------------------------------------------------------|
| `./gradlew build`         | Builds current project                                                   |
| `./gradlew build -x test` | Builds without running tests.                                            |
| `./gradlew test`          | Runs tests                                                               |
| `./gradlew bootRun -i`    | Runs the SpringBoot application and outputs the logs written to console. |

## Resources

| Link                                                                                                                                               | Description                                                                                                            |
|----------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| [Spring Initializr](https://start.spring.io/)                                                                                                      | Current project was initialized using `Spring Initializr` IntelliJ project template.                                   |
| [SpringDoc](https://www.baeldung.com/spring-rest-openapi-documentation)                                                                            | Exposes API documentation using `OpenAPI v3` and `Swagger UI`.                                                         |
| [Jackson XML SerDe](https://www.baeldung.com/jackson-xml-serialization-and-deserialization)                                                        | XML Deserialization using Jackson. Useful for deserializing remote API errors which content type is `application/xml`. |
| [Jackson XML SerDe using JAXB Annotations](https://stackoverflow.com/questions/68831792/xmlmapper-deserializing-xmlproperty-as-xmlelement)         | Add support to Jackson `XmlMapper` to consider JAXB annotations.                                                       |
| [Spring Cloud Contract Wiremock](https://docs.spring.io/spring-cloud-contract/docs/current/reference/html/project-features.html#features-wiremock) | Bootstrapping Wiremock in order to stub request-responses and be able to achieve contract testing for feign clients.   |
| [Change Faign Client URL in Tests](https://stackoverflow.com/questions/43733569/how-can-i-change-the-feign-url-during-the-runtime)                 | Testing feign clients require to point them to the location where WireMock stubs listen for requests.                  |
| [Configure MockMvc](https://spring.io/guides/gs/testing-web/)                                                                                      | Configure MockMvc in order to test interaction with the REST controllers                                               |
