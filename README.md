This is simple Java and Spring Boot project to learn Jenkins pipelines.
Base piplene builds app form github and then deploys to local Tomcat server.

Tomcat (not embedded) properties:
- apache-tomcat-9.0.85\bin\catalina.bat run
- http://localhost:8077/
- example users in tomcat-users.xml:
    - <user username="admin" password="admin" roles="manager-gui"/>
    - <user username="jenkins-deploy-user" password="jenkins-deploy-pwd" roles="manager-script"/>

H2 run command:
java.exe" -jar h2-2.2.220.jar -web

Jenkins run command:
java.exe" -jar Jenkins.war --httpPort=9099