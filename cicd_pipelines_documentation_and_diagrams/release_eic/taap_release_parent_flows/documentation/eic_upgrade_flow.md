# eic-upgrade-flow

[TOC]

![eic_upgrade_flow](../diagrams/eic_upgrade_flow.png)
[eic-r2l-upgrade-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/153df311-927a-464a-9db6-d15b81368a14)
[eic-s2l-upgrade-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/d03eb3c8-055a-4fda-bb96-842efb236ecb)


## Introduction

The EIC upgrade loop, which sits to the right of IDUN product staging. The EIC upgrade loop is seperated into two pipelines:

* `eic-r2l-upgrade-flow` / Release to Latest
* `eic-s2l-upgrade-flow` / Sprint to Latest

The purpose of these two separate loops is to protect the integrity of upgrade from a release version to each subsequent latest version, as well as the integrity of a sprint version to each subsequent latest version. This will make software releases less prone to failure due to some customers having longer gaps between upgrades.

Currently the `R2L` flow will run on a released cadence, with `S2L` running against a sprintly cadence.

At the moment there are no automatic triggers for the release pipeline, it's triggered manually only. The wanted position is `S2L`, `R2L` & `II/BUR` pipelines are run automatically as frequently as possible.

After each II and UG stage in the pipeline, if the `II/UG` is successful, the version will be updated in RPT to track it.

* * *

## Pre-requisite

1. EIC Test Environment is added to DIT.
2. EIC Test Environment is updated in DIT and RPT (for version) to house specific data needed for CICD function, please use the following jobs:
    1. [Update-Environment-Details](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Update-Environment-Details/)
    2. [DSC-DIT-Update-Key-Value-Pairs/](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/DSC-DIT-Update-Key-Value-Pairs/)
3. EIC Test Environment has a backup directory created on remote SFTP.
4. EIC Test Environment has any necessary files ready for any specific CICD function. e.g. [oss-release-io-solution](/README.md) repo for observability, kube configs stored in Jenkins for II/UG etc.

* * *

## Pipeline Parameters

| Parameter | Description |
|-----|-----|
| ENV_NAME | Please leave this field blank if you want to Reserve a Test Environment from the pool. The Test Environment to use for the flow. |
| USE_REAL_CSAR | Option to use real CSAR. If false then dummy CSAR will be used |
| TAGS | List of tags for applications that have to be deployed. If this is left blank, the values will be taken from DIT for Upgrade stage. |
| DOCKER_REGISTRY | Set this to the docker registry to execute the deployment from. Used when deploying from Officially Released CSARs |
| DOCKER_REGISTRY_CREDENTIALS | Jenkins secret ID for the Docker Registry. Not needed if deploying from armdocker.rnd.ericsson.se |
| INT_CHART_VERSION | Version of the Integration Chart which will be used during Install / Upgrade |
| DEPLOYMENT_MANAGER_REPO | Deployment Manager Repository. |
| DEPLOYMENT_MANAGER_VERSION | Deployment Manager version. |
| K6_VERSION | K6 version |
| USE_DM_PREPARE | When set to true uses the site values generated from the Deployment Manager prepare command for the deployment. |
| RSR_TAG | RSR Tag is used to identify the environment by Report Center Registration Stage for Release stage |
| DDP_AUTO_UPLOAD | When set to true, enables the DDP auto upload |
| CLEANUP_TYPE | The Type of cleanup that needs to be done. FULL will cleanup the deployment helm releases, crd helm releases, crd components, clusterroles, cluster rolebindings, namespaces (deployment and crd). Where as PARTIAL will only cleanup the deployment namespace. FULL cleanup option should NOT be used in such cases where single cluster is hosting multiple deployments. |
| RUN_ENM_INTEGRATION_TEST | When set to true, ENM integration tests will be run |
| SITE_VALUE_FILE_BUCKET_NAME | Name of the bucket that is storing the site values that is stored in object store. Defaults to eic_site_values_template. The Parameter is used for From State install/upgrade |
| TO_STATE_SITE_VALUE_FILE | The path to to state site values file, setting this param overrides the value taken from DIT |
 * * *

## Pipeline stages

### Report Center Registration

This stage runs a Jenkins job [Staging-Report-Register](https://fem4s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/Staging-Report-Register) (Regulus owned Jenkins job).

#### Description

 This stage sends the pipeline execution ID for logging and monitoring.

 * * *

### Get Latest Helmfile Version

This stage runs a Jenkins job [Get-Latest-ChartOrHelmfile](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/Get-Latest-ChartOrHelmfile)

#### Description

Retrieves the latest chart or helmfile and sets the `INT_CHART_VERSION` for downstream use.
 * * *

### CSAR Build

This stage runs a spinnaker pipeline [eic-release-csar-build-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/7a7c372b-a633-4c03-a4fe-ee5f08c433bd) (Thunderbee owned pipeline) [Pipeline Documentation](/cicd_pipelines_documentation_and_diagrams/release/common_release_child_flows/documentation/eic_release_csar_build_flow.md).

#### Description

* This pipeline calls the "oss-csar-build-flow" pipeline to build CSARs to be included in EIC.
* The CSAR Build stage runs only if the `USE_REAL_CSAR` parameter is set to `True` (Set to `False` by default).
* This stage is disabled by default because using the build CSAR test coverage is being handled in the [CaaP loop (MANA loop)](/cicd_pipelines_documentation_and_diagrams/release/caap_parent_flow/documentation/eic_connected_release_e2e_flow.md).

 * * *

### Reserve Environment

This stage runs a Jenkins job [RPT-RC_Reserve-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Reserve-Environment) (Thunderbee owned Jenkins job).

#### Description

This Job implements a function to Reserve a Test Environment in RPT.
 * * *

### Set Test Environment

This stage evaluates variables to be referenced downstream.

#### Description

This stage evaluates:

* RESOURCE_NAME

* * *

### Direct Reservation of Environment

This stage runs a Jenkins job [RPT-RC_Update-Test-Environment-Status](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Update-Test-Environment-Status) (Thunderbee owned Jenkins job).

#### Description

This Stage runs a Jenkins job to update the status of a Test Environment in RPT to Reserved.
 * * *

### Get Environment Details

This stage runs a Spinnaker pipeline [eic-release-get-environment-details-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/0d236ab2-bce0-4d4a-807d-0ccb638d4ce7) (Thunderbee owned pipeline). [Pipeline Documentation](/cicd_pipelines_documentation_and_diagrams/release/taap_release_child_flows/documentation/eic_release_get_environment_details_flow.md)

#### Description

This stage retrieves Test Environment specific values from RPT.
 * * *

### Evaluate Variables

This stage evaluates variables to be referenced downstream.

#### Description

This stage evaluates:

* TO_STATE_SITE_VALUES_OVERRIDE_FILE
* FROM_STATE_SITE_VALUES_OVERRIDE_FILE
* TO_STATE_SITE_VALUES_FILE
* SKIP_TESTS
* RUN_TESTS
* ECM_HOSTNAME
* ENM_HOSTNAME

 * * *

### Set Docker Registry Details

This stage evaluates variables to be referenced downstream.

#### Description

This stage evaluates:

* DOCKER_REGISTRY
* DOCKER_REGISTRY_CREDENTIALS

 * * *

### Initial Install to Release From State

This stage runs a spinnaker pipeline [eic-release-install-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/903cef19-f589-4286-bb50-45ab52f92de0) (Thunderbee owned pipeline) [Pipeline Documentation](/cicd_pipelines_documentation_and_diagrams/release/common_release_child_flows/documentation/eic_release_install_flow.md).

#### Description

This stage performs an install to the Release From State.
 * * *

### Update Version in RPT and DIT Post II to Release From State

This stage runs a spinnaker pipeline [eic-release-update-version-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/c2ef3a0e-65be-45a1-91ac-d8661170dd30) (Thunderbee owned pipeline) [Pipeline Documentation](/cicd_pipelines_documentation_and_diagrams/release_eic/taap_release_child_flows/documentation/eic_release_update_version_flow.md).

#### Description

This stage updates the Environments `INT_CHART_VERSION` in RPT.
 * * *

### Upgrade & Assert_Staging

This stage runs a spinnaker pipeline [eic-release-upgrade-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/e6c06ca6-1798-48ae-bc07-6406460db4a2) (Thunderbee owned pipeline) [Pipeline Documentation](/cicd_pipelines_documentation_and_diagrams/release/common_release_child_flows/documentation/eic_release_upgrade_flow.md).

#### Description

This Pipeline performs the steps necessary for an Upgrade of EIC Environment.
This stage is followed by the [Update Upgrade Baseline](#update-upgrade-baseline) stage.
 * * *

### Update Version in RPT and DIT Post UG

This stage runs a spinnaker pipeline [eic-release-update-version-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/c2ef3a0e-65be-45a1-91ac-d8661170dd30) (Thunderbee owned pipeline) [Pipeline Documentation](/cicd_pipelines_documentation_and_diagrams/release_eic/taap_release_child_flows/documentation/eic_release_update_version_flow.md).

#### Description

This stage updates the Environments `INT_CHART_VERSION` in RPT.
 * * *

### Post Flow Checks

This checks preconditions before continuing.

#### Description

This checks:

* "Get Environment Details" is `SUCCEEDED` or `SKIPPED`
* "Upgrade & Assert_Staging" is `SUCCEEDED` or `SKIPPED`
* "Initial Install to Release From State" is `SUCCEEDED` or `SKIPPED`

 * * *

### Unreserve Environment

This stage runs a Jenkins job [RPT-RC_Unreserve-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Unreserve-Environment) (Thunderbee owned Jenkins job).

#### Description

This Job implements a function to Unreserve a Test Environment in RPT.
 * * *

### Update Upgrade Baseline

This stage runs a Spinnaker pipeline [eic-release-check-baseline-version-and-update-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/cc89af18-2f3c-4a6b-b27e-c37856c2a08a) (Thunderbee owned pipeline)  [Pipeline Documentation](/cicd_pipelines_documentation_and_diagrams/release/taap_release_child_flows/documentation/eic_release_check_baseline_version_and_update_flow.md).

#### Description

The "eic-release-check-baseline-version-and-update-flow" pipeline is a Spinnaker pipeline that is used to manage the Upgrade baseline version for the EIC release process. The pipeline is responsible for the following tasks:

1. Retrieve the Upgrade baseline version currently stored in the [baseline-versions](https://gerrit-gamma.gic.ericsson.se/plugins/gitiles/OSS/com.ericsson.oss.cicd/baseline-versions/+/refs/heads/master/versions.json) repository.
2. Compare the retrieved version with the version used for a successful Upgrade in the "eic-traditional-release-e2e-Flow" pipeline.
3. Update the Upgrade baseline version stored in the repository, if appropriate.
The "Update Upgrade Baseline" stage of the pipeline will only execute under the following conditions:

Upgrade has been successful.
 * * *

### End Flow

Checks preconditions for successful execution of the pipeline.
 * * *
