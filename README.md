# Desktop Application for retrieving user data from the Fitbit API

## Ιn a few words
Fitbit App for retrieving User Data from the Fitbit API and sending e-mail for Heart-rate information according to the user selection.

## Implementation
The project is a Spring Boot application partially based on Java Spring framework, Vaadin and Java 8 and built with Maven. The databases used are MongoDB for the stored data and RedisDB for other usefull information needed in auth processes.

## Trying it out
First you have to clone or download the project from github to your workspace "git clone https://github.com/NikosMas/Fitbit_Data_Project.git"

Then you have to start mongo service and redis service required:
on linux ("sudo systemctl start mongod", "sudo systemctl start redis")
on windows just run the mongod and redis services.
   
Then tou have to open the project to your IDE and go to "/gradFit/src/main/resources/application.yml" and put your information about mongo, mail and fitbit client properties. 

Finally you run the application as Spring Boot Application from the IDE or by cmd with "./mvnw spring-boot:run" .

## Suggested moves
Open your browser to "localhost:8080/fitbitApp/dashboard" and create the collections, complete the credentials required taken from the "dev.fitbit.com". After finish this click to go to the user data retreiving proccess.

You are redirected to "localhost:8080/fitbitApp/userData" where you have to choose the start date & end date,  the category of data you want to receive. The next step is clicking to redirect at "localhost:8080/fitbitApp/heartRateFilter" but this is possible only if you have selected for downloading heart rate data.

At "localhost:8080/fitbitApp/heartRateFilter" you have to fill the required fields for e-mail and heart rate details.


The application is now completed.


   
