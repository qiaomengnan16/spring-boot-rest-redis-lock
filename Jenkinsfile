pipeline {
    agent any
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}