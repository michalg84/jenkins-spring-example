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
            echo 'BUILD'
                bat "mvn -B -DskipTests clean package"
            }
        }

        stage('Test') {
            steps {
                echo 'TEST'
                bat "mvn test"
            }
             post {
                always {
                    echo 'Publishing JUnit test results'
                    junit (
                        keepProperties: true,
                        stdioRetention: 'all',
                        testResults: 'target/surefire-reports/**/*.xml'
                    )
                }
            }
        }

        stage('Jacoco') {
            steps {
                jacoco (
                    execPattern: '**/**.exec',
                    classPattern: '**/classes',
                    sourcePattern: '**/src/main/java',
                    runAlways: true,
                    changeBuildStatus : true,
                    maximumLineCoverage: '80',
                    minimumLineCoverage: '50',
                    maximumClassCoverage : '80',
                    minimumClassCoverage : '50',
                    maximumComplexityCoverage : '33',
                    minimumComplexityCoverage : '25',
                    maximumMethodCoverage : '60',
                    minimumMethodCoverage : '50',
                    maximumBranchCoverage : '1',
                    minimumBranchCoverage : '0',
                    maximumInstructionCoverage : '30',
                    minimumInstructionCoverage : '20'
                )
            }
        }


        stage('Deploy') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    retry(3) {
                         deploy adapters: [tomcat9(credentialsId: 'Tomcat9Credentials', url: 'http://localhost:8077/')],
                                                contextPath: 'webapp',
                                                war: '**/*.war'
                    }
                }

            }
        }
    }


}
