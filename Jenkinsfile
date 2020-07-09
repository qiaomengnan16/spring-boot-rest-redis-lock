pipeline {
    agent any
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Test') {
            steps {
                sh '/home/user-package/apache-maven-3.6.3/bin/mvn clean package'
            }
        }
        stage('Docker Build') {
            steps {
                echo 'Starting to build docker image'
                script {
                    def customImage = docker.build("10.0.0.153:5000/jenkins-test:${new Date().format('yyyy-MM-dd-HH-mm-ss')}")
                    customImage.push()
                }
            }
        }
    }
}