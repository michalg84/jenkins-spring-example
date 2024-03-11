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
                    echo 'Save artifacts'
                    archiveArtifacts artifacts: 'target/app.war', fingerprint: true
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
    post {
        failure {
            mail to: '${params.emailRecipient}',
                 subject: "Build Failure: ${currentBuild.fullDisplayName}",
                 body: "Something is wrong with ${env.BUILD_URL}"
             echo "Failure message sent to ${params.emailRecipient}"
        }
        success {
            mail to: '${params.emailRecipient}',
                 subject: "Build Success: ${currentBuild.fullDisplayName}",
                 body: "App was deployed ${env.BUILD_URL}"
            echo "Success message sent to ${params.emailRecipient}"

        }
    }


}
