pipeline {
    agent any

 environment {
        TOMCAT_USER = credentials('mig')
        TOMCAT_PASS = credentials('123qwe')
        TOMCAT_URL = 'http://localhost:8077/manager/text'
        WAR_FILE = 'your-application-name.war'
    }
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/michalg84/jenkins-spring-example'
            }
        }

        stage('Build') {
            steps {
                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
            // Use the Deploy to Container plugin to deploy to Tomcat
                deploy adapters: [tomcat9(credentialsId: TOMCAT_USER, url: TOMCAT_URL)], contextPath: target, war: '**/*.war'
           }
        }
//             post {
//                 // If Maven was able to run the tests, even if some of the test
//                 // failed, record the test results and archive the jar file.
//                 success {
//                     junit '**/target/surefire-reports/TEST-*.xml'
//                     archiveArtifacts 'target/*.jar'
//                 }
//             }
        }
    }
}
