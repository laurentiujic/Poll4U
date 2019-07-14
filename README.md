# Poll4U [![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
> Free organisation of your personal polls

## Table of Contents

- [Overview](#overview)
    - [Technical Details](#technical-details)
- [License](#license)
    - [Forbidden](#forbidden)

## Overview

The idea behind this project is to allow each registered user to create new polls or vote for existing ones. An user would be able to create a new account based on a choosen username and it's email address. This account is just used for authentication and permitts the user to access the full service.

In detail the service is very simple: It includes creating polls with multiple choice options. This means a user is able to set between two and six choices on poll creation time. Each other user can vote for this poll once, by choosing one of the provided options. After the poll is expired, defined by a choosen expiration date, the poll get locked and is from now on just read-only.

### Technical Details

The *RESTful* design is used for structuring the web service, this means all operations are based on plain *HTTP* requests. Even the simplest *HTTP* clients will be able to access this API. The API itself is structured by using different routes. Each of this routes supports the *JSON* media type for consumption and production, this requires that the client is able to deal with *JSON*.

The whole project is based on *Oracle Java 8 (JDK8)* and the *Spring Boot 2.1.6.RELEASE Framework*. Furthermore a lot of additional technologies and libraries are used for getting this service working:

- **Swagger**: Swagger is used for API documentation. For allow easiest as possible client-side development, a goode API documentation is required. Swagger generates the documentation in form of an HTML page hosted by the same base url like the API itself.
- **JJWT**: This service also includes routes dealing with sensitive date. For securing this routes authentication is required, in this case *JSON Web Tokens* are used. For bringing this functionality the [JJWT](https://java.jsonwebtoken.io/) library is used.
- **MapStruct**: For flexibility reasons, this service doesn't work with JPA enities inside the web layer, instead *Data Transfer Objects (DTO)* are used. This brings dynamic but also the requirement to map entity and related DTO. For this [MapStruct](http://mapstruct.org/) is used.

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
