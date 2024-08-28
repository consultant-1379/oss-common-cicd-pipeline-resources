[TOC]

# eiap-release-update-version-flow

![eiap_release_update_version_flow](../diagrams/eiap_release_update_version_flow.png)
[eiap-release-update-version-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/73d3e805-7a92-4ec3-9f73-ad18d1432a2f)
## Introduction:
Updates the version in the document stored in DIT.

## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| ENVIRONMENT_NAME | Name of the environment |
| INT_CHART_VERSION | The integration chart version |
 * * *

## Pipeline Stages:

### Update Environment Details in DIT:
This stage runs a Jenkins job [DSC-DIT-Update-Single-Key-Value](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/DSC-DIT-Update-Single-Key-Value) (Thunderbee owned Jenkins job).

#### Description:
This Job is to update the properties of a Test Environment in DIT.

 * * *

### Update Environment Details in RPT:
This stage runs a Jenkins job [RPT-RC_Update-Environment-Details](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Update-Environment-Details) (Thunderbee owned Jenkins job).

#### Description:
This Job is to update the properties of a Test Environment in RPT.

 * * *

### Post Flow Checks

Checks preconditions for successful execution of the pipeline.
 * * *
### Quarantine Environment:
This stage runs a Jenkins job [RPT-RC_Quarantine-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Quarantine-Environment) (Thunderbee owned Jenkins job).

#### Description:
This Job implements a function to quarantine a Test Environment in RPT.

 * * *
### End Flow

Checks preconditions for successful execution of the pipeline.

