pipeline {
    agent any
    triggers {
        pollSCM(cron('* * * * *'))
    }
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}