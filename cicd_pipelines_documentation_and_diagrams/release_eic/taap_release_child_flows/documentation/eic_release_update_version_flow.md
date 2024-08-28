[TOC]

# eic-release-update-version-flow

![eic_release_update_version_flow](../diagrams/eic_release_update_version_flow.png)
[eic-release-update-version-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/c2ef3a0e-65be-45a1-91ac-d8661170dd30)
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

