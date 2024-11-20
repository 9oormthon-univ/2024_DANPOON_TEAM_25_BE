pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = "milkymilky0116/flake-ide-be"
        DOCKER_TAG = "latest"
        // Docker Hub 크리덴셜 ID를 설정합니다
        DOCKER_CREDENTIALS_ID = 'jenkins-credentials'

        MANIFEST_PATH = "2024_DANPOON_TEAM_25_MANIFEST"
        MANIFEST_REPO = "https://github.com/9oormthon-univ/2024_DANPOON_TEAM_25_MANIFEST"  // Manifest 깃헙 레포지토리
        MANIFEST_REPO_BRANCH = "main"  // Manifest 브랜치
    }
    
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'dev',
                    url: 'https://github.com/9oormthon-univ/2024_DANPOON_TEAM_25_BE.git'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    // Jenkins 사용자에게 sudo 권한을 부여하여 Docker 커맨드 실행
                    sh '''
                        docker build -t $DOCKER_IMAGE:$DOCKER_TAG .
                    '''
                }
            }
        }
        
        stage('Push Docker Image') {
            steps {
                script {
                    // Docker Hub 로그인을 위한 크리덴셜 사용
                    withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                        sh '''
                            echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                            docker push $DOCKER_IMAGE:$DOCKER_TAG
                            docker rmi $DOCKER_IMAGE:$DOCKER_TAG
                        '''
                    }
                }
            }
        }

        stage('Clone and Update Manifest Repo') {
            steps {
                script {
                    sh """
                    rm -rf ${MANIFEST_PATH}
                    git clone --branch ${MANIFEST_REPO_BRANCH} ${MANIFEST_REPO}
                    """

                    sh """
                    sed -i 's|image: ${DOCKER_IMAGE}:.*|image: ${DOCKER_IMAGE}:${DOCKER_TAG}|' ${MANIFEST_PATH}/be/deployment.yaml
                    """
                }
            }
        }

        stage('Commit and Push Manifest Update') {
            steps {
                script {

            withCredentials([usernamePassword(credentialsId: 'jenkins-token', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                    dir("${MANIFEST_PATH}") {
                        sh """
                        git config user.name "milkymilky0116"
                        git config user.email "sjlee990129@gmail.com"
                        git remote set-url origin https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/9oormthon-univ/2024_DANPOON_TEAM_25_MANIFEST.git
                        git add be/deployment.yaml
                        git commit -m "bot: Update backend image to ${DOCKER_TAG}"
                        git push origin ${MANIFEST_REPO_BRANCH}
                        """
                    }
                }
            }
            }
    }
    
    post {
        success {
            echo 'Successfully built and pushed Docker image!'
        }
        failure {
            echo 'Failed to build or push Docker image.'
        }
        always {
            // 클린업 작업
            sh 'docker logout'
        }
    }
}
