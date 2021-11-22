pipeline {
    agent { docker { image 'maven:3.8.3' } }
    stages {
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

    }
}