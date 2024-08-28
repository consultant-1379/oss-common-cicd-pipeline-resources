[TOC]

# IDUN-PRODUCT-release-E2E-Flow

![idun_product_release_flow](../diagrams/idun_product_release_flow.png)
[IDUN-PRODUCT-release-E2E-Flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/product-e2e-cicd/executions/configure/f8a1fd04-ea37-4381-a003-54f4f1fb6ff8)
## Introduction:
The IDUN Product Release E2E Flow.
## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| HELM_TIMEOUT | Time in seconds for the deployment manager to wait for the deployment to complete |
| QUARANTINE_ENV | Set this parameter to true if you wish for the environment to be quarentined if the deployment flow false |
| SO_DEPLOY | Option to Deploy SO set either "true" or "false" |
| PF_DEPLOY | Option to Deploy Policy Framework set either "true" or "false" |
| UDS_DEPLOY | Option to Deploy UDS set either "true" or "false" |
| PLATFORM_DEPLOY | Option to Deploy PLATFORM set either "true" or "false" |
| FUNCTIONAL_USER_SECRET | Arm docker credential secret ro get access to the ar docker during the deployment using the deployment manager |
| ENV_DETAILS_DIR | This is the directory within the Repo specified within the Gather-Env-Details Jenkins job where to find the pooling environment details |
| ENV_LABEL | This is the label to search for that is attached to the environments in the Lockable Resource Plugin on Jenkins |
| FLOW_URL_TAG | Flow URL Tag is used when locking the invironment to add a tag to descript what has locked the environment for easier tracking |
| WAIT_TIME | This is the time to wait for an Environment to become available. After the time expirers the job will fail out |
| WAIT_SUBMITTABLE_BEFORE_PUBLISH |  |
 * * *

## Pipeline stages:

### IDUN_Product_Staging:
This stage runs a spinnaker pipeline [product-staging](https://spinnaker.rnd.gic.ericsson.se/#/applications/product-e2e-cicd/executions/configure/d8e85bad-3d55-433b-b7db-d3d3567e2553) (??? owned pipeline) [Pipeline Documentation](/../../cicd_pipelines_documentation_and_diagrams/product_staging/eiap_product_staging_parent_flows/documentation/idun_product_staging_pipeline.md).

#### Description:
This Pipeline Performs the necessary steps to stage a product.
 * * *
### Gerrit Feedback on Trigger:
This stage runs a Jenkins job [OSS-Common-Base-Gerrit-Notification](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/OSS-Common-Base-Gerrit-Notification) (Ticketmaster owned Jenkins job).

#### Description:
This Job comments on / reviews a Gerrit Patchset.
 * * *
### Gerrit Feedback on Successful IDUN_Product_Staging:
This stage runs a Jenkins job [OSS-Common-Base-Gerrit-Notification](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/OSS-Common-Base-Gerrit-Notification) (Ticketmaster owned Jenkins job).

#### Description:
THis Job comments on / reviews a Gerrit Patchset.
 * * *
### Gerrit Feedback on Failed IDUN_Product_Staging:
This stage runs a Jenkins job [OSS-Common-Base-Gerrit-Notification](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/OSS-Common-Base-Gerrit-Notification) (Ticketmaster owned Jenkins job).

#### Description:
 THis Job comments on / reviews a Gerrit Patchset.
 * * *
### Flow Completed

Checks preconditions for successful execution of the pipeline.

