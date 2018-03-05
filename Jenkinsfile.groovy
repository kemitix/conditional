final String mvn = "mvn --batch-mode --update-snapshots"

pipeline {
    agent any
    stages {
        stage('no SNAPSHOT in master') {
            // checks that the pom version is not a snapshot when the current branch is master
            // TODO: also check for SNAPSHOT when is a pull request with master as the target branch
            when {
                expression {
                    (env.GIT_BRANCH == 'master') &&
                            (readMavenPom(file: 'pom.xml').version).contains("SNAPSHOT") }
            }
            steps {
                error("Build failed because SNAPSHOT version")
            }
        }
        stage('Build') {
            parallel {
                stage('Java 8') {
                    steps {
                        withMaven(maven: 'maven 3.5.2', jdk: 'JDK 1.8') {
                            sh "${mvn} clean install"
                        }
                    }
                }
                stage('Java 9') {
                    steps {
                        withMaven(maven: 'maven 3.5.2', jdk: 'JDK 9') {
                            sh 'mvn clean install'
                        }
                    }
                }
            }
        }
        stage('Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
                jacoco exclusionPattern: '**/*{Test|IT|Main|Application|Immutable}.class'
            }
        }
        stage('Archiving') {
            steps {
                archiveArtifacts '**/target/*.jar'
            }
        }
        stage('Quality') {
            steps {
                pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''
            }
        }
        stage('Deploy') {
            when { expression { (env.GIT_BRANCH == 'master') } }
            steps {
                withMaven(maven: 'maven 3.5.2', jdk: 'JDK 9') {
                    sh "${mvn} deploy --activate-profiles release -DskipTests=true"
                }
            }
        }
    }
}
