
void notifySlack(String status, String color )  {
    slackSend(
        color: color, message: status + ' : ' + "${env.JOB_NAME} [${env.BUILD_NUMBER}] (${env.BUILD_URL})"
    )
}

pipeline {
    agent any

    options {
        ansiColor('xterm')
    }

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
                notifySlack('STARTED', '#0000FF')
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

        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('SonarQube-Server') {
                    sh '''
                    ./gradlew sonarqube \
                    -Dsonar.projectKey=jenkins \
                    -Dsonar.host.url=http://34.64.148.169:9000 \
                    -Dsonar.login=2a553e7506c70332bd69f03407b1d3b53730e9b8
                    '''
                }
            }
        }
        stage('SonarQube Quality Gate') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    script {
                        echo 'Start~~~~'
                        def qg = waitForQualityGate()
                        echo "Status: ${qg.status}"
                        if (qg.status != 'OK') {
                            echo "NOT OK Status: ${qg.status}"
                            slackSend(
                                channel: '#ci-테스트-알림',
                                color: '#FF0000',
                                message: 'SonarQube Status Not OK'
                            )

                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        } else {
                            echo "OK Status: ${qg.status}"

                            slackSend(
                                channel: '#ci-테스트-알림',
                                color: '#00FF00',
                                message: 'SonarQube Status OK'
                            )
                        }
                        echo 'End~~~~'
                    }
                }
            }
        }
    }

    post {
        success {
            notifySlack('SUCCESS', '#00FF00')
        }
        unstable {
            notifySlack('UNSTABLE', '#FFFF00')
        }
        failure {
            notifySlack('FAILED', '#FF0000')
        }

        always {
            archiveArtifacts artifacts: 'build/libs/**/*.jar', fingerprint: true
            script {
                def summary = junit testResults: '**/build/test-results/**/*.xml', skipMarkingBuildUnstable: true,
                                skipPublishingChecks: true,
                                healthScaleFactor: 1.0

                slackSend(
                    channel: '#ci-테스트-알림',
                    color: '#007D00',
                    message: """
_BUILD SUMMARY_

*Test Summary* - ${summary.totalCount}

Failures: ${summary.failCount}
Skipped: ${summary.skipCount}
Passed: ${summary.passCount}
                    """
                )
            }
        }
    }
}
