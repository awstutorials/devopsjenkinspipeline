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

        stage('run') {
        steps {
        script {
            docker.image('in28min/todo-web-application-h2:0.0.1-SNAPSHOT').withRun('-p 8081:8081') { c ->
                    /* Wait until mysql service is up */
                    sh 'while ! curl http://localhost:8081 --silent; do sleep 1; done'
                    /* Run some tests which require MySQL */
                    sh 'make check'
            }
            }
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
                sh "mvn failsafe:integration-test failsafe:verify"
            }
        }
    }
}