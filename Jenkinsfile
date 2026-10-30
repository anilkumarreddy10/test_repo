pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "mvn"
    }
    environment {
        IMAGE_NAME = "us-central1-docker.pkg.dev/project-b7d113bc-47ff-4eb4-827/test-repo/test-app"
        CLUSTER_NAME = 'my-cluster'
        REGION = 'us-central1'
        
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
        stage('Check Java Maven') {
            steps {
                sh '''
                    echo "===== JAVA_HOME ====="
                    echo $JAVA_HOME

                    echo "===== Java ====="
                    which java
                    readlink -f $(which java)
                    java -version

                    echo "===== Java Compiler ====="
                    which javac
                    readlink -f $(which javac)
                    javac -version

                    echo "===== Maven ====="
                    which mvn
                    mvn -version
                '''
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
                sh 'docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} .'
            }
        }
        stage('docker push') {
            steps {
                sh 'docker push ${IMAGE_NAME}:${BUILD_NUMBER}'
            }
        }
        stage('connect to gke') {
            steps {
                sh "gcloud container clusters get-credentials $CLUSTER_NAME --region $REGION"
            }
        }
        stage('deploy to gke') {
            steps {
                sh "kubectl apply -f Deployment.yaml -f service.yaml"
            }
        }
    }
}
