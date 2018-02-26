def maven(goals, modules, profiles) {
    withMaven(maven: 'maven 3.5.2', jdk: 'JDK 1.8') {
        sh "mvn -U $profiles $modules $goals"
    }
}

def isBranch(branch) {
    return env.GIT_BRANCH == branch
}

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
                maven("clean install", ".", "")
            }
        }
        stage('Deploy') {
            when { expression { isBranch 'master' } }
            steps {
                maven("deploy", allModules, "-P release")
            }
        }
    }
}
