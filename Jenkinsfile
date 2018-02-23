pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '**']],
                    extensions: [[$class: 'CleanBeforeCheckout']],
                    userRemoteConfigs: [[credentialsId: 'github-kemitix', url: 'git@github.com:kemitix/conditional.git']]
                ])
                sh './mvnw clean install'
                junit '**/target/surefire-reports/*.xml'
                archiveArtifacts '**/target/*.jar'
            }
        }
        stage('Publish Coverage') {
            steps {
                sh 'curl -s https://codecov.io/bash | bash -s || echo "Codecov did not collect coverage reports"'
                sh './mvnw test jacoco:report coveralls:report'
            }
        }
        stage('Deploy') {
            steps {
                sh './mvnw -Dskip-Tests=true -P release -B deploy'
            }
        }
    }
}
