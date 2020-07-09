def responseJson = new URL("http://10.0.0.153:5000/v2/jenkins-test/tags/list")
                    .getText(requestProperties: ['Content-Type': 'application/json']);

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
                sh "echo 'kill -9 \$(ps -ef | grep \"${version}\" | grep -v \"grep\" | awk \'{print \$2}\')'"
                sh "ssh root@10.0.0.155 'docker rm -f jenkins-test && docker run --name jenkins-test -d -p 8080:8080 10.0.0.153:5000/jenkins-test:${version}'"
            }
        }
    }
}