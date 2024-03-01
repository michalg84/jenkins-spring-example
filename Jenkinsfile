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
                // Define variables within the steps section (local scope)
                def tomcat_host = 'localhost'
                def tomcat_port = '8077'
                def context_path = '/webapp'

                // Download WAR file
                sh "scp -r target/*.war ${tomcat_host}:${tomcat_port}/webapps/${context_path}"

                // Deploy using Tomcat Manager API (requires JMeter plugin)
                httpRequest(
                    url: "http://${tomcat_host}:${tomcat_port}/manager/html/undeploy?path=${context_path}",
                    method: 'POST',
                    auth: [ username: 'jenkins-deploy-user', password: 'jenkins-deploy-pwd' ], // Replace with actual credentials or reference from Jenkins Credentials Store
                    timeout: 5
                )

                httpRequest(
                    url: "http://${tomcat_host}:${tomcat_port}/manager/html/deploy?path=${context_path}&war=/${context_path}",
                    method: 'POST',
                    auth: [ username: 'jenkins-deploy-user', password: 'jenkins-deploy-pwd' ], // Replace with actual credentials or reference from Jenkins Credentials Store
                    timeout: 5
                )
            }
        }
    }
}
