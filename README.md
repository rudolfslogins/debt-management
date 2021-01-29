# Debt Management application

##### Project details
- Spring Boot 2.4.1
- Database: Postgres 13.1
- Testing: Spock Framework

##### Before running project locally, database needs to be set up. Two options available:
- Create Postgres database *debt_management_all*, create user *debt_management* with password *debt_management*\
 and grant ALL privileges for *debt_management_all* db
- Start docker compose: navigate to project root folder using Terminal/cmd/powershell\
 and run command *`docker-compose up`*

Property *`seed-data: true`* in properties file *`src\main\resources\application.yml`* will invoke test data addition\
 on first run.

##### Building and running project
- Navigate to project root folder run *`.\gradlew clean build`* to build project, *`.\gradlew bootRun`* to run project
- Navigate to *`http://localhost:8080/swagger-ui/`* in browser to explore available http methods
- Basic Authentication credentials:\
*username:* user\
*password:* password

