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
            steps {
           

                // Download WAR file
                bat "copy target/*.war localhost:8077/webapps/webapp"

                // Deploy using Tomcat Manager API (requires JMeter plugin)
                httpRequest(
                    url: "http://localhost:8077/manager/html/undeploy?path=wabapp",
                    method: 'POST',
                    auth: [ username: "jenkins-deploy-user", password: "jenkins-deploy-pwd" ],
                    timeout: 5
                )

                httpRequest(
                    url: "http://localhost:8077/manager/html/deploy?path=wabapp&war=/wabapp",
                    method: 'POST',
                    auth: [ username: "jenkins-deploy-user", password: "jenkins-deploy-pwd" ],
                    timeout: 5
                )
            }
        }
    }
}
