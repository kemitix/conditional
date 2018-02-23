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
        stage('Deploy') {
            steps {
                sh 'export GPG_TTY=$(tty) ./mvnw -Dskip-Tests=true -P release -B deploy'
            }
        }
    }
}
