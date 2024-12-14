pipeline {
    agent any

    tools {
        maven 'MAVEN'       
        jdk 'JAVA_HOME'     
    }

    environment {
        BRANCH_NAME = bat(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "Checking out branch: ${env.BRANCH_NAME} from GitHub repository"
                    // Checkout from the new GitHub repository
                    git branch: "${env.BRANCH_NAME}", url: 'https://github.com/ralftheepic/fancode.git'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "Building branch: ${env.BRANCH_NAME}"
                    bat 'mvn clean package' // Avoid using nohup
                    echo "Build completed for branch: ${env.BRANCH_NAME}"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo "Running tests for branch: ${env.BRANCH_NAME}"
                    if (env.BRANCH_NAME == 'main' || env.BRANCH_NAME == 'develop') {
                        bat 'mvn test'
                        echo "Tests completed for branch: ${env.BRANCH_NAME}"
                    } else {
                        echo "Skipping tests for branch: ${env.BRANCH_NAME}"
                    }
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                echo "Deploying main branch build."
                // Add actual deployment steps here as needed.
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution complete.'
        }
        success {
            echo "Build successful for branch: ${env.BRANCH_NAME}"
        }
        failure {
            echo "Build failed for branch: ${env.BRANCH_NAME}"
        }
    }
}
