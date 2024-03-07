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
                junit keepProperties: true, stdioRetention: 'all', testResults: 'target/surefire-reports/TEST-com.galka.jenkinsspringexample.ControllerTest.xml'
            }
        }

        stage('Report Jacoco') {
            steps {
                jacoco(
                    execPattern: '**/**.exec',
                    classPattern: '**/classes',
                    sourcePattern: '**/src/main/java',
                    changeBuildStatus : true,
                    minimumBranchCoverage: '20',
                    minimumClassCoverage : '20',
                )

            }
        }







//         stage('Test') {
//             steps {
//                 bat 'mvn test'
//             }
//         }

        stage('Deploy') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'Tomcat9Credentials', url: 'http://localhost:8077/')],
                        contextPath: 'webapp',
                        war: '**/*.war'
            }
        }
    }
}
