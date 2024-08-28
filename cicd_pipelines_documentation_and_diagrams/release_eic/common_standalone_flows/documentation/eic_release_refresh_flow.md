[TOC]

# eic-release-refresh-flow

![eic_release_refresh_flow](../diagrams/eic_release_refresh_flow.png)
[eic-release-refresh-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/511e2071-e585-4c8c-b466-66e5367018fb)
## Introduction:
For new sprints/releases, the from states of EIC Test Environments will need to be updated to the correct version of software. Therefore a refresh pipeline is required for this function.

## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| NEW_FROM_STATE | The fromState to be updated and used S2L and R2L loops |
| DEPLOYMENT_NAME | Name of the deployment |
| USE_REAL_CSAR | Option to use real CSAR. If false then dummy CSAR will be used |
| NEW_FROM_STATE_TAGS | List of tags for applications that have to be deployed |
| DOCKER_REGISTRY |  |
| DOCKER_REGISTRY_CREDENTIALS |  |
| NAMESPACE |  |
| SFTP_CREDENTIALS_ID | Credentials for SFTP |
| SFTP_SERVER_PATH | Server path of SFTP |
| DEPLOYMENT_MANAGER_REPO | Deployment Manager Repository. |
| NEW_DM_FROM_STATE | Update the Deployment Manager From State. |
| NEW_FROM_STATE_K6_VERSION | Update the K6 From State. |
| DEPLOYMENT_TYPE | Install or upgrade deployment. |
| USE_DM_PREPARE | When set to true uses the site values generated from the Deployment manager prepare command for the deployment. |
| FROM_STATE_SITE_VALUES_FILE | Datafile name of the site values file stored in OST which is associated to the from state value. |
| FROM_STATE_SITE_VALUES_OVERRIDE_FILE | Datafile name of the site values override file stored in OST which is associated to the from state value. |
| DDP_AUTO_UPLOAD | When set to true, enables the DDP auto upload |
| CLEANUP_TYPE | The Type of cleanup that needs to be done. FULL will cleanup the deployment helm releases, crd helm releases, crd components, clusterroles, cluster rolebindings, namespaces (deployment and crd). Where as PARTIAL will only cleanup the deployment namespace. FULL cleanup option should NOT be used in such cases where single cluster is hosting multiple deployments. |
| RUN_ENM_INTEGRATION_TEST | When set to true, ENM integration tests will be run |
 * * *

## Pipeline Stages:

### Update Env Status to Refreshing:
This stage runs a Jenkins job [RPT-RC_Update-Test-Environment-Status](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Update-Test-Environment-Status) (Thunderbee owned Jenkins job).

#### Description:
This Job is to update the status of a Test Environment in RPT.
 * * *
### Get Environment Details:
This stage runs a Jenkins job [DSC-DIT-Download-Document-As-Artifact](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/DSC-DIT-Download-Document-As-Artifact) (Thunderbee owned Jenkins job).

#### Description:
This Job is to retrieve the properties of a Test Environment in DIT.
 * * *

### Evaluate Variables:
This stage evaluates variables to be referenced downstream

#### Description:
This stage evaluates:

- SKIP_TESTS
- RUN_TESTS
- ECM_HOSTNAME
- ENM_HOSTNAME

 * * *
### SFTP Server Cleanup:
This stage runs a spinnaker pipeline [EIC_Release_SFTP_Server_Cleanup](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/EIC_Release_SFTP_Server_Cleanup/) (Release owned job).

#### Description
This stage performs a cleans up all backups from external EIC Release SFTP server.
 * * *
### Install to new fromState:
This stage runs a Spinnaker pipeline [eic-release-install-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/903cef19-f589-4286-bb50-45ab52f92de0) (Thunderbee owned pipeline). [Pipeline_Documentation](/../../cicd_pipelines_documentation_and_diagrams/release/common_release_child_flows/documentation/eic_release_install_flow.md)

#### Description:
This Pipeline performs the steps necessary for an Install of an EIC Environment
 * * *
### Upgrade to new fromState:
This stage runs a Spinnaker pipeline [eic-release-upgrade-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/e6c06ca6-1798-48ae-bc07-6406460db4a2) (Thunderbee owned pipeline). [Pipeline_Documentation](/../../cicd_pipelines_documentation_and_diagrams/release/common_release_child_flows/documentation/eic_release_upgrade_flow.md)

#### Description:
This Pipeline performs the steps necessary for an Upgrade of EIC Environment
 * * *
### Update From State details in DIT:
This stage runs a Jenkins job [DSC-DIT-Update-Key-Value-Pairs](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/DSC-DIT-Update-Key-Value-Pairs) (Thunderbee owned Jenkins job).

#### Description:
 This Job is to update the properties of a Test Environment in DIT.
### Update DIT fromState and dmFromState:
This stage runs a Jenkins job [DSC-DIT-Update-Key-Value-Pairs](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/DSC-DIT-Update-Key-Value-Pairs) (Thunderbee owned Jenkins job).

#### Description:
This stage changes the EIC version and DM version of the Test Environment in DIT, so that the version of the environment and the DM can be tracked, and viewed in a UI for visualization purposes.
 * * *
### Get OSS Deployment Manager Version:
This stage evaluates variables to be referenced downstream

#### Description:
This stage evaluates:

- DEPLOYMENT_MANAGER_VERSION

 * * *
### Backup and Export (external):
This stage runs a Spinnaker pipeline [eic-release-backup-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/e1a4c214-54f8-49de-b6f9-2d9b4e14460a) (Thunderbee owned pipeline). [Pipeline Documentation](/../../cicd_pipelines_documentation_and_diagrams/release/taap_release_child_flows/documentation/eic_release_backup_flow.md)

#### Description:
This pipeline performs a Backup and Export

 * * *
### Post Flow Checks

Checks preconditions for successful execution of the pipeline.
 * * *
### Quarantine Environment:
This stage runs a Jenkins job [RPT-RC_Quarantine-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Quarantine-Environment) (Thunderbee owned Jenkins job).

#### Description:
This Job implements a function to quarantine a Test Environment in RPT.
 * * *
### Update Env Status to Available:
This stage runs a Jenkins job [RPT-RC_Update-Test-Environment-Status](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Update-Test-Environment-Status) (Thunderbee owned Jenkins job).

#### Description:
This Job is to update the status of a Test Environment in RPT.
 * * *
### End Flow

Checks preconditions for successful execution of the pipeline.
 * * *
