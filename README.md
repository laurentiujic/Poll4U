# Poll4U [![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
> Free organisation of your personal polls

## Table of Contents

- [Overview](#overview)
- [Development](#development)
    - [Project Structure](#project-structure)
    - [Getting Started](#getting-started)
        - [Profiles](#profiles)
        - [Persistence](#persistence)
- [License](#license)
    - [Forbidden](#forbidden)
- [Appendix](#appendix)

## Overview

The idea behind this project is to allow each registered user to create new polls or vote for existing ones. An user would be able to create a new account based on a choosen username and it's email address. This account is just used for authentication and permitts the user to access the full service.

In detail the service is very simple: It includes creating polls with multiple choice options. This means a user is able to set between two and six choices on poll creation time. Each other user can vote for this poll once, by choosing one of the provided options. After the poll is expired, defined by a choosen expiration date, the poll get locked and is from now on just read-only.

## Development

The project is based on a opinionated view of the [Spring](https://spring.io/) platform and it's third party libraries, known as [Spring Boot](https://spring.io/projects/spring-boot). For building the project and managing dependencies the famous [Apache Maven](https://maven.apache.org/) build autonomation tool is used. In detail the project is developed with *Oracle Java 8 (JDK8)* and should be compatible with newer versions.

### Project Structure

```bash
.
/---src
|   /---main
|   |   /---java
|   |   |   /---org.x1c1b.poll4u
|   |   |       /---config
|   |   |       /---dto
|   |   |       |   /---mapper
|   |   |       /---error
|   |   |       /---model
|   |   |       |   /---audit
|   |   |       /---repository
|   |   |       /---security
|   |   |       /---service
|   |   |       |   /---impl
|   |   |       /---web
|   |   |       |   /---auth
|   |   |       +---Poll4UApplication.java
|   |   /---resources
|   |       +---application.properties
|   |       +---application-test.properties
|   |       +---application-prod.properties
|   |       +---banner.txt
|   /---test
|
+---Procfile
+---pom.xml
```
The Poll4U service is divided into three logical layers: *Web Layer*, *Service Layer*, *Repository Layer*. This are the basis of the project architecture and all other components are arranged around or somewhere between.

### Getting Started

The project is developed as a Spring Boot standalone application. This means it's deployed as a executable *JAR* with embedded *Tomcat Web Server* instead of a traditional *WAR*. The result is that **no** application server is required for executing the service. The whole project is based on Oracle Java 8 (JDK8) and the Spring Boot 2.1.6.RELEASE Framework. This requires an installed *JDK8* or a newer version.

#### Profiles

Moreover different profiles are used, e.g. for testing (`application-test.properties`) or for production (`application-prod.properties`). In addition a local development profile (`application-dev.properties`) is used, that isn't part of the repository. So first it's required\* to configure the development profile:

```properties
# ----------------------------------------
# DATA PROPERTIES
# ----------------------------------------

# DATASOURCE
spring.datasource.url=<URL>
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>

# JPA
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update

# ----------------------------------------
# WEB PROPERTIES
# ----------------------------------------

# EMBEDDED SERVER CONFIGURATION
server.port=<PORT>

# ----------------------------------------
# Poll4U PROPERTIES
# ----------------------------------------

# Security
poll4u.security.secret=<SECRET>
```
This is a minimal development profile. Of course the wildcards **must** be replaced by sensible values.

>**Please note:** Spring loads the default `application.properties` file by default, that isn't complete. This means the service is only runable if the default config file is combined with a specification (e.g. `test`, `prod`, `dev`). For setting the environment/profile the environment variable `POLL4U_PROFILE` must be set, for example `POLL4U_PROFILE=dev`.

*\*Instead of course the production or testing profile could be used*

#### Persistence

This project uses different data sources (databases) dependend to the profile/environment. The databases for the testing and production environment are preset: The testing environment uses `H2` and the production environment `PostgreSql`. The database used inside of the development environment is very flexible, this means each developer can use it's own prefered database, as long it's an SQL database supported by `Hibernate ORM` and the driver dependencies are in the classpath means declared inside of `pom.xml`.

Like any other properties the data source is configured inside of the related profile, for example the `MySql` database for the development environment:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/poll4u?useSSL=false&serverTimezone=UTC
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>
```
With the declared dependency inside of `pom.xml`:
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

>**Please note:** Creating schemas or tables manually isn't required. The service creates the tables automatically if they doesn't exist. Required are a working database installation and the required privileges for reading and writing.

## License

Copyright (c) 2019 0x1C1B

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

[MIT License](https://opensource.org/licenses/MIT) or [LICENSE](LICENSE) for
more details.

### Forbidden

**Hold Liable**: Software is provided without warranty and the software
author/license owner cannot be held liable for damages.

## Appendix

`application-dev.properties`: Opinionated development profile
```properties
# ----------------------------------------
# DATA PROPERTIES
# ----------------------------------------

# DATASOURCE
spring.datasource.url=jdbc:mysql://localhost:3306/poll4u?useSSL=false&serverTimezone=UTC
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>

# JPA
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect

# ----------------------------------------
# WEB PROPERTIES
# ----------------------------------------

# EMBEDDED SERVER CONFIGURATION
server.port=3000

# ----------------------------------------
# Poll4U PROPERTIES
# ----------------------------------------

# Security
poll4u.security.secret=<SECRET>
```
