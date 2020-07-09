def responseJson = new URL("http://k8s.n2:5000/v2/jenkins-test/tags/list").getText(requestProperties: ['Content-Type': 'application/json']);

println(responseJson)

Map response = new groovy.json.JsonSlurperClassic().parseText(responseJson) as Map;

def versionStr = response.tags.join('\n');

pipeline {
    agent any

    stages {
        stage('Test') {
            input {
                message "Choose a version"
                ok "Deploy"
                parameters {
                    choice(choices: versionStr , description: 'version' , name: 'version')
                }
            }
            steps {
                sh "ssh root@k8s.n3 'docker rm -f jenkins-test && docker run --name jenkins-test -p 8080:8080 k8s.n2:5000/jenkins-test:${version}'"
            }
        }
    }
}