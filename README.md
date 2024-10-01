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
# ROOM SERVICE
## DATA MODEL ANALYSIS

| TABLE_NAME | FIELDS  | 
| ---------- | ------- |
| ROOM    | ID , NUMBER , FLOOR , STATUS , TYPE_ID |
| ROOM_TYPE | ID , NAME , PRICE , PERSON_COUNT , SIZE , DESCRIPTION |
| ROOM_TYPE_ASSET | ID , ROOM_TYPE_ID , ASSET_ID |
| ASSET | ID , NAME , PRICE , IS_DEFAULT |  
