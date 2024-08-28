[TOC]

# product-staging

![idun_product_staging_pipeline](../diagrams/idun_product_staging_pipeline.png)
[product-staging](https://spinnaker.rnd.gic.ericsson.se/#/applications/product-e2e-cicd/executions/configure/d8e85bad-3d55-433b-b7db-d3d3567e2553)
## Introduction:
This Pipeline Performs the necessary steps to stage a product.
## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| CHART_NAME | Name of the microservice chart within the integration chart |
| CHART_VERSION | Version of the microservice chart within the integration chart |
| CHART_REPO | Repo of the microservice chart within the integration chart |
| GERRIT_REFSPEC | Gerrit REF Spec is used by Inca to pull down a code review to build a new OSS Common Base |
| GERRIT_BRANCH | Gerrit Branch is used by Inca to pull down a code review to build a new OSS Common Base |
| II_INT_CHART_NAME | Name of the integration chart which will be used for the Initial install |
| II_INT_CHART_VERSION | Version of the Integration Chart which will be used for the Initial Install |
| II_INT_CHART_REPO | Repo of the Integration Chart which will be used for the Initial Install |
| SPRINT_CLOSED | Set this to true to stop all deliveries being delivered to the EO Chart. Application's can still execute the flow but will not be delivered to the EO Chart at the end. Options true or false. |
| TAGS | Applications that should be switch on during deployment |
| FORCE_REDEPLOY | Force the environment to be re-installed whether it is good or bad, options true or false. |
| PATH_TO_SITE_VALUES_FILE | The Path where all the necessary site values are located for the install/upgrade |
| QUARANTINE_ENV | Set this parameter to true if you wish for the environment to be quarantined if the deployment flow false |
| RCR_TAG | RCR Tag is used to identify the environment by Report Center Registration Stage |
| ENV_DETAILS_DIR | This is the directory within the Repo specified within the Gather-Env-Details Jenkins job where to find the pooling environment details |
| ENV_LABEL | This is the label to search for that is attached to the environments in the Lockable Resource Plugin on Jenkins |
| FLOW_URL_TAG | Flow URL Tag is used when locking the environment to add a tag to describe what has locked the environment for easier tracking |
| WAIT_TIME | This is the time to wait for an Environment to become available. After the time expires the job will fail out |
| SLAVE_LABEL | Label to choose which Jenkins slave to execute Jenkinsfiles against |
| FUNCTIONAL_USER_SECRET | Functional user for logging into armdocker |
| ARMDOCKER_USER_SECRET | Jenkins credentials ID for Docker login configuration file |
| HELM_TIMEOUT | Timeout for helmfile deploy |
| WAIT_SUBMITTABLE_BEFORE_PUBLISH |  |
| PLATFORM | Specify what platform the deployment runs e.g. KaaS RH Azure AWS OCP |
| STANDBY_POOL_NAME | Name of the pool containing 'Standby' test environments. |
| PSO_FLOW_POOL_NAME | Name of the pool containing current environment being used in the Product Staging pipeline. |
| PATH_TO_SITE_VALUES_OVERRIDE_FILE | PATH_TO_SITE_VALUES_OVERRIDE_FILE |
| SKIP_CRD_DEPLOY | Skip CRD Deploy |
 * * *

## Pipeline stages:

### Report Center Registration:
This stage runs a Jenkins job [Staging-Report-Register](https://fem4s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/Staging-Report-Register) (Regulus owned Jenkins job).

#### Description:
This stage sends the pipeline execution ID for logging and monitoring

 * * *
### Check is App Blocked
This checks preconditions before continuing.

#### Description:

- Checks if `CHART_NAME` contains an app, for each blocked app.
- If the Application is blocked the Pipeline will fail.

 * * *
### Reserve Environment:
This stage runs a Jenkins job [RPT-RC_Reserve-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Reserve-Environment) (Thunderbee owned Jenkins job).

#### Description:
This Job implements a function to reserve a Test Environment in RPT.
 * * *
### Get Latest Helmfile Version:
This stage runs a Jenkins job [Get-Latest-ChartOrHelmfile](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/Get-Latest-ChartOrHelmfile) (Ticketmaster owned Jenkins job).

#### Description:
This Job gets the latest version of the Integration of the Helmfile.
 * * *
>## Pipeline Helmfile flow from Master
### Check For CRDS:
This stage runs a Jenkins job [OSS-Integration-Check-For-CRDS](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/OSS-Integration-Check-For-CRDS) (Ticketmaster owned Jenkins job).

#### Description:
Check the status of the Deployment using HELM
 * * *
### Helmfile Fetch Build Upload Snapshot:
This stage runs a Jenkins job [Helmfile-Fetch-Build-Upload](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/Helmfile-Fetch-Build-Upload) (Ticketmaster owned Jenkins job).

#### Description:
This Job updates the helmfile with the application dropback versions and can either prepare a snapshot or publish a helmfile.
 * * *
### Evaluate Variables:
This stage evaluates variables to be referenced downstream.

#### Description:
This stage evaluates:

- INT_CHART_NAME
- INT_CHART_VERSION
- INT_CHART_REPO

 * * *
>## Pipeline Helmfile flow from Refspec

### Helmfile Fetch Build Upload Snapshot from Ref Spec:
This stage runs a Jenkins job [Helmfile-Fetch-Build-Upload](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/Helmfile-Fetch-Build-Upload) (Ticketmaster owned Jenkins job).

#### Description:
This Job updates the helmfile with the application dropback versions and can either prepare a snapshot or publish a helmfile.
### Evaluate Variables from Ref Spec Snapshot:
This stage evaluates variables to be referenced downstream.

#### Description:
This stage evaluates:

- `INT_CHART_NAME`
- `INT_CHART_VERSION`
- `INT_CHART_REPO`

 * * *
>## Both branches join back here.
### Post Snapshot Checks

Checks preconditions for successful execution of the pipeline.
 * * *
### Upgrade using Helmfile:
This stage runs a spinnaker pipeline [deploy-using-helmfile](https://spinnaker.rnd.gic.ericsson.se/#/applications/base-platform-e2e-cicd/executions/configure/08535c6d-871c-4b85-81bb-6f4f5c568969) (TicketMaster owned pipeline).

#### Description:
This Pipeline Cleans down a deployment and deploys a new Helmfile.
 * * *
### Start & Assert K6 Continuous Tests:
This stage runs a spinnaker pipeline [k6-Start-&-Assert](https://spinnaker.rnd.gic.ericsson.se/#/applications/product-e2e-cicd/executions/configure/303b7ebb-146b-4956-8ec2-86aef3d37817) (Banba owned pipeline).

#### Description:
This stage starts K6 tests.
 * * *
### Post UG Checks

Checks preconditions for successful execution of the pipeline.
 * * *
### Quarantine Environment:
This stage runs a Jenkins job [RPT-RC_Quarantine-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Quarantine-Environment) (Thunderbee owned Jenkins job).

#### Description:
This Job implements a function to quarantine a test environment in RPT.
 * * *
### Redeploy Moved Out Environment:
This stage runs a spinnaker pipeline [refresh-standby-env-for-product-staging](https://spinnaker.rnd.gic.ericsson.se/#/applications/product-e2e-cicd/executions/configure/3c1982f7-475e-47f0-a5eb-44186296651e) (Thunderbee owned pipeline). [Pipeline Documentation](/../../cicd_pipelines_documentation_and_diagrams/product_staging/eiap_product_staging_child_flows/documentation/refresh_standby_env_for_product_staging.md)

#### Description:
This pipeline performs a Refresh of a standby environment.
 * * *
### Helmfile Fetch Build Upload Release:
This stage runs a Jenkins job [Helmfile-Fetch-Build-Upload](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/Helmfile-Fetch-Build-Upload) (Ticketmaster owned Jenkins job).

#### Description:
This Job updates the helmfile with the application dropback versions and can either prepare a snapshot or publish a helmfile.
 * * *
### Helmfile Fetch Build Upload Release from Ref Spec:
This stage runs a Jenkins job [Helmfile-Fetch-Build-Upload](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/Helmfile-Fetch-Build-Upload) (Ticketmaster owned Jenkins job).

#### Description:
This Job updates the helmfile with the application dropback versions and can either prepare a snapshot or publish a helmfile.
 * * *

### Move Freshest Standby Environment into PSO Pool:
This stage runs a Jenkins job [RPT-RC_Update_Freshest_Standby_Environment_To_Available_And_Swap_Pool](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Update_Freshest_Standby_Environment_To_Available_And_Swap_Pool) (Thunderbee owned Jenkins job).

#### Description:
This Job is to update the freshest standby environment in a pool to available and swap it into a new pool.
 * * *
### Move Failed Environment to Standby Pool:
This stage runs a Jenkins job [RPT-RC_Swap_Test_Environment_Pool](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Swap_Test_Environment_Pool) (Thunderbee owned Jenkins job).

#### Description:
This Job is to swap a test environment out of the specified pool and into another specified pool.
 * * *
### EIAP_map_eiap_testware_versions:
This stage runs a Jenkins job [EIAP_Prod_Eng_map_eiap_testware_versions](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/EIAP_Prod_Eng_map_eiap_testware_versions) (Banba owned Jenkins job).

#### Description:
 This Pipeline creates a map between an EIAP and testware version in armdocker.rnd.ericsson.se/proj-oss-idun-prod-eng/idun-prod-eng-testware.
 * * *
### Update Version in RPT Post UG:
This stage runs a Jenkins job [RPT-RC_Update-Environment-Details](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Update-Environment-Details) (Thunderbee owned Jenkins job).

#### Description:
This Job is to update the properties of a test environment in RPT.
 * * *
### Unreserve Environment:
This stage runs a Jenkins job [RPT-RC_Unreserve-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Unreserve-Environment) (Thunderbee owned Jenkins job).

#### Description:
This Job implements a function to unreserve a test environment in RPT.
 * * *
### Post Upload Gate
Checks preconditions for successful execution of the pipeline.

 * * *
### Flow Completed
Checks preconditions for successful execution of the pipeline.
 * * *
