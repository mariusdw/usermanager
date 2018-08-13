This is a short program written for the BlackSwan interview assignment.

The program allows for the creation of users. Each user can be assigned tasks. A scheduled service will periodically
check whether configured tasks should be executed and will print to the console whenever this should happen.

Unit tests were written to provide code coverage for most of the functionality.

Requirements:
=============
Latest Java 8 release installed.

Parameters:
===========
com.github.mariusdw.usermanager.scheduled_task_interval_in_seconds
The configured interval for checking whether a task should be executed is 10 seconds. This can be changed with the
com.github.mariusdw.usermanager.scheduled_task_interval_in_seconds parameter (in application.properties).

spring.jackson.date-format
The date format for request and response dates can be configured. The default is yyyy-MM-dd HH:mm:ss

spring.jackson.time-zone
The timezone for request and response dates can be configured. The default is GMT+02:00

Execution:
==========
To compile the application, change to the source root and run the following command (Linux):
./gradlew clean build

On windows the gradlew.bat file can be used.

An artifact will be produced under <PROJECT ROOT>/build/libs called usermanager-<VERSION>.jar. To start the service,
execute the artifact as follows:

java -jar <PROJECT ROOT>/build/libs/usermanager-<VERSION>.jar

The application will expose a ReST api on port 8080.