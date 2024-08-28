[TOC]

# sample-autoapp-onboard-instantiation-verification-Flow

![sample-autoapp-instantiation-verification-Flow](../diagrams/sample-autoapp-onboard-instantiation-verification-Flow.png)

Example of Sample Auto App onboard instantiation verification pipeline:

[hello-world-go-app-instantiation-verification-Flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/autoapp-hello-world-go-app-e2e-cicd/executions?pipeline=hello-world-go-app-onboard-instantiation-verification-Flow)

## Introduction:
This pipeline is used to onboard and instantiate a Sample Auto App onto the necessary test environment. Before the pipeline will onboard the Sample Auto App, it will run pre onboarding testing of the Sample Auto App and after the Sample Auto App is instantiated, it will run post instantiation testing of the Sample Auto App. For any queries on pre onboarding or post instantiation testing, please contact the relevant auto app team as Thunderbee have no control over these stages.

The product staging and release pipelines for Sample Auto Apps, such as the [Hello World Go App](https://developer.intelligentautomationplatform.ericsson.net/#tutorials/go-sample-app) and [Hello World Python App](https://developer.intelligentautomationplatform.ericsson.net/#tutorials/sample-app-in-python), differ from those for conventional Auto Apps, like EACC and PME.

While the release flow for conventional Auto Apps runs the following:

* Intial Instantiation To Last Released Auto App Version
* Upgrade To Latest Candidate Auto App Version
* Initial Instantiation To Latest Candidate Auto App Version

For Sample Auto Apps only the following will be done:

* Intial Instantiation To Last Released Sample Auto App Version

The Sample Auto App pipeline uses the APP-MGR API endpoints of App Onboarding and App Lcm to Onboard and Instantiate the auto app onto EIC. Please use the provided links for further information about the [App Onboarding](https://developer.intelligentautomationplatform.ericsson.net/#capabilities/app-onboarding) and [App Lcm](https://developer.intelligentautomationplatform.ericsson.net/#capabilities/app-lifecycle-management) APIs.

### Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| CHART_NAME | Name of the auto app helm chart |
| CHART_VERSION | Version of the auto app helm chart |
| CHART_REPO | Repo of the auto app helm chart |
| ENVIRONMENT_NAME | Name of the Environment in which the auto app will be onboarded and instantiated |
| CSAR_STORAGE_INSTANCE | Storage Instance (Staging and Release) to push the CSARs to |
| CSAR_STORAGE_REPO_STAGING | Storage directory (Staging) to push the CSARs to |
| ADMIN_USER_CREDENTIALS | ADMIN_USER_CREDENTIALS to run pipeline with. |
| INGRESS_LOGIN_PASSWORD | Ingress Password for Pre/Post testing pipelines |
| INGRESS_LOGIN_USER | Ingress Username for Pre/Post testing pipelines |
 * * *

## Pipeline Stages:

### Get Environment Details:
This stage runs a Jenkins job [RPT-RC_Retrieve-Environment-Details](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Retrieve-Environment-Details/) (Thunderbee owned Jenkins job).

#### Description:
This Job is to retrieve the properties of a Test Environment in RPT.
 * * *

### Cleanup:
This stage runs a Jenkins job [APP-MGR-PC-Delete-All-Onboarded-Auto-Apps](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/APP-MGR-PC-Delete-All-Onboarded-Auto-Apps/) (Thunderbee owned Jenkins job).

#### Description:
The job implements a function to delete all the onboarded Auto Apps for a given Auto App name.
 * * *

### Pre Onboarding Testing:
This stage is controlled by the owner of the sample auto app and in this stage, the owning team will specify the specific pre onboarding testing pipeline to run
 * * *

### Onboarding:
This stage runs a Jenkins job [APP-MGR-PC-Onboard-Auto-App](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/APP-MGR-PC-Onboard-Auto-App/) (Thunderbee owned Jenkins job).

#### Description:
This job implements a function to Onboard an Auto App.
 * * *

### Instantiation:
This stage runs a Jenkins Job [APP-MGR-PC-Instantiate-Auto-App](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/APP-MGR-PC-Instantiate-Auto-App/) (Thunderbee owned Jenkins job).

#### Description:
This job implements a function to Instantiate an Auto App.
 * * *

### Post Instantiation Testing:
This stage is controlled by the owner of the sample auto app and in this stage, the owning team will specify the specific post instantiation testing pipeline to run
 * * *
