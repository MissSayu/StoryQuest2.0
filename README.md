Korte instalatie handleiding (backend only, verdere informatie te vinden in projectmap)

## Backend: 

Er zijn 2 manieren om de backend te installeren en te starten: 

Optie 1: 

Via Github Open IntelliJ IDEA 
Ga naar File -> New -> Project from Version Control 

Plak de Github-link van het project in: https://github.com/MissSayu/StoryQuest2.0.git 
Klik op Clone. 
Controleer vervolgens de instellingen in application.properties 

spring.datasource.url=jdbc:postgresql://localhost:5432/storyquest
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.database=postgresql
spring.sql.init.platform=postgres
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
spring.datasource.driver-class-name=org.postgresql.Driver
server.port=8081
upload.path=uploads/covers
avatar.upload.path=static/avatars
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=20MB
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui
jwt.secret=storyquest-secret-key-storyquest-secret-key-123456
jwt.expiration=3600000

Optie 2: Via meegeleverde broncode: 
Open IntelliJ IDEA Ga naar open en zoek de source map (StoryQuestBackend)
Klik op Select Folder 
Controleer of application.properties dezelfde instellingen bevat als hierboven. 

Database configuratie:

Open Database Tool Window
Rechts in IntelliJ zie je een Database-tab 

Klik op: Data Source en selecteer PostgreSQL

Vul dezelfde gegevens als in application.properties:

Host: localhost
Port: 5432
Database: storyquest
User: postgres
Password: password

Als IntelliJ vraagt om een driver:

klik op Download en wacht tot deze geïnstalleerd is

Klik op Test Connection
Als alles goed is: krijg je "Successful"
Klik op OK

Start de applicatie via de Run knop in IntelliJ.
Controleer of de database-instellingen in application.properties correct zijn.
Wacht tot de melding verschijnt:
Started StoryQuestApplication in X seconds

De backend draait nu op http://localhost:8081 
