In de Github staat nog een .idea map die een keer per ongeluk is mee gegaan. Ik heb hem eruit geprobeerd te verwijderen dit lukte niet. 

git rm -r .idea         
fatal: pathspec '.idea' did not match any files


https://github.com/MissSayu/StoryQuest2.0.git


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
spring.jpa.hibernate.ddl-auto=update 
spring.jpa.show-sql=true 
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect 
server.port=8081 
spring.datasource.driver-class-name=org.postgresql.Driver 
upload.path=uploads/covers avatar.upload.path=static/avatars 
spring.servlet.multipart.max-file-size=20MB spring.servlet.multipart.max-request-size=20MB 
springdoc.api-docs.path=/api-docs 
springdoc.swagger-ui.path=/swagger-ui 

Optie 2: Via meegeleverde broncode: 
Open IntelliJ IDEA Ga naar open en zoek de source map (StoryQuest2 -> storyquest) 
Klik op Select Folder 
Controleer of application.properties dezelfde instellingen bevat als hierboven. 
