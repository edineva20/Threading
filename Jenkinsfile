pipeline {

    tools {
        jdk 'linux_jdk1.8.0_172'
        maven 'linux_M3'
    }
    agent any

    stages {
        stage('Compile/Test/Install') {
            steps {
                withMaven(jdk: 'linux_jdk1.8.0_172', maven: 'linux_M3') {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Code Analysis') {
            steps {
                withMaven(jdk: 'linux_jdk1.8.0_172', maven: 'linux_M3') {
                    withSonarQubeEnv('jenkins') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                withMaven(jdk: 'linux_jdk1.8.0_172', maven: 'linux_M3') {
                    sh 'echo mvn deploy'
                }
            }
        }

        stage('Archive artifacts') {
            steps {
                archiveArtifacts artifacts: '**/*.jar',  allowEmptyArchive: true
                archiveArtifacts artifacts: 'target/surefire-reports/*.xml',  allowEmptyArchive: true
            }
        }
    }

    post {
        failure {
            echo 'I will always say Hello again!'
        }
    }
}
