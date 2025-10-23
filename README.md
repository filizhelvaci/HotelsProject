# HOTEL RESERVATION SYSTEM APPLICATION

Tech Stack

_*Framework*_
 * _Core_
   - *Spring*
      + Spring Boot 3
      + Spring Boot Dev Tools
      + Spring Web
      + Spring Boot Actuator
      + Spring Security 6
   - *Spring Data*
      + Spring Data JPA

_*3rd Party Dependencies*_
* _Lombok_
* _Mapstruct_
    
_*Database*_
* _PostgreSql_

_*Language*_
* _Java 17_

_*Build Tool*_
* _Gradle_

_*Version Control*_
* _Git_
* _GitHub_
 
_*APIs Interaction Platform*_
* _Postman_

---

## Project Documents
For other information, you can check [Wiki](https://github.com/filizhelvaci/HotelsProject/wiki) Page.

## Postman
[Documentation](https://documenter.getpostman.com/view/32358530/2sAYJ7exws) & [Workspace](https://www.postman.com/filizkh/workspace/hotel-reserve)

## Running All Components as Container on Docker

The following command can be executed to stand up the application and database on Docker. Then the application can be run and proceed.

`docker compose up -d --build`

The following command can be used to remove Docker Containers.

`docker compose down -v`

## Running PostgreSQL Container on Docker

The following command can be executed to stand up the database on Docker. Then the application can be run and proceed.

`docker compose up -d --build database`

The following command can be used to remove Docker Containers.

`docker compose down -v`

