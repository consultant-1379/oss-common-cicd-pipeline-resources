import common_classes.CommonSteps
import common_classes.CommonParameters

CommonSteps commonSteps = new CommonSteps()
CommonParameters commonParams = new CommonParameters()


def pipelineBeingGeneratedName = "oss-common-cicd-pipeline-resources_Pipeline_Updater"

pipelineJob(pipelineBeingGeneratedName) {
    description(commonSteps.defaultJobDescription( pipelineBeingGeneratedName,
        "<p>This ${pipelineBeingGeneratedName} job updates the Common CICD Pipeline Resources jobs.</p>",
        "cicd_files/dsl/pipeline_operations/PipelineUpdater.groovy",
        "cicd_files/jenkins/files/pipeline_operations/PipelineUpdater.Jenkinsfile"))

    parameters {
        stringParam(commonParams.slave())
    }

    logRotator(commonSteps.defaultLogRotatorValues())

    properties {
        pipelineTriggers {
            triggers {
                gerritTrigger {
                    triggerOnEvents {
                        changeMerged()
                    }

                    gerritProjects {
                        gerritProject {
                            compareType("PLAIN")
                            pattern(commonParams.repo())
                            branches {
                                branch {
                                    compareType("PLAIN")
                                    pattern("master")
                                }
                            }
                            filePaths {
                                filePath{
                                    compareType('ANT')
                                    pattern('cicd_files/**')
                                }
                            }
                            disableStrictForbiddenFileVerification(false)
                        }
                    }
                }
            }
        }
    }

    definition {
        cpsScm {
            scm {
                git {
                    branch('master')
                    remote {
                        url(commonParams.repoUrl())
                    }
                    extensions {
                        cleanBeforeCheckout()
                        localBranch 'master'
                    }
                }
            }
            scriptPath("cicd_files/jenkins/files/pipeline_operations/PipelineUpdater.Jenkinsfile")
        }
    }
}