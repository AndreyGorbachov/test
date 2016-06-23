#Test task for the company "EPAM Systems".

#####The project is designed for viewing and editing departments and staff working in these departments.
#####The project consists of two independent web applications: web services and a web client application that uses this Web service to display and change the database state. Both modules are published on Tomcat and work in tandem.

#####To install the application you need:
1.  Download the project to your computer
2.  Open the console in the project root and run the following commands:
	1. mvn package
	2. mysql -uusername -ppassword < rest/db/create-db.sql
	3. mysql -uusername -ppassword < rest/db/insert-data.sql
3.  Copy the war-files from "rest/target" and "client/target" in "\<TOMCAT_HOME\>/webapps"
4.  Start tomcat

###### Note: To change the settings for connecting to the database edit the file: rest/src/main/resources/db.properties.

###### Rest service is available at: http://localhost:8080/rest
###### Rest client is available at: http://localhost:8080/client
