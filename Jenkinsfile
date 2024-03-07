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
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
        }

        stage('Jacoco') {
            steps {
                jacoco (
                    execPattern: '**/**.exec',
                    classPattern: '**/classes',
                    sourcePattern: '**/src/main/java',
                    runAlways: true,
                    changeBuildStatus : false,
//                     maximumLineCoverage: '90',
                    minimumLineCoverage: '60',
//                     maximumClassCoverage : '100',
                    minimumClassCoverage : '60',
//                     maximumComplexityCoverage : '90',
                    minimumComplexityCoverage : '30',
//                     maximumMethodCoverage : '100',
                    minimumMethodCoverage : '60',
//                     maximumBranchCoverage : '100',
                    minimumBranchCoverage : '1',
//                     maximumInstructionCoverage : '100',
                    minimumInstructionCoverage : '1'
                )
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
        }
    }
}
