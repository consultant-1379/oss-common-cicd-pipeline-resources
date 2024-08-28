[TOC]
# TEST_PIPELINE-eiap-connected-release-external-install-flow
![TEST_PIPELINE-eiap-connected-release-external-install-flow](../diagrams/TEST_PIPELINE-eiap-connected-release-external-install-flow.png)
[TEST_PIPELINE-eiap-connected-release-external-install-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/b182c667-5e53-4a71-8f24-cd2c4ecafee2)
## Introduction:
This pipeline is a standalone pipeline which performs an install of EIAP software on a Test Environment. This flow simulates the install of EIAP on a customer environment.
 * * *
## Pipeline stages
### Is environment name Azure_MANA
Checks preconditions for the pipeline.
 * * *
### Pre-requisites Verification:
This stage requires a decision by the pipeline operator.
> **_Instructions:_**"Are all pre-requisite done?"
 * * *
### Jenkins Agent Cleanup:
This stage runs a Jenkins job [oss-idun-release-cicd_Jenkins_Agent_Cleanup](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/oss-idun-release-cicd_Jenkins_Agent_Cleanup) (Thunderbee owned Jenkins job).
#### Description:
This job cleans the agent it is run on.
 * * *
### Get Environment Details:
This stage runs a Jenkins job [DSC-DIT-Download-Document-As-Artifact](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/DSC-DIT-Download-Document-As-Artifact) (Thunderbee owned Jenkins job).
#### Description:
This Job is to retrieve the properties of a test environment in DIT.
 * * *
### Purge:
This stage runs a Jenkins job [Purge-Release-in-Namespace](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/view/TicketMaster/job/Purge-Release-in-Namespace) (Ticketmaster owned Jenkins job).
#### Description:
This job used to remove any old helm charts and PVC's from a given namespace.
 * * *
### Pre-Deploy:
This stage runs a Spinnaker pipeline [IDUN-Pre-Deployment](https://spinnaker.rnd.gic.ericsson.se/#/applications/common-e2e-cicd/executions/configure/88012023-7839-4eca-92f4-f87715ed77ad) (Ticketmaster owned pipeline).
#### Description:
This pipeline performs pre-requisites before starting the deployment of EIAP.
 * * *
### Transfer Package Verification:
This stage requires a decision by the pipeline operator.
> **_Instructions:_** "Please verify if package.tar.gz is available in the DESTINATION_SERVER_PACKAGE_LOCATION provided (default=/tmp)."
 * * *
### Test Unpack and Push Images:
This stage runs a Jenkins job [oss-idun-release-cicd_Unpack_And_Push_Images_Internal_Testing](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/oss-idun-release-cicd_Unpack_And_Push_Images_Internal_Testing) (Thunderbee owned Jenkins job).
#### Description:
This job is used to push the images to the Conatainer Registry.
 * * *
### Checkpoint:
This stage requires a decision by the pipeline operator.
> **_Instructions:_** "Please verify if all the images are present in the ACR. If yes, please press continue."
 * * *
### Test MANA Deploy:
This stage runs a Jenkins job [oss-idun-release-cicd_MANA_Deploy_Internal_Testing](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/oss-idun-release-cicd_MANA_Deploy_Internal_Testing) (Thunderbee owned Jenkins job).
#### Description:
This job is used to install/upgrade the EIAP on an environment.
 * * *
### Post Deploy Health Check:
This stage runs a Jenkins job [oss-idun-release-cicd_MANA_Health_Check_Internal_Testing](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/oss-idun-release-cicd_MANA_Health_Check_Internal_Testing) (Thunderbee owned Jenkins job).
#### Description:
This job checks the status of HELM deployments on MANA environments.
