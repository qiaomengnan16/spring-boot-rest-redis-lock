pipeline {
    agent any
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Test') {
            steps {
                sh 'ssh root@10.0.0.151'
                sh 'qiaohao'
            }
        }
    }
}