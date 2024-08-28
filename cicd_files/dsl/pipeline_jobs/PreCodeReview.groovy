import common_classes.CommonSteps
import common_classes.CommonParameters

CommonSteps commonSteps = new CommonSteps()
CommonParameters commonParams = new CommonParameters()

def pipelineBeingGeneratedName = 'oss-common-cicd-pipeline-resources_Pre_Code_Review'
pipelineJob(pipelineBeingGeneratedName) {
    description(commonSteps.defaultJobDescription(pipelineBeingGeneratedName,
        "<p>This ${pipelineBeingGeneratedName} job is to run the precode review for Common CICD Pipeline Resources.</p>",
        "cicd_files/dsl/pipeline_jobs/PreCodeReview.groovy",
        "cicd_files/jenkins/files/PreCodeReview.Jenkinsfile"))
    disabled(false)
    keepDependencies(false)
    parameters {
        stringParam(commonParams.slave())
    }

    blockOn('oss-common-cicd-automation-pipeline_Build_And_Publish')

    triggers {
        gerrit {
            project(commonParams.repo(), 'master')
            events {
                patchsetCreated()
            }
        }
    }

    definition {
        cpsScm {
            scm {
                git {
                    branch('\${GERRIT_PATCHSET_REVISION}')
                    remote {
                        name('gcn')
                        url(commonParams.repoUrl())
                        refspec('\${GERRIT_REFSPEC}')
                    }
                    extensions {
                        choosingStrategy {
                            gerritTrigger()
                        }
                        cleanBeforeCheckout()
                    }
                }
            }
            scriptPath('cicd_files/jenkins/files/pipeline_jobs/PreCodeReview.Jenkinsfile')
        }
    }
    quietPeriod(5)
}