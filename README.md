<h2>This is simple Java and Spring Boot project to be used for Jenkins pipelines learning.</h2>
Base pipeline builds app form github, runs tests and then deploys to local Tomcat server.

1. **Tomcat** (not embedded) properties:
   - add example users in tomcat-users.xml:
       - user username="admin" password="admin" roles="manager-gui"
       - user username="jenkins-deploy-user" password="jenkins-deploy-pwd" roles="manager-script"
   - run tomcat wwith .bat: apache-tomcat-9.0.85\bin\catalina.bat run
   - http://localhost:8077/manager


2. **H2** run command:
   - java.exe" -jar h2-2.2.220.jar -web

3. **Jenkins** run command:
   - java.exe" -jar Jenkins.war --httpPort=9099
   - configure Tomcat9Credentials with username="jenkins-deploy-user" password="jenkins-deploy-pwd"
   - create new pipeline feching from github repo and Jenkinsfile
   - Start pipeline