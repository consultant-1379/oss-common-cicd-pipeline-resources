[TOC]

# eiap-release-end-of-sprint-activities

![eiap_release_end_of_sprint_activities](../diagrams/eiap_release_end_of_sprint_activities.png)
[eiap-release-end-of-sprint-activities](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/05b14642-9b94-428d-950d-c249f7c80282)
## Introduction:
At the end of a sprint or when a software release is requested, the Release team branches from the master branch and builds using the Helmfile from that branch. This approach allows for faster releases and the ability to track down any issues without the impact of continuous changes being merged to the master branch.

The end of sprint activities for Release involve two parts:

1. Executing the 'eiap-release-end-of-sprint-activities' pipeline.
2. In case of a rollback for any application, updating and publishing the Helmfile to the artifactory by executing the 'eiap-release-update-helmfile' pipeline.
Both of these pipelines are described in further detail in the following section.

## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| VERSION | Will pick up the latest tag in eiae-helmfile repo. If a specific version X.Y.Z is provided, it will checkout from the version specified. |
| SLAVE_LABEL | Agent to run the job on. |
 * * *

## Pipeline stages:

### Branch Out - IDUN Level:
This stage runs a Jenkins job [oss-idun-release-cicd_Create_Branch](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/oss-idun-release-cicd_Create_Branch/) (Thunderbee owned Jenkins job).

#### Description:
This Pipeline creates a `VERSION_YYWW_track` branch of the[EIAE HelmFile Repo](https://gerrit-gamma.gic.ericsson.se/#/admin/projects/OSS/com.ericsson.oss.eiae/eiae-helmfile,branches) from master.
 * * *
### Copy site_values:
This stage runs a Jenkins job [idun-integration-site-values-update](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/idun-integration-site-values-update/) (Thunderbee owned Jenkins job).

#### Description:
This job creates a copy of the EIAP site values with the helmfile version before bump in the repo [oss-integration-ci](https://gerrit-gamma.gic.ericsson.se/plugins/gitiles/OSS/com.ericsson.oss.aeonic/oss-integration-ci/+/refs/heads/master/ci/jenkins/files/copyEiapSiteValues.Jenkinsfile).
 * * *

### Copy site_values to OST:
This stage runs a Jenkins job [oss-idun-release-cicd_Copy_EIC_Site_Values_To_OST](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/oss-idun-release-cicd_Copy_EIC_Site_Values_To_OST/) (Thunderbee owned Jenkins job).

#### Description:
This job creates a copy of the EIC site values with the helmfile version before bump in OST bucket [eic_site_values_versioned](https://atvost.athtem.eei.ericsson.se/buckets/view/6470cc1f705c0c7eea109ced).
 * * *

### Update Latest Candidate Baseline Version:
This stage runs a Jenkins job [BASE-VERS_Update_Baseline_Version_In_Repository](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/BASE-VERS_Update_Baseline_Version_In_Repository) (Thunderbee owned Jenkins job).

#### Description:
The job updates the baseline version in a given versions file.
 * * *

### CSAR Build:
This stage runs a Spinnaker pipeline [eiap-release-csar-build-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/c187f4db-555a-4a9a-a331-6dfdc79a5b97) (Ticketmaster owned pipeline).

#### Description:
This stage is a Spinnaker pipeline and is used to build all of the CSARs to be included in EIAP.
The CSAR Build stage runs only if the USE_REAL_CSAR parameter is set to true. The default value for the pipeline is set to true.
