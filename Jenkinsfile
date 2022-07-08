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
        
        stage('remove previous image if exists') {
            steps {
                sh 'docker rmi -f kth844/task-manager || true'
            }
        }

        stage('create image') {
            steps {
                sh 'docker build -t kth844/task-manager .'
            }
        }

        stage('remove previous container if exists') {
            steps {
                sh 'docker stop task-app || true'
            }
        }

        stage('create container') {
            steps {
                sh 'docker run -d -p 80:8080 --name task-app --rm kth844/task-manager'
            }
        }
    }
}