pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2 --network ci -v /var/run/docker.sock:/var/run/docker.sock' 
        }
    }
    environment {
        ORG_NAME = "deors"
        APP_NAME = "deors-demos-petclinic"
        APP_CONTEXT_ROOT = "petclinic"
        TEST_CONTAINER_NAME = "ci-${APP_NAME}-${BUILD_NUMBER}"
        DOCKER_HUB = credentials("${ORG_NAME}-docker-hub")
    }  
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    step( [ $class: 'JacocoPublisher' ] )
                }
            }
        }
        stage('Package') {
            steps {
                echo "-=- packaging project -=-"
                sh "mvn package -DskipTests"
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }
    }
}