[TOC]

# eic-release-upgrade-flow

![eic_release_upgrade_flow](../diagrams/eic_release_upgrade_flow.png)
[eic-release-upgrade-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/e6c06ca6-1798-48ae-bc07-6406460db4a2)
## Introduction:
This Pipeline performs the steps necessary for an Upgrade of EIC Environment

## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| DEPLOYMENT_NAME | Name of the deployment |
| RCF_TAG | RCF Tag is used to identify the environment by Report Center Registration Stage for Child flows |
| NAMESPACE | Namespace of environment |
| KUBECONFIG_FILE | Kubernetes configuration file to specify which environment to install on (Datafile in OST) |
| INT_CHART_VERSION | The version of integration chart |
| USE_REAL_CSAR | Option to use real CSAR. If false then dummy CSAR will be used |
| GAS_HOSTNAME | Hostname for GAS |
| IAM_HOSTNAME | Hostname for IAM |
| ADC_HOSTNAME | Hostname for ADC |
| APPMGR_HOSTNAME | Hostname for APPMGR |
| BDR_HOSTNAME | Hostname for BDR |
| EIC_HOSTNAME | Hostname for EIC |
| TAGS |  |
| TH_HOSTNAME | Hostname for TH |
| ENM_HOSTNAME | Hostname for ENM |
| ECM_HOSTNAME | Hostname for ECM |
| KF_BO_HOSTNAME | Hostname for Kafka Bootstrap |
| SKIP_TESTS | Skips given test scenarios e.g. CANARY_UPGRADE_01, CANARY_UPGRADE_03 |
| RUN_TESTS | Run given tests scenarios and nothing else e.g. CANARY_UPGRADE_01, CANARY_UPGRADE_03 |
| CONFIG_FILES | By default these tests will run e.g., ['main_multiple_iterations.json', 'main_single_iteration.json', 'main_CU2.json'] Override it if needed |
| SLAVE_LABEL |  |
| DOCKER_REGISTRY |  |
| DOCKER_REGISTRY_CREDENTIALS |  |
| DEPLOYMENT_MANAGER_REPO | Deployment Manager Repository. |
| DEPLOYMENT_MANAGER_VERSION | Deployment Manager version. |
| USE_DM_PREPARE | When set to true uses the site values generated from the Deployment manager prepare command for the deployment. |
| K6_TESTWARE_VERSION | The version of the K6 testware to be used. |
| DDP_AUTO_UPLOAD | When set to true, enables the DDP auto upload |
| RUN_ENM_INTEGRATION_TEST | When set to true, ENM integration tests will be run |
| USE_CERTM | Set to true to use the "--use-certm" tag during the deployment |
| SITE_VALUE_FILE_BUCKET_NAME | Name of the bucket that is storing the site values that is stored in object store. Defaults to eic_site_values_template. |
| SITE_VALUES_FILE_NAME | Name of the site values template to use that is stored in object store, including the file extension |
| SITE_VALUE_OVERRIDE_BUCKET_NAME | Name of the overwrite site values to use that is stored in object store. Defaults to eic_site_values_override |
| SITE_VALUES_OVERRIDE_FILE_NAME | Name of the overwrite site values to use that is stored in object store, including the file extension. Content will override the content for the site values set in the SITE_VALUES_FILE_NAME parameter. |
| KUBE_CONFIG_FILE_CREDENTIAL_ID | Kubernetes configuration file to specify which environment to install on (Jenkins  Credential ID) |
| PRELOAD_APP_MANAGER | When set to true, enables the population of the App Manager database with dummy applications |
 * * *

## Pipeline Stages:

### Report Center Registration:
This stage runs a Jenkins job [Staging-Report-Register](https://fem4s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/Staging-Report-Register) (Regulus owned Jenkins job).

#### Description:
 This stage sends the pipeline execution ID for logging and monitoring.

 * * *

### Retrieve EIAE Helmfile Version:
This stage runs a Jenkins job [CICD-UTILS-Retrieve-EIAE-Helmfile-Version](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/CICD-UTILS-Retrieve-EIAE-Helmfile-Version) (Thunderbee owned Jenkins job).

#### Description:
Retrieves a EIAE helmfile version from a namespace using specified ConfigMap

 * * *
### Compare From State With TLS Version:
This stage runs a Jenkins job [BASE-VERS_Compare_Version_With_Baseline_Version](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/BASE-VERS_Compare_Version_With_Baseline_Version) (Thunderbee owned Jenkins job).

#### Description:
The job compares a specified version with the baseline version

 * * *
### Compare To State With TLS Version:
This stage runs a Jenkins job [BASE-VERS_Compare_Version_With_Baseline_Version](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/BASE-VERS_Compare_Version_With_Baseline_Version) (Thunderbee owned Jenkins job).

#### Description:
The job compares a specified version with the baseline version

 * * *
### Compare From State With SEF Version:
This stage runs a Jenkins job [BASE-VERS_Compare_Version_With_Baseline_Version](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/BASE-VERS_Compare_Version_With_Baseline_Version) (Thunderbee owned Jenkins job).

#### Description:
The job compares a specified version with the baseline version

 * * *
### Pre Upgrade Health Check:
This stage runs a Jenkins job [OSS-Integration-HealthCheck-Using-DM](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/OSS-Integration-HealthCheck-Using-DM) (Ticketmaster owned Jenkins job).

#### Description:
Checks the status of the Deployment using HELM

 * * *
### Pre Upgrade TLS Setup With SEF:
This stage runs a Jenkins job [CICD-UTILS-TLS-Setup](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/CICD-UTILS-TLS-Setup) (Thunderbee owned Jenkins job).

#### Description:
Performs a TLS setup before Upgrade with SEF

 * * *
### Pre Upgrade TLS Setup Without SEF:
This stage runs a Jenkins job [CICD-UTILS-TLS-Setup](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/CICD-UTILS-TLS-Setup) (Thunderbee owned Jenkins job).

#### Description:
Performs a TLS setup before Upgrade without SEF

 * * *
### Pre-Deploy:
This stage runs a Spinnaker pipeline [IDUN-Pre-Deployment](https://spinnaker.rnd.gic.ericsson.se/#/applications/common-e2e-cicd/executions/configure/88012023-7839-4eca-92f4-f87715ed77ad) (Ticketmaster owned pipeline).

#### Description:
This pipeline performs pre-requisites before starting the deployment of EIC.

 * * *
### Upgrade:
This stage runs a Jenkins job [helmfile-deploy](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/helmfile-deploy) (Ticketmaster owned Jenkins job).

#### Description:
Performs the Upgrade
Checks the status of the Deployment using HELM

 * * *
### Post Upgrade TLS Setup:
This stage runs a Jenkins job [CICD-UTILS-TLS-Setup](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/CICD-UTILS-TLS-Setup) (Thunderbee owned Jenkins job).

#### Description:
Performs a TLS setup after Upgrade

 * * *
### Post Upgrade Health Check:
This stage runs a Jenkins job [OSS-Integration-HealthCheck-Using-DM](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/OSS-Integration-HealthCheck-Using-DM) (Ticketmaster owned Jenkins job).

#### Description:
Checks the status of the Deployment using HELM
 * * *
### Run k6 Tests & Assert:
This stage runs a Spinnaker pipeline [IDUN_Prod_Eng_K6_single_iteration_release](https://spinnaker.rnd.gic.ericsson.se/#/applications/banba/executions/configure/e908f29c-e1ac-4df5-a8e3-0cae7a426efe) (Banba owned pipeline).

#### Description:
Runs K6 Tests against [eric-oss-product-engineering|https://gerrit-gamma.gic.ericsson.se/plugins/gitiles/OSS/com.ericsson.oss.productEngineering/eric-oss-product-engineering]

 * * *
### Post Flow Checks

Checks preconditions for successful execution of the pipeline.
 * * *
### Push Data to DDP:
This stage runs a Jenkins job [CICD-UTILS-DDP-Upload](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/CICD-UTILS-DDP-Upload/) (Thunderbee owned Jenkins job).

#### Description:
This job will execute the necessary logic to push environment data to DDP before running an Install

 * * *
### Quarantine Environment:
This stage runs a Jenkins job [RPT-RC_Quarantine-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Quarantine-Environment) (Thunderbee owned Jenkins job).

#### Description:
This Job implements a function to quarantine a Test Environment in RPT.

 * * *
### End Flow

Checks preconditions for successful execution of the pipeline.
 * * *
