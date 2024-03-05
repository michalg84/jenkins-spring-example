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
                // Run Maven on a Unix agent:
                // sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent:
                bat "mvn -Dmaven.test.failure.ignore=true clean package -Dspring.profiles.active=test"
                 post {
                        success {
                            jacoco(
                                execPattern: '**/build/jacoco/*.exec',
                                classPattern: '**/build/classes/java/main',
                                sourcePattern: '**/src/main'
                            )
                        }
                    }
            }
        }



        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'Tomcat9Credentials', url: 'http://localhost:8077/')],
                        contextPath: 'webapp',
                        war: '**/*.war'
            }
        }
    }
}
