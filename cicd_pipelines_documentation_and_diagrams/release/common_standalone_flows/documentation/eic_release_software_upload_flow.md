# EIC Release Software Upload Flow

[TOC]


![eic-release-software-upload-flow](../diagrams/eic_release_software_upload_flow.png)
[eic-release-software-upload-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/568a1610-cd8c-47b4-8c48-5902660e3b66)

## Introduction

This Spinnaker pipeline is used to upload the EIC release to Software Gateway (SWGW).

 * * *

## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| RELEASE_INT_CHART_VERSION | The EIC helmfile version. |
| DEPLOYMENT_MANAGER_VERSION| The DM version to upload. |

## Pipeline Stages

### Get Details of EIC Release:

This stage runs a Jenkins job [GetAppVersionFromHelmfile](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/GetAppVersionFromHelmfile/) (Ticketmaster owned Jenkins job).

#### Description:

This Jenkins job generates an artifacts.properties file containing the application names and versions for a given `INT_CHART_REPO`.

[OSS Calculate App Versions From Helmfile Job Documentation](https://gerrit-gamma.gic.ericsson.se/plugins/gitiles/OSS/com.ericsson.oss.aeonic/oss-integration-ci/+/master/docs/files/calculateAppVersionsFromHelmfile.md)

 * * *
### Generate SW Artifact:
This stage evaluates variables to be referenced downstream. `ADDITIONAL_PACKAGES` are the additional packages and versions to upload to SWGW which are not returned from the 'Get Details of EIC Release' stage. `{"example-key":"example-value",`

#### Description:
This stage evaluates:

- ADDITIONAL_PACKAGES

 * * *

### SW Upload:
This stage runs a Jenkins job [DP-RAF](https://fem2s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/DP-RAF/) (CM team owned Jenkins job).

#### Description:
The `fileInput` is the contents of the artifact.properties (from 'Get Details of EIC Release' stage) converted to JSON. The JSON is then converted to a string and the opening curly-brace `{` is replaced by the `ADDITIONAL_PACKAGES` generated in the above stage.
This Jenkins job uses DP-RAF to perform the upload of SW to SWGW.

### Update Release INT chart version:
This stage runs a Jenkins job [BASE-VERS_Update_Baseline_Version_In_Repository](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/BASE-VERS_Update_Baseline_Version_In_Repository) (Thunderbee owned Jenkins job).

#### Description:
The job updates the baseline version in a given versions file.

### Create RCD JSON:
This stage runs a Jenkins job [CICD-UTILS-Create-Rcd-Json](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/CICD-UTILS-Create-Rcd-Json) (Thunderbee owned Jenkins job).

#### Description:
The job creates a RCD JSON data using Product name, Product Set and Product Set Type

