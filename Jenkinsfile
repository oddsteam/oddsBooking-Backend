pipeline{
    //  กำหนด ชื่อ,IP,.. ของ agent --> any : can run any agent
    agent any


    //
    environment{

        ORGANIZATION = "odds-booking"
        REGISTRY = "swr.ap-southeast-2.myhuaweicloud.com"
        TAG = "api-oddsbooking:${BRANCH_NAME}"
        API_BUILD_TAG = "${REGISTRY}/${ORGANIZATION}/${TAG}"
        SENDGRID_API_KEY='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'
        SENDGRID_ODDS_TEMPLATE_ID='d-c86d84c6ec274a8ca44a2d05541cba14'
        SENDGRID_USER_TEMPLATE_ID='d-aa36a3daea924b47be7ac9e601d5b672'

    }

    stages{
        stage("unit test"){
            steps{
                sh "echo '🚨 Unit tests when build.'"
            }
        }
        stage("build image"){
            steps{
                sh "docker build --rm --build-arg environment=${BRANCH_NAME} -t ${API_BUILD_TAG} ."
            }
        }
        stage("push docker image"){
            steps{
                sh """
                    docker login -u ap-southeast-2@OA4R6SQSJDS6O5TPXWUJ -p 092929273c8458b0141bdca0a6475a3f3103eb3f4fa57b4a5405635828bc4c9a ${REGISTRY}
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
