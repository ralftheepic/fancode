pipeline {
    agent any

    tools {
        maven 'MAVEN'       
        jdk 'JAVA_HOME'     
    }

    environment {
        BRANCH_NAME = sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
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
                    // Print before executing the build
                    echo "Executing Maven clean package..."
                    sh 'mvn clean package'
                    // Log after the build step completes
                    echo "Build completed for branch: ${env.BRANCH_NAME}"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo "Running tests for branch: ${env.BRANCH_NAME}"
                    if (env.BRANCH_NAME == 'main' || env.BRANCH_NAME == 'develop') {
                        // Log before running the tests
                        echo "Executing Maven test..."
                        sh 'mvn test'
                        // Log after test execution
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
                // Log before deploying
                echo "Deploying main branch build."
                echo "Deployment started for branch: ${env.BRANCH_NAME}"
                // Add actual deployment steps here as needed.
                echo "Deployment completed for branch: ${env.BRANCH_NAME}"
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
