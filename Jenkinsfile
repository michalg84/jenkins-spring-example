pipeline {
    agent any

    tools {
        maven "M3"
        jdk "JDK17"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/michalg84/jenkins-spring-example.git'
            }
        }

        stage('Build') {
            steps {
                // Run Maven on a Unix agent.
                // sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
        }

//         stage('Test') {
//             steps {
//                 bat 'mvn test'
//             }
//         }

         stage('Deploy to Tomcat') {
            tomcat_host = 'localhost'
            tomcat_port = '8077'
            tomcat_manager_user = 'jenkins-deploy-user'
            tomcat_manager_password = 'jenkins-deploy-pwd'
            context_path = '/webapp'
             steps {


                 // Download WAR file
                 sh "scp -r target/*.war ${tomcat_host}:${tomcat_port}/webapps/${context_path}"

                 // Deploy using Tomcat Manager API (requires JMeter plugin)
                 httpRequest(
                     url: "http://${tomcat_host}:${tomcat_port}/manager/html/undeploy?path=${context_path}",
                     method: 'POST',
                     auth: [ username: tomcat_manager_user, password: tomcat_manager_password ],
                     timeout: 5
                 )

                 httpRequest(
                     url: "http://${tomcat_host}:${tomcat_port}/manager/html/deploy?path=${context_path}&war=/${context_path}",
                     method: 'POST',
                     auth: [ username: tomcat_manager_user, password: tomcat_manager_password ],
                     timeout: 5
                 )
             }
         }
    }
}
