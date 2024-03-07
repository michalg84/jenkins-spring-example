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
            }
        }

        stage('Report junit') {
            steps {
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
    post {
        always {
            junit keepProperties: true, stdioRetention: 'all', testResults: 'target/surefire-reports/**/*.xml'

j           jacoco (
                execPattern: '**/**.exec',
                classPattern: '**/classes',
                sourcePattern: '**/src/main/java',
                runAlways: true,
                changeBuildStatus : true,
                maximumLineCoverage: '90',
                minimumLineCoverage: '60',
                maximumClassCoverage : '100',
                minimumClassCoverage : '60',
                maximumComplexityCoverage : '90',
                minimumComplexityCoverage : '35',
                maximumMethodCoverage : '100'
                maximumMethodCoverage : '60'
            )
        }
    }
}
