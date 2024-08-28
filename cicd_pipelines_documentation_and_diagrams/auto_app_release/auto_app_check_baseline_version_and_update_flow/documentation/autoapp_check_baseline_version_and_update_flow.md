[TOC]

# autoapp-check-baseline-version-and-update-flow

![autoapp-check-baseline-version-and-update-flow](../diagrams/autoapp_check_baseline_version_and_update_flow.png)
[EACC autoapp-check-baseline-version-and-update-flow](https://spinnaker.rnd.gic.ericsson.se/#/projects/auto_app_e2e_cicd/applications/autoapp-eacc-e2e-cicd/executions/configure/bb6e4653-eba6-4ff9-9233-37cda5431c11)
## Introduction:
Retrieves the current Upgrade baseline version from the 'baseline-versions' repository. Compares it against a version from a successful Upgrade/Install in the "autoapp-check-baseline-version-and-update-flow" pipeline.
If necessary, updates the stored Upgrade baseline version in the repository.

## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| VERSION_FOR_COMPARISON | Version to compare against version in repo |
| ENVIRONMENT_NAME | Name of the environment being used in the pipeline |
| BASELINE_VERSION_KEY | The key of the baseline version that will be retrieved from the repo and, if necessary, updated |

 * * *

## Pipeline Stages:

### Retrieve Version from Repo:
This stage runs a Jenkins job [BASE-VERS_Retrieve_Baseline_Version](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/BASE-VERS_Retrieve_Baseline_Version) (Thunderbee owned Jenkins job).

#### Description:
The job retrieves the baseline version from a given versions file.

 * * *
### Compare Versions:
This stage runs a Jenkins job [BASE-VERS_Compare_Version_With_Baseline_Version](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/BASE-VERS_Compare_Version_With_Baseline_Version) (Thunderbee owned Jenkins job).

#### Description:
The job compares a specified version with the baseline version stored in the baseline-versions repo.
 * * *
### Update Version in Repo:
This stage runs a Jenkins job [BASE-VERS_Update_Baseline_Version_In_Repository](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/BASE-VERS_Update_Baseline_Version_In_Repository) (Thunderbee owned Jenkins job).

#### Description:
The job updates the baseline version in a given versions file.

 * * *
