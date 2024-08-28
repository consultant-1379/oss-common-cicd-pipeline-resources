[TOC]

# sample-autoapp-E2E-Flow

![sample-autoapp-E2E-Flow](../diagrams/sample_autoapp_e2e_flow.png)

Example of Sample Auto App E2E Flow:

[eric-oss-hello-world-go-app E2E Flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/autoapp-hello-world-go-app-e2e-cicd/executions?pipeline=eric-oss-hello-world-go-app-E2E-Flow)

## Introduction:
This Pipeline Coordinates the running of the Sample Autoapp Product Staging and Release Flows.

The Sample Autoapp is a sample Auto App, this differs from the EACC, PME Auto Apps.
Sample auto apps only perform perform initial instantiations in product staging and release pipelines.
Unlike normal Auto Apps release flow who run the following:

* Intial Instantiation To Last Released Auto App Version
* Upgrade To Latest Candidate Auto App Version
* Initial Instantiation To Latest Candidate Auto App Version

Sample Auto Apps will only do the following:

* Intial Instantiation To Last Released Sample Auto App Version

### Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| INPUT_FOLDER_LOCATION | This is the path of the folder which will contain the input template files for the CSAR build |
| SSH_REPO_URL | SSH URL to the repo that will contain the input template files for the CSAR build. |
| KICK_OFF_RELEASE | When set to 'true', the release pipeline will be kicked off upon successful execution of the PSO pipeline. |
 * * *

## Pipeline stages

### SAMPLE_AUTOAPP_PRODUCT_STAGING_PIPELINE:

This stage runs the Sample Autoapp Product staging pipeline.

Example of Sample Auto App Product Staging Flow

[hello-world-go-app-product-staging Flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/autoapp-hello-world-go-app-e2e-cicd/executions/configure/563f6e06-e14e-4d3e-ae83-b604b013ae40) (Thunderbee owned pipeline).

#### Description:
This pipeline is the Sample Autoapp product staging pipeline.

The Sample Autoapp pipeline uses the APP-MGR API endpoints of App Onboarding and App Lcm to Onboard and Instantiate the auto app onto EIC

[Read More in the autoapp-product-staging Pipeline Documentation](../../../auto_app_product_staging/auto_app_product_staging_parent_flows/documentation/autoapp-product-staging.md)
* * *

### SAMPLE_AUTOAPP_RELEASE:

This stage runs the Sample Autoapp Release pipeline.

Example of Sample Auto App Traditional Release E2E Flow

[hello-world-go-app-traditional-release-E2E-Flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/autoapp-hello-world-go-app-e2e-cicd/executions/configure/19dffa93-0f9a-4ed4-a9b4-61aafadef6e0) (Thunderbee owned pipeline).

#### Description:
This pipeline is the Sample Autoapp Release pipeline.

The main function of this pipeline is to simply orchestrate the calling of other pipelines in the Sample Autoapp traditional release E2E flow

Example of Sample Auto App

[Read More in the hello-world-go-app-traditional-release-E2E-Flow Pipeline Documentation](../../../auto_app_release/auto_app_release_parent_flows/documentation/hello-world-go-app-traditional-release-E2E-Flow.md)
* * *

### SAMPLE_AUTOAPP_TRANSFER_ZIP_RELEASE:

This stage runs a Jenkins Job [EIC-AUTO-APP-Transfer-ZIP](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/view/Thunderbee_Jobs/job/EIC-AUTO-APP-Transfer-ZIP/) (Thunderbee owned Jenkins job).

#### Description:
This Job copies a zip file of a tested artifact after a successful E2E run and uploads to a separate artifactory that exclusively contains fully tested versions.
* * *
