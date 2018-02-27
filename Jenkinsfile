pipeline {
    agent any
    stages {
        stage('Prepare') {
            steps {
                git url: 'git@github.com:kemitix/condition.git',
                        branch: '**',
                        credentialsId: 'github-kemitix'
            }
        }
        stage('Build') {
            steps {
                withMaven(maven: 'maven 3.5.2', jdk: 'JDK 1.8') {
                    sh "mvn -B -U clean install"
                }
            }
        }
        stage('Deploy') {
            when { expression { (env.GIT_BRANCH == 'master') } }
            steps {
                withMaven(maven: 'maven 3.5.2', jdk: 'JDK 1.8') {
                    sh "mvn -B -U -P release deploy"
                }
            }
        }
    }
}
