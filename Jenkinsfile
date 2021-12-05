pipeline {
    agent any

    tools {
        gradle 'gradle 7.1'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', credentialsId: 'minsoo',
                url: 'https://github.com/Software-Development-Practices/gradle-invaders.git'
            }
        }
        stage('Build') {
                steps {
                    sh '''
                chmod +x gradlew
                ./gradlew build -x test
                '''
                }
        }
        stage('Test') {
            steps {
                script {
                    try {
                        sh './gradlew test'
                    } catch (error) {
                        echo 'Test Failed'
                    }
                }
            }
        }
    }
}
