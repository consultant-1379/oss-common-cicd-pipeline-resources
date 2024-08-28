[TOC]

# deploy-using-helmfile-product-staging

![deploy_using_helmfile_product_staging](../diagrams/deploy_using_helmfile_product_staging.png)
[deploy-using-helmfile-product-staging](https://spinnaker.rnd.gic.ericsson.se/#/applications/product-e2e-cicd/executions/configure/5225e7c3-171e-4d29-99b1-ecfd40b5b2b8)
## Introduction:
This pipeline  cleans down a deployment and deploys a new Helmfile.
## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| ADC_HOSTNAME | ADC hostname Entry |
| APPMGR_HOSTNAME | APPMGR hostname Entry |
| ARMDOCKER_USER_SECRET | User secret from jenkins to create the Docker login configuration file |
| BDR_HOSTNAME | BDR_HOSTNAME Entry |
| COLLECT_LOGS | Select true to collect deployment logs when a failure is seen, false to turn off log collection |
| DDP_AUTO_UPLOAD | Specify if DDP Auto upload should run |
| DEPLOYMENT_TYPE | Used to set the deployment Type, option install or upgrade |
| DEPLOY_ALL_CRDS |  |
| DOCKER_REGISTRY | Registry that should be used for the deployment |
| DOCKER_REGISTRY_CREDENTIALS | Docker Registry Credentials, Only should be populated if using the local registry |
| DOWNLOAD_CSARS | Used to download the official released CSAR according to the helmfile version, if set to true the DOCKER_REGISTRY and DOCKER_REGISTRY_CREDENTIALS should be filled |
| ELASTIC_INGRESS_HOST |  |
| ENV_CONFIG_FILE | Environment configuration file stored in repo |
| FH_SNMP_ALARM_IP | IP for the SNMP Alarm Provider |
| FUNCTIONAL_USER_SECRET | Functional user for logging into armdocker |
| GAS_HOSTNAME | GAS Hostname |
| HELM_TIMEOUT | Timeout for helmfile deploy |
| IAM_HOSTNAME | IAM Hostname |
| INGRESS_IP | INGRESS IP to use for the deployment |
| INT_CHART_NAME | Name of the integration chart that holds the new microservice |
| INT_CHART_REPO | Repo of the Integration Chart that holds the new microservice |
| INT_CHART_VERSION | Version of the Integration Chart that holds the new microservice |
| KUBE_CONFIG | Kubernetes Config File name that is stored in Jenkins |
| LA_HOSTNAME | LA_HOSTNAME Entry |
| ML_HOSTNAME | ML_HOSTNAME Entry |
| NAMESPACE | Kubernetes Namespace to deploy the system into |
| OS_HOSTNAME | OS_HOSTNAME Entry |
| PATH_TO_CERTIFICATES_FILES | Path to the Certificates with the repo |
| PATH_TO_SITE_VALUES_FILE | The Path where all the necessary site values are located for the install/upgrade |
| PATH_TO_SITE_VALUES_OVERRIDE_FILE | Path within the Repo to the location of the site values override file. Content of this file will override the content for the site values set in the FULL_PATH_TO_SITE_VALUES_FILE parameter. |
| PF_HOSTNAME | PF HOSTNAME |
| PROMETHEUS_INGRESS_HOST |  |
| REGISTRY_HOSTNAME | EVNFM Registry Hostname Entry |
| SKIP_CRD_DEPLOY | Skip CRD Deploy |
| SLAVE_LABEL | Label to choose which Jenkins slave to execute Jenkinsfiles against |
| SO_HOSTNAME | SO Hostname Entry |
| TAGS | Site values tags which has to be set to true during deployment |
| TA_HOSTNAME | TA Hostname Entry |
| TH_HOSTNAME | TH_HOSTNAME Entry |
| UDS_HOSTNAME | UDS Hostname Entry |
| USE_DM_PREPARE | Set to true to use the Deployment Manager function "prepare" to generate the site values file |
| VNFM_HOSTNAME | EVNFM Hostname Entry |
| WHAT_CHANGED |  |
 * * *

## Pipeline Stages:

### Clean Down Deployment:
This stage runs a Jenkins job [purge-release-in-namespace](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/purge-release-in-namespace) (Ticketmaster/Honeypots owned Jenkins job).
* * *
### Pre-Deploy:
This stage runs a Spinnaker pipeline [IDUN-Pre-Deployment](https://spinnaker.rnd.gic.ericsson.se/#/applications/common-e2e-cicd/executions/configure/88012023-7839-4eca-92f4-f87715ed77ad) (Ticketmaster owned pipeline).
 * * *
### Deployment:
This stage runs a Jenkins job [helmfile-deploy](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/helmfile-deploy) (Ticketmaster/Honeypots owned Jenkins job).
 * * *
