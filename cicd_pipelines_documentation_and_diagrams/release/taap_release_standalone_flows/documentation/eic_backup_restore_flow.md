[TOC]
# eic_backup_restore_flow
![eic_backup_restore_flow](../diagrams/eic_backup_restore_flow.png)
## Introduction:
This pipeline performs a Backup, Export and Restore of EIC on a given ENV.

## Pipeline Parameters:

| name | description |
|-----|-----|
| ADC_HOSTNAME | Hostname for ADC |
| APPMGR_HOSTNAME | Hostname for APPMGR |
| BDR_HOSTNAME | Hostname for Bulk Data Repository (BDR) |
| CLEANUP_SFTP |  |
| CLEANUP_TYPE | The Type of cleanup that needs to be done. FULL will cleanup the deployment helm releases, crd helm releases, crd components, clusterroles, cluster rolebindings, namespaces (deployment and crd). Where as PARTIAL will only cleanup the deployment namespace. FULL cleanup option should NOT be used in such cases where single cluster is hosting multiple deployments. |
| CONFIG_FILES | By default these tests will run e.g., ['main_multiple_iterations.json', 'main_single_iteration.json', 'main_CU2.json'] Override it if needed |
| DDP_AUTO_UPLOAD | When set to true, enables the DDP auto upload |
| DEPLOYMENT_MANAGER_REPO | Deployment Manager Repository. |
| DEPLOYMENT_MANAGER_VERSION | Deployment Manager version. |
| DEPLOYMENT_NAME | Name of the deployment |
| DOCKER_REGISTRY |  |
| DOCKER_REGISTRY_CREDENTIALS |  |
| ECM_HOSTNAME | Hostname for ECM |
| ENM_HOSTNAME | Hostname for ENM |
| ENV_CONFIG_FILE | Can be used to specify the environment configuration file which has specific details only for the environment under test |
| FH_SNMP_ALARM_IP | IP address for Fault Handling SNMP Alarm IP |
| FULL_PATH_TO_SITE_VALUES_FILE | Path within the Repo to the location of the site values file. |
| GAS_HOSTNAME | Hostname for GAS |
| IAM_HOSTNAME | Hostname for IAM |
| INT_CHART_VERSION | The version of integration chart |
| K6_TESTWARE_VERSION | The version of the K6 testware to be used |
| KF_BO_HOSTNAME | Hostname for Kafka Bootstrap |
| KUBECONFIG_FILE | Kubernetes configuration file to specify which environment to install on |
| LA_HOSTNAME | Hostname for Log Aggregator (LA) |
| ML_HOSTNAME | Hostname for Machine Learning Application (ML) |
| NAMESPACE | Namespace of environment |
| NEW_FROM_STATE_TAGS | List of tags for applications that have to be deployed |
| OS_HOSTNAME | Hostname for OS |
| PATH_TO_CERTIFICATES_FILES | Path within the Repo to the location of the certificates directory |
| PATH_TO_SITE_VALUES_OVERRIDE_FILE | Path within the Repo to the location of the site values override file.  The content of this file will be added or will override the content in the FULL_PATH_TO_SITE_VALUES_FILE |
| RCF_TAG | RCF Tag is used to identify the environment by Report Center Registration Stage for Child flows |
| RUN_ENM_INTEGRATION_TEST | When set to true, ENM integration tests will be run |
| RUN_TESTS | Run given tests scenarios and nothing else e.g. CANARY_UPGRADE_01, CANARY_UPGRADE_03 |
| Run_From_State | Choose to run 'From State' or not |
| SERVICE_MESH_IP | Service Mesh Load Balancer IP Address |
| SFTP_CREDENTIALS_ID | Credentials for SFTP |
| SFTP_SERVER_PATH | Server path of SFTP |
| SKIP_TESTS | Skips given test scenarios e.g. CANARY_UPGRADE_01, CANARY_UPGRADE_03 |
| SLAVE_LABEL |  |
| TAGS | List of tags for applications that have to be deployed |
| TA_HOSTNAME | Hostname for TA |
| TH_HOSTNAME | Hostname for TH |
| USE_DM_PREPARE | When set to true uses the site values generated from the Deployment manager prepare command for the deployment. |
| USE_REAL_CSAR | Option to use real CSAR. If false then dummy CSAR will be used |
| User_Version | User provided version |
| VNFM_HOSTNAME | Hostname for VNFM |

## Pipeline stages

### Get Latest Helmfile Version:
This stage runs a Jenkins job [Get-Latest-ChartOrHelmfile](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/Get-Latest-ChartOrHelmfile) (Ticketmaster owned Jenkins job).

#### Description:
This Job gets the latest version of the Integration of the Helmfile.
 * * *
### Get Environment Details:
This stage runs a Jenkins Job [DSC-DIT-Download-Document-As-Artifact](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/DSC-DIT-Download-Document-As-Artifact) (Thunderbee owned Jenkins job).

#### Description:
This Job is used to retrieve the properties of a Test Environment from DIT.
 * * *
### Evaluate Variables:
This stage evaluates variables to be referenced downstream.

#### Description:
This stage evaluates:

- PATH_TO_TO_STATE_SITE_VALUES_OVERRIDE_FILE
- SKIP_TESTS
- RUN_TESTS
- ECM_HOSTNAME
- ENM_HOSTNAME
- INT_CHART_VERSION

 * * *
### SFTP Server Cleanup:
This stage runs a spinnaker pipeline [EIC_Release_SFTP_Server_Cleanup](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/EIC_Release_SFTP_Server_Cleanup/) (Release owned job).

#### Description
This stage performs a cleans up all backups from external EIC Release SFTP server.
 * * *
### Initial Install:
This stage runs a spinnaker pipeline [eiap-release-install-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/9586d39d-db4d-40d8-a691-1178c766f65c) (Thunderbee owned pipeline) [Pipeline Documentation](/cicd_pipelines_documentation_and_diagrams/release/common_release_child_flows/documentation/eiap_release_upgrade_flow.md).

#### Description
This stage performs an install to the specififed intergartion chart version.
 * * *
### Upgrade:
This stage runs a Spinnaker pipeline [eiap-release-upgrade-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/59c0789e-51e9-4e5d-9387-53e276cda158) (Thunderbee owned pipeline).

#### Description:
This stage performs an upgrade to the specififed intergartion chart version.
 * * *
### Get OSS Deloyment Manager Version
This stage evaluates the deployment manager version variable to be referenced downstream. This value is taken from either the Install or Update stage

#### Description
This stage evaluates:

- DEPLOYMENT_MANAGER_VERSION

 * * *
### Backup:
This stage runs a Jenkins job [EO_BUR_Run_Backup](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/EO_BUR_Run_Backup) (Laika owned Jenkins job).

#### Description:
Pipeline used for testing Backup & Restore script
 * * *
### Initial Install To State (SKIP_TESTS == true):
This stage runs a spinnaker pipeline [eiap-release-install-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/9586d39d-db4d-40d8-a691-1178c766f65c) (Thunderbee owned pipeline) [Pipeline Documentation](/cicd_pipelines_documentation_and_diagrams/release/common_release_child_flows/documentation/eiap_release_upgrade_flow.md).

#### Description
This stage performs an install to the specififed intergartion chart version but skips running the k6 tests.
 * * *
### Restore To State:
This stage runs a spinnaker pipeline [eiap-release-data-restore-and-verify-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/419149fd-78da-4089-bbee-e22cc094e2f2) (Thunderbee owned pipeline) [Pipeline Documentation](/cicd_pipelines_documentation_and_diagrams/release/taap_release_child_flows/documentation/eiap_release_data_restore_and_verify_flow.md).

#### Description
This stage backs up and restore data using the EIAP Release data Restore and Verify flow linked above.
 * * *
