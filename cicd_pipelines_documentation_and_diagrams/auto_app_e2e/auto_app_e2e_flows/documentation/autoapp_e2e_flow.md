[TOC]

# autoapp-e2e-flow

![autoapp_e2e_flow](../diagrams/autoapp_e2e_flow.png)
[Example autoapp Flow PME](https://spinnaker.rnd.gic.ericsson.se/#/applications/autoapp-pme-e2e-cicd/executions?pipeline=eric-oss-pme-E2E-Flow)

## Introduction:
This Pipeline Coordinates the running of an auto apps Product Staging and Release Flows.

### Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| INPUT_FOLDER_LOCATION | This is the path of the folder which will contain the input template files for the CSAR build |
| SSH_REPO_URL | SSH URL to the repo that will contain the input template files for the CSAR build. |
| KICK_OFF_RELEASE | When set to 'true', the release pipeline will be kicked off upon successful execution of the PSO pipeline. |
 * * *


## Pipeline stages

### Integration Testing:

This stage runs an Jenkins Job Containing Integration Tests For an Autoapp.

#### Description:
Integration testing validates the seamless collaboration and data exchange between interconnected auto apps. It helps identify potential issues arising from the integration points and ensures the overall functionality and reliability of EIC.

[View Integration Test Jobs](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/view/AUTOAPP)
* * *
### AUTO_APP_PRODUCT_STAGING_PIPELINE_NAME:

This stage runs an Auto app Product staging pipeline such as the [autoapp-pme-product-staging Flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/autoapp-pme-e2e-cicd/executions/configure/6c5d6b66-f5e4-47b7-8ef3-0fd2172b4a7f) (Thunderbee owned pipeline).

#### Description:
This pipeline is the common auto app product staging pipeline, for all auto apps which are placed under the auto_app_e2e_cicd Spinnaker project.

The auto app pipeline uses the APP-MGR API endpoints of App Onboarding and App Lcm to Onboard and Instantiate the auto app onto EIAP

[Read More in the autoapp-product-staging Pipeline Documentation](../../../auto_app_product_staging/auto_app_product_staging_parent_flows/documentation/autoapp-product-staging.md)
* * *

### AUTO_APP_RELEASE_PIPELINE_NAME:

This stage runs an Auto app Release pipeline such as the [autoapp-pme-traditional-release-E2E-Flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/autoapp-pme-e2e-cicd/executions/configure/a8a1d903-c8b8-43cf-ae54-1249cefa1f56) (Thunderbee owned pipeline).

#### Description:
This pipeline is the common auto app release pipeline for all auto apps which are placed under the auto_app_e2e_cicd Spinnaker project.

The main function of this pipeline is to simply orchestrate the calling of other pipelines in the autoapp traditional release E2E flow

[Read More in the autoapp-traditional-release-e2e-flow Pipeline Documentation](../../../auto_app_release/auto_app_release_parent_flows/documentation/autoapp-traditional-release-e2e-flow.md)
