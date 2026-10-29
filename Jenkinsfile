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
              sshagent(credentials: ['github-ssh-key']) {
                configFileProvider([
                    configFile(fileId: 'settings.xml', variable: 'SETTINGS')
                ]) {
                    script {
                        if (params.RELEASE) {
                        echo 'Running RELEASE build'
                        sh 'mvn -B release:clean release:prepare release:perform -s ${SETTINGS}'
                        } else {
                            echo 'Running SNAPSHOT build'
                            sh 'mvn -s "$SETTINGS" clean deploy'
                        }
                    }
                }
              }
            }
        }

        stage('sonarqube & Quality Gate') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.skipCoverage=true'
                }
                timeout(time: 4, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('docker') {
            steps {
                sh 'docker build -t gcr.io/project-b7d113bc-47ff-4eb4-827/test-repo:${BUILD_NUMBER}'
            }
        }
    }
}
