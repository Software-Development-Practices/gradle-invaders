pipeline {
    agent any

    tools {
        gradle 'gradle'
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
                ./gradlew build
                '''
            }
        }
    }
}