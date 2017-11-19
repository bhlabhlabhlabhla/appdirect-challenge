<img src='https://travis-ci.org/bhlabhlabhlabhla/appdirect-challenge.svg?branch=master'/>

# AppDirect API Integration Challenge


This repository contains source code for AppDirect Coding Challenge project to demonstrate various API integrations with AppDirect marketplace.

Demo application is deployed on heroku at: https://appdirect-js-challenge.herokuapp.com/

### Implemented features:
* oAuth Signature verification
* Event Handling of following types:
  * Subscription Create
  * Subscription Cancel
  * Subscription Change
  * Subscription Notice
  * User Assigned
  * User UnAssigned
  * User Updated

All the above events are handled at common Restful API endpoint at following URL:

https://appdirect-js-challenge.herokuapp.com/api/v1/integration/events?eventUrl={eventUrl}

### Runtime requirements:
* Java 8
* Maven

### Packaging and Installation:

Go to root directory of the project and do the build using maven: 

    mvn clean install
  
Then run with Java:
  
    java -jar target/randomfox-1.0-RELEASE.jar
 

### Frameworks used:
* Spring Boot for Spring ecosystem
* Spring Data for DAO operations 
* Spring oAuth Security for oAuth Signature Verifications
* HSQLDB for In Memory Relational database
* Spring Thymeleaf for templates based UI
* DataTables table plug-in
* jQuery
* Bootstrap


### Please note:
* Server port is customized at run at 8082.
* oAuth Consumer Key and Secret are used from my developers profile.

You can change both of these settings at /src/resources/application.properties file.


