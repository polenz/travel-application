# Introduction

# Environment Restrictions

# Remarks to run this application
There is no web interface to access the application. To get started refer to the
Arquillian test case, which by default connects to a camunda BPM Platform running
locally on JBoss AS 7.

# Known Issues
If you use JAVA 6, it is necessary to add the 'webservices-api.jar' to your JDK directory under 'jre/lib/endorsed'. 
You need the jar to generate the Webservice Client classes with maven.

# Improvements Backlog
