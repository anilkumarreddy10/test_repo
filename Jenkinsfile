pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "mvn"
    }
    parameters {
        booleanParam(
            name: 'RELEASE',
            defaultValue: false,
            description: 'Create Maven release'
        )
    }

    stages {
        stage('checkout') {
            steps {
                git credentialsId: 'github-ssh-key',
                  url: 'git@github.com:anilkumarreddy10/test_repo.git'
            }
        }
        stage('Build and Deploy') {
            steps {
                configFileProvider([
                    configFile(fileId: 'settings.xml', variable: 'SETTINGS')
                ]) {
                    script {
                        if (params.RELEASE) {
                        echo 'Running RELEASE build'
                        sh 'mvn -B release:clean release:prepare release:perform -s ${SETTINGS}'
                        } else {
                            echo 'Running SNAPSHOT build'
                            sh 'mvn clean deploy'
                        }
                    }
                }
            }
        }
    }
}
