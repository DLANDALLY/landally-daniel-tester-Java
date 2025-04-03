# Parking System
A command line app for managing the parking system. 
This app uses Java to run and stores the data in Mysql DB.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

- Java 1.8
- Maven 3.6.2
- Mysql 8.0.17

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install MySql:

https://dev.mysql.com/downloads/mysql/

After downloading the mysql 8 installer and installing it, you will be asked to configure the password for the default `root` account.
This code uses the default root account to connect and the password can be set as `rootroot`. If you add another user/credentials make sure to change the same in the code base.

### Running App

Post installation of MySQL, Java and Maven, you will have to set up the tables and data in the data base.
For this, please run the sql commands present in the `Data.sql` file under the `resources` folder in the code base.

Finally, you will be ready to import the code into an IDE of your choice and run the App.java to launch the application.

### Testing

The app has unit tests and integration tests written. More of these need to be added and in some places that can be seen mentioend as `TODO` comments. The existing tests need to be triggered from maven-surefire plugin while we try to generate the final executable jar file.

To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

`mvn test`

### Main Branch

Daniel is the main branch

https://github.com/DLANDALLY/landally-daniel-tester-Java/tree/daniel

### Rapport JaCoCo

![Capture d’écran 2025-04-03 à 12.45.12.png](..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F80%2Fx9v0qvy17vsf3l66l7zt13wm0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_UhouhO%2FCapture%20d%E2%80%99%C3%A9cran%202025-04-03%20%C3%A0%2012.45.12.png)

### Rapport Surefire

![Capture d’écran 2025-04-03 à 12.46.31.png](..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F80%2Fx9v0qvy17vsf3l66l7zt13wm0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_aSZ1Wz%2FCapture%20d%E2%80%99%C3%A9cran%202025-04-03%20%C3%A0%2012.46.31.png)