[TOC]

# autoapp-product-staging

![autoapp-product-staging](../diagrams/autoapp-product-staging.png)
[auto_app_e2e_cicd](https://spinnaker.rnd.gic.ericsson.se/#/projects/auto_app_e2e_cicd/dashboard)

## Introduction:
This pipeline is the common auto app product staging pipeline, for all auto apps which are placed under the auto_app_e2e_cicd Spinnaker project.

The auto app pipeline uses the APP-MGR API endpoints of App Onboarding and App Lcm to Onboard and Instantiate the auto app onto EIAP. Please use the provided links for further information about the [App Onboarding](https://adp.ericsson.se/marketplace/app-onboarding) and [App Lcm](https://adp.ericsson.se/marketplace/app-lcm) APIs.

### Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| CHART_NAME | Name of the auto app helm chart |
| CHART_VERSION | Version of the auto app helm chart |
| CHART_REPO | Repo of the auto app helm chart |
| ENVIRONMENT_NAME | Name of the Environment in which the auto app will be onboarded and instantiated |
| INPUT_FOLDER_LOCATION | This is the path of the folder which will contain the input template files for the CSAR build |
| ADDITIONAL_VALUES_LOCATION | This path of the folder which will contain the additional values files for the CSAR build. |
| SSH_REPO_URL | SSH URL to the repo that will contain the input template files for the CSAR build. |
| CSAR_STORAGE_INSTANCE | Storage Instance (Staging and Release) to push the CSARs to |
| CSAR_STORAGE_REPO_STAGING | Storage directory (Staging) to push the CSARs to |
| CSAR_STORAGE_REPO_RELEASE | Storage directory (Release) to push the CSARs to |
| RCR_TAG | RCR Tag is used to identify the environment by Report Center Registration Stage for AutoApp stage |
| SKIP_CSAR_RELEASE | If set to true - new Auto App CSAR will not be released. |
 * * *

## Pipeline Stages:

### Get Environment Details:
This stage runs a Jenkins Job [RPT-RC_Retrieve-Environment-Details](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Retrieve-Environment-Details) (Thunderbee owned Jenkins job).

#### Description:
This Job is used to retrieve the properties of a Test Environment from RPT.
 * * *

### Report Center Registration:
This stage runs a Jenkins Job [Staging-Report-Register](https://fem4s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/Staging-Report-Register) (Regulus owned Jenkins job).

#### Description:
This Job sends the pipeline execution ID for logging and monitoring
 * * *

### Fetch Build and Upload CSAR to Staging Repo:
This stage runs a Jenkins Job [EIAP-AUTO-APP-CSAR-Builder](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/EIAP-AUTO-APP-CSAR-Builder/) (Thunderbee owned Jenkins job).

#### Description:
This Job is used to prepare, build and upload a CSAR to the Staging Repo
 * * *

### Pre Onboarding Testing
This stage is controlled by the owner of the auto app and in this stage, the owning team will specify the specific pre onboarding testing pipeline to run
 * * *

### Onboarding
This stage runs a Jenkins job [APP-MGR-PC-Onboard-Auto-App](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/APP-MGR-PC-Onboard-Auto-App/) (Thunderbee owned Jenkins job).

#### Description:
This job implements a function to Onboard an Auto App.
 * * *

### Instantiation
This stage runs a Jenkins Job [APP-MGR-PC-Instantiate-Auto-App](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/APP-MGR-PC-Instantiate-Auto-App/) (Thunderbee owned Jenkins job).

#### Description:
This job implements a function to Instantiate an Auto App.
 * * *

### Post Instantiation Testing
This stage is controlled by the owner of the auto app and in this stage, the owning team will specify the specific post instantiation testing pipeline to run
 * * *

### Fetch Build and Upload CSAR to Release Repo
This stage runs a Jenkins job [EIAP-AUTO-APP-CSAR-Builder](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/EIAP-AUTO-APP-CSAR-Builder/) (Thunderbee owned Jenkins job).

#### Description:
This Job is used to prepare, build and upload a CSAR to the Release Repo
 * * *

### Update Product Staging Baseline Version In Repo
This stage runs a Jenkins job [BASE-VERS_Update_Baseline_Version_In_Repository](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/BASE-VERS_Update_Baseline_Version_In_Repository/) (Thunderbee owned Jenkins job).

#### Description:
This job updates the baseline version in the baseline repo. To see the current auto app baseline versions, please see the [Thunderbee Baseline Repo](https://gerrit.ericsson.se/plugins/gitiles/OSS/com.ericsson.oss.cicd/baseline-versions/+/refs/heads/master/autoapp_versions.json).
 * * *
