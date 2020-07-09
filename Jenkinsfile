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
    }
}