pipeline{
    //  กำหนด ชื่อ,IP,.. ของ agent --> any : can run any agent
    agent any


    //
    environment{

        ORGANIZATION = "odds-booking"
        REGISTRY = "swr.ap-southeast-2.myhuaweicloud.com"
        TAG = "api-oddsbooking:${BRANCH_NAME}"
        API_BUILD_TAG = "${REGISTRY}/${ORGANIZATION}/${TAG}"

    }

    stages{
        stage("unit test"){
            steps{
                sh "echo '🚨 Unit tests should be added.'"
            }
        }
        stage("build image"){
            steps{
                sh "docker build --build-arg environment=${BRANCH_NAME} -t ${API_BUILD_TAG} ."
            }
        }
        stage("push docker image"){
            steps{
                sh """
                    docker login -u ap-southeast-2@H97WABNOA1NBRPW8INUL -p aa275bca967ab0e83dccf3c57efb23ff981d9cd8ae4c66089d4aa25cdf971292 ${REGISTRY}
                    docker push ${API_BUILD_TAG}
                """
            }
        }
        stage("deploy"){
            steps{
                sh  """
                        scp docker-compose.yml oddsbooking@159.138.240.167:./docker-compose.yml
                        scp deploy-script.sh oddsbooking@159.138.240.167:./deploy-script.sh
                        ssh -oStrictHostKeyChecking=no -t oddsbooking@159.138.240.167 \"
                            chmod +x deploy-script.sh
                            REGISTRY=${REGISTRY} \
                            BRANCH_NAME=${BRANCH_NAME} \
                            ./deploy-script.sh
                        \"

                    """
            }
        }
    }
}
