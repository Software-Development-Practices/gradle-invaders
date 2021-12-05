void notifySlack(String status, String color)  {
    slackSend(
        color: color, message: status + ' : ' + "${env.JOB_NAME} [${env.BUILD_NUMBER}] (${env.BUILD_URL})"
    )
}

/* groovylint-disable-next-line CompileStatic */
pipeline {
    agent any

    options {
        ansiColor('xterm')
    }

    tools {
        gradle 'gradle 7.1'
    }
    notifySlack('STARTED', '#0000FF')

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
            script {
                def summary = junit(
                    $class: 'JUnitResultArchiver',
                    testResults: '**/build/test-results/**/*.xml',
                    skipMarkingBuildUnstable: true,
                    skipPublishingChecks: true,
                    healthScaleFactor: 1.0
                )

                publishHTML(target: [
                            allowMissing: false,
                            alwaysLinkToLastBuild: false,
                            keepAll: true,
                            reportDir: 'build/reports/tests/test',
                            reportFiles: 'index.html',
                            reportName: 'Junit Report'
                ])

                def causes = currentBuild.rawBuild.getCauses()

                slackSend(
                    channel: '#ci-테스트-알림',
                    color: '#007D00',
                    message: """
                    ${summary}

                    BUILD #${currentBuild.number}
                    causes: ${causes}

                    *Test Summary* - ${summary.totalCount},
                    Failures: ${summary.failCount}
                    Skipped: ${summary.skipCount}
                    Passed: ${summary.passCount}
                    """
                )
            }
        }
    }
}
