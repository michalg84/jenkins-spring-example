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

        stage('Publish JaCoCo Coverage Report') {
            steps {
                script {
                    // Windows Path handling:
                    def execFile = "${env.WORKSPACE}/**/*.exec" // Pattern for JaCoCo exec files
                    def classFile = "${env.WORKSPACE}/**/*.class" // Pattern for class files
                    def sourceFile = "${env.WORKSPACE}/src/main/java/**/*.java" // Pattern for source files (adjust paths)

                    // Publish JaCoCo report:
                    coverage envVarName: 'JACOCO_REPORT', type: 'jacoco',
                              classFile: classFile,
                              execFile: execFile,
                              sourceFile: sourceFile
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
