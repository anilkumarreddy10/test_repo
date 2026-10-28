pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "mvn"
    }

    stages {
        stage('checkout') {
            steps {
                // Get some code from a GitHub repository
                git 'git@github.com:anilkumarreddy10/test_repo.git'
            }
        }
        stage('Build') {
            steps {
                // Run Maven on a Unix agent.
                sh "mvn clean package"
            }

        }
    }
}
