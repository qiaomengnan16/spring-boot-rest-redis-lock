pipeline {
    agent any
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Test') {
            steps {
                sh '/home/user-package/apache-maven-3.6.3/bin/mvn clean package'
                sh 'scp target/spring-boot-rest-redis-lock-1.0-SNAPSHOT.jar root@10.0.0.153:/root && cd /root && java -jar spring-boot-rest-redis-lock-1.0-SNAPSHOT.jar'

            }
        }
    }
}