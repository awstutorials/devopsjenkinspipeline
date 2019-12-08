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

         stage('Run Docker image') {
            steps {
                echo "-=- run Docker image -=-"
                echo "${PATH}"
                sh "docker run --name ${TEST_CONTAINER_NAME} --detach --rm --network ci --expose 8081 --env JAVA_OPTS='-javaagent:/usr/local/tomcat/jacocoagent.jar=output=tcpserver,address=*,port=8000' in28min/todo-web-application-h2:0.0.1-SNAPSHOT"
            }
        }

        stage('Run Integration Test image') {
            steps {
                echo "-=- run Docker image -=-"
                echo "${PATH}"
                sh "mvn failsafe:integration-test failsafe:verify -DargLine=\"-Dtest.selenium.hub.url=http://selenium-hub:4444/wd/hub -Dtest.target.server.url=http://${TEST_CONTAINER_NAME}:8080/${APP_CONTEXT_ROOT}\""
                sh "java -jar target/dependency/jacococli.jar dump --address ${TEST_CONTAINER_NAME} --port 6300 --destfile target/jacoco-it.exec"
                junit 'target/failsafe-reports/*.xml'
                step( [ $class: 'JacocoPublisher' ] )
            }
        }
    }
}