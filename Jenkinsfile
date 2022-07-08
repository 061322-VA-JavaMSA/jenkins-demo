pipeline {
    agent any

    stages {
        stage('checkout') {
            steps {
                script{
                    properties([pipelineTriggers([githubPush()])])
                }
                git branch: 'main', url: 'https://github.com/061322-VA-JavaMSA/jenkins-demo'
            }
        }
        stage('clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}