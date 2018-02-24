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
                sh './mvnw -Dskip-Tests=true -P release -B org.kohsuke:pgp-maven-plugin:sign -Dpgp.secretkey=6F626C45D8B32894'
            }
        }
    }
}
