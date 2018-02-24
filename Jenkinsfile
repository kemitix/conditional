pipeline {
    agent any
    stages {
        stage('Prepare') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '**']],
                    extensions: [[$class: 'CleanBeforeCheckout']],
                    userRemoteConfigs: [[credentialsId: 'github-kemitix', url: 'git@github.com:kemitix/conditional.git']]
                ])
            }
        }
        stage('Build') {
            steps {
                sh './mvnw -B -U clean install'
            }
        }
        stage('Reporting') {
            steps {
                junit '**/target/surefire-reports/*.xml'
                archiveArtifacts '**/target/*.jar'
            }
        }
        stage('Deploy') {
            when {
                expression {
                    env.GIT_BRANCH == 'master'
                }
            }
            steps {
                sh './mvnw -B -Dskip-Tests=true -P release deploy'
            }
        }
    }
}
