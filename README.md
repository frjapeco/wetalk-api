# wetalk-server

Backend server for wetalk chat application. It uses STOMP websocket protocol to achieve real time communication between server and subscribers. [More info about Spring & STOMP](https://docs.spring.io/spring/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/websocket.html#websocket-stomp-overview)

## Prerequisites

[Java JDK 1.8](https://www.oracle.com/technetwork/java/javaee/downloads/index.html) or higher.

[Maven 3.5.2](https://maven.apache.org/download.cgi) or higher.

## Installation

1. Clone project `git clone https://github.com/fjperezcostas/wetalk-api.git`
2. Move into the project's folder `/wetalk-api`.
3. Package the project `mvn package`.
4. Run jar file `java -jar target/wetalk-api-1.0-SNAPSHOT.jar`.

## API description:

**ws://localhost:8080/chat**<br/>
STOMP server URL (local environment example)

**/app/chat/startup**<br/>
Topic which gives all necessary data to boot the app.

**/topic/chat/home**<br/>
Topic which permits subscribers receive all messages sent to 'home' channel.

**/user/queue/chat/private**<br/>
Topic which permits subscribers receive all private messages sent to concrete user.

**/topic/chat/login**<br/>
Topic which informs who is logging at the moment.

**/topic/chat/logout**<br/>
Topic which informs who is disconnecting at the moment.

## Predefined users:

It uses an embedded h2 database to store info about users and roles.

Username | Password | Role
---------|----------|------
jon.snow | 123456 | ROLE_ADMIN
daenerys.targaryen | 123456 | ROLE_USER
tyrion.lannister | 123456 | ROLE_USER
khal.drogo | 123456 | ROLE_USER
