def getDslParamaters() {
    return [SLAVE: env.SLAVE]
}

def getPipelineJobList() {
    def pipelineJobList = []

    pipelineJobList.add("cicd_files/dsl/pipeline_jobs/PreCodeReview.groovy")

    pipelineJobList.add('cicd_files/dsl/pipeline_operations/PipelineUpdater.groovy')

    return pipelineJobList.join('\n')
}

pipeline {
    agent {
        node {
            label SLAVE
        }
    }

    environment {
        DSL_CLASSPATH = 'cicd_files/dsl'
    }

    stages {
        stage ('Validate required parameters set') {
            when {
                expression {
                    env.SLAVE == null
                }
            }

            steps {
                error ('Required parameter(s) not set. Please provide a value for all required parameters')
            }
        }

        stage ('Generate Common CICD Pipeline Resources jobs') {
            steps {
                jobDsl targets: getPipelineJobList(),
                additionalParameters: getDslParamaters(),
                additionalClasspath: env.DSL_CLASSPATH
            }
        }

        stage ('Update Common CICD Pipeline Resources List View') {
            steps {
                jobDsl targets: 'cicd_files/dsl/pipeline_views/View.groovy'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
