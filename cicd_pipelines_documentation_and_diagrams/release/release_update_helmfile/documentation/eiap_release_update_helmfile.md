[TOC]

# eiap-release-update-helmfile

![eiap_release_update_helmfile](../diagrams/eiap_release_update_helmfile.png)
[eiap-release-update-helmfile](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/894dbdf8-42aa-4f2c-bf0d-a893cf708a5e)
## Introduction:
This pipeline performs all the necessary steps to Update a Helmfile and bump the EIAP version.
## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| CHART_NAME | Dependency helm chart name. |
| CHART_VERSION | Dependency helm chart version. |
| CHART_REPO | Dependency helm chart URL. |
| GERRIT_REFSPEC | Ref Spec from the Gerrit review. |
| VCS_BRANCH | Branch for the change to be pushed. |
| WAIT_SUBMITTABLE_BEFORE_PUBLISH | For the publish command, wait for the gerrit patch to be set for a verified +1 or +2 or both before submitting, default is false. |
| SLAVE_LABEL | Specify the slave label that you want the job to run on. |
| GERRIT_PREPARE_OR_PUBLISH | Select 'prepare' to create a snapshot. Select 'publish' to publish the helmfile to drop repository. |
| ALLOW_DOWNGRADE | Default is 'false', if set to true, downgrade of dependency is allowed. |
| VERSION_STRATEGY | Possible values: MAJOR, MINOR, PATCH. Step this digit automatically in Chart.yaml after release when dependency change received. |
 * * *

## Pipeline stages:

### Helmfile Fetch Build Upload:
This stage runs a jenkins job [Helmfile-Fetch-Build-Upload](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/Helmfile-Fetch-Build-Upload) (Ticketmaster/Hummingbirds owned Jenkins job).

#### Description:
This Stage prepares and uploads the new helmfile.
 * * *

### Copy site_values:
This stage runs a jenkins job [idun-integration-site-values-update](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/idun-integration-site-values-update/) (Thunderbee owned Jenkins job).

#### Description:
The job creates a copy of site values with the helmfile version before bump.
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
