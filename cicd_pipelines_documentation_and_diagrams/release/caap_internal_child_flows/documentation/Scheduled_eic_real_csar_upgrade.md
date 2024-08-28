[TOC]

# Scheduled_eic-real-csar-upgrade-flow

![Scheduled_eic_real_csar_upgrade_flow](../diagrams/Scheduled_eic-real-csar-upgrade-flow.png)
[Scheduled_eic-real-csar-upgrade-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/b7a2f12d-3b11-4717-93d4-60316d1d35ad)
## Introduction:
This pipeline is a Scheduled Standalone pipeline which performs an install/upgrade of EIAP software on a Test Environment

This pipeline runs everyday at 6am, 12pm and 6pm via the "eic-real-csar-upgrade" schedule in [RPT](https://rpt.ews.gic.ericsson.se/#/schedules).
 * * *

## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| ENV_NAME | Name of the deployment. |
| DDP_AUTO_UPLOAD | When set to true, enables the DDP auto upload. |
| DEPLOYMENT_MANAGER_REPO | Deployment Manager Repository (for internal Upgrade only). |
| OSS_DEPLOYMENT_MANAGER_VERSION | The version of Deployment Manager to use. |
| INTERNAL_AGENT_NAME | Specify the internal Jenkins agent that you want the job to run on. |
| K6_TESTWARE_VERSION | The version of the K6 testware to be used. |
| INT_CHART_NAME | Name of the integration chart which will be used for the Initial install |
| INT_CHART_REPO | Repo of the Integration Chart which will be used for the Initial Install |
| INT_CHART_VERSION | Version of the Integration Chart which will be used for the Initial Install |
* * *
## Pipeline Stages

### Update Env Status to Reserved:
This stage runs a Jenkins job [RPT-RC_Update-Test-Environment-Status](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Update-Test-Environment-Status) (Thunderbee owned Jenkins job).

#### Description:
This Job is to update the status of a Test Environment in RPT.

 * * *
### Get Environment Details:
This stage runs a Jenkins Job [DSC-DIT-Download-Document-As-Artifact](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/DSC-DIT-Download-Document-As-Artifact) (Thunderbee owned Jenkins job).

#### Description:
This Job is used to retrieve the properties of a Test Environment from DIT.

 * * *
### Get Latest Helmfile Version:
This stage runs a Jenkins job [Get-Latest-ChartOrHelmfile](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/Get-Latest-ChartOrHelmfile) (Ticketmaster/Honeypot owned Jenkins job).

#### Description:
This job gets the latest chart or Helmfile version.
This is passed back into the pipeline as INT_CHART_VERSION

 * * *
### Compare Versions:
This stage runs a Jenkins job [BASE-VERS_Compare_Version_With_Baseline_Version](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/BASE-VERS_Compare_Version_With_Baseline_Version) (Thunderbee owned Jenkins job).

#### Description:
The job compares a specified version with the baseline version stored in the baseline-versions repo.

 * * *
### Upgrade
This stage runs a Spinnaker pipeline [eic-real-csar-upgrade-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/f33e58e5-c011-4875-8060-2fa6a040960e) (Thunderbee owned pipeline).

#### Description:
This pipeline performs an upgrade of EIC software on a Test Environment.
[Pipeline Documentation](../../caap_internal_child_flows/documentation/eic_real_csar_upgrade_flow.md).

 * * *
### Update Version in RPT and DIT to Latest Candidate Version:
This stage runs a Spinnaker pipeline [eiap-release-update-version-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/73d3e805-7a92-4ec3-9f73-ad18d1432a2f) (Thunderbee owned pipeline). [Pipeline Documentation](../../taap_release_child_flows/documentation/eiap_release_update_version_flow.md)

#### Description:
If necessary, updates the Upgrade baseline version in the repo, replacing the existing, stored version with the version used in the pipeline.

 * * *
### Post Flow Checks

Checks preconditions for successful execution of the pipeline.

 * * *
### Unreserve Environment:
This stage runs a Jenkins job [RPT-RC_Unreserve-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Unreserve-Environment) (Thunderbee owned Jenkins job).

#### Description:
This Job implements a function to unreserve a test environment in RPT.

 * * *
### Quarantine Environment:
This stage runs a Jenkins job [RPT-RC_Quarantine-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Quarantine-Environment) (Thunderbee owned Jenkins job).

#### Description:
This Job implements a function to quarantine a Test Environment in RPT.

 * * *
### End Flow

Checks preconditions for successful execution of the pipeline.
 * * *
