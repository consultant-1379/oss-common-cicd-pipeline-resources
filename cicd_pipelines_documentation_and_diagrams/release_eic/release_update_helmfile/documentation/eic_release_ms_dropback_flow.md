[TOC]

# eic_release_ms_dropback_flow

![eic_release_ms_dropback_flow](../diagrams/eic_release_ms_dropback_flow.png)
[eic-release-ms-dropback-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/4f329d70-b072-4578-95e2-e46bdd29edbf)
## Introduction:

The pipeline is used for Emergency Package (EP) handling. This pipeline is used for performing a microservice dropback into the product.

## Pre-Requisite

The Gerrit project should be configured to provide the user (reluserfid) the following permissions:

* References: refs/*
  * Create Reference
  * Create Annotated Tag
  * Forge Committer Identity
  * Label Code-Review
  * Label Verified
  * Rebase
  * Submit

* Reference: refs/heads/*
  * Push

These permissions are necessary to enable the user to modify, commit, and push changes to the branch that was created as part of the EP Handling flow. Please reach out to [Team Hummingbirds](https://eteamspace.internal.ericsson.com/x/fhRke) for getting the user configured in the Gerrit projects.

## Pipeline Parameters:

| Parameter | Description |
|-----|-----|
|APPLICATION_GERRIT_PROJECT|Gerrit project (E.g. OSS/com.ericsson.oss.eiae/eiae-helmfile).|
|CREATE_APPLICATION_BRANCH|Select 'true' if you want to create a branch for application or select 'false' to use an existing branch|
|APPLICATION_BRANCH_VERSION|Will pick up the latest tag in application Gerrit repo. If a specific version X.Y.Z is provided, it will checkout from the version specified. Else if latest is selected it will checkout from the latest version of the master branch|
|APPLICATION_VCS_BRANCH|Branch for the change to be pushed. If you are creating a branch for the application in the flow then leave this blank.|
|PRODUCT_GERRIT_PROJECT|Gerrit project (E.g. OSS/com.ericsson.oss.eiae/eiae-helmfile).|
|CREATE_PRODUCT_BRANCH|Select 'true' if you want to create a branch for product or select 'false' to use an existing branch|
|PRODUCT_BRANCH_VERSION|Will pick up the latest tag in product Gerrit repo. If a specific version X.Y.Z is provided, it will checkout from the version specified. Else if latest is selected it will checkout from the latest version of the master branch|
|PRODUCT_VCS_BRANCH|Branch for the change to be pushed. If you are creating a branch for the product in the flow then leave this blank.|
|APPLICATION_CHART_PATH|Relative path to helm chart in git repo.|
|GERRIT_PREPARE_OR_PUBLISH|Select 'prepare' to create a snapshot. Select 'publish' to publish the helmfile to drop repository.|
|VERSION_STRATEGY|Possible values: MAJOR, MINOR, PATCH. Step this digit automatically in Chart.yaml after release when dependency change received.|
|WAIT_SUBMITTABLE_BEFORE_PUBLISH|For the publish command, wait for the gerrit patch to be set for a verified +1 or +2 or both before submitting, default is false.|
|MS_CHART_NAME|Comma-separated dependency helm chart name list. E.g.: eric-pm-server, eric-data-document-database-pg|
|MS_CHART_REPO|Comma-separated dependency helm chart url list. E.g.: <https://arm.rnd.ki.sw.ericsson.se/artifactory/proj-pm-1,https://arm.rnd.ki.sw.ericsson.se/artifactory/proj-pm-2>|
|MS_CHART_VERSION|Comma-separated dependency helm chart version list. E.g.: 1.0.0+66, 2.3.0+57|
|APPLICATION_GIT_REPO_URL|gerrit https url to helm chart git repo. Example: <https://gerrit-gamma.gic.ericsson.se/adp-cicd/demo-app-release-chart>|
|APPLICATION_HELM_DROP_REPO|Drop Helm chart repository url for the application. Eg. <https://arm.seli.gic.ericsson.se/artifactory/proj-eric-oss-drop-helm-local/>|
|APPLICATION_HELM_INTERNAL_REPO|Internal Helm chart repository url for the Application. Eg. <https://arm.seli.gic.ericsson.se/artifactory/proj-eric-oss-ci-internal-helm/>|
|PRODUCT_HELM_DROP_REPO|Drop Helm chart repository url for the Product. Eg. <https://arm.seli.gic.ericsson.se/artifactory/proj-eric-oss-drop-helm-local/>|
|PRODUCT_HELM_INTERNAL_REPO|Internal Helm chart repository url for the Prodct. Eg. <https://arm.seli.gic.ericsson.se/artifactory/proj-eric-oss-ci-internal-helm/>|
|CSAR_BUILD|Set this to true to execute CSAR Build stage or false to skip it|
 * * *

## Pipeline stages

### Create Application Branch:
This stage runs a jenkins job [oss-idun-release-cicd_Create_Branch](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/oss-idun-release-cicd_Create_Branch) (Thunderbee owned Jenkins job).

#### Description:
The underlying Jenkins job is used to create a branch for the provided gerrit repository.
 * * *

### MS dropback:
This stage runs a jenkins job [OSS-Integration-Fetch-Build-Upload-Using-ADP-Inca](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/OSS-Integration-Fetch-Build-Upload-Using-ADP-Inca) (Ticketmaster owned Jenkins job).

#### Description:
This job is used to generate either a chart or helmfile which can be used in the different testing phases. It can be used to generate either a snapshot of the artifact or a released version of the artifact. The Jenkins file uses the ADP INCA image as the backbone of its execution.
 * * *

### Create Product Branch:

This stage runs a jenkins job [oss-idun-release-cicd_Create_Branch](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/oss-idun-release-cicd_Create_Branch) (Thunderbee owned Jenkins job).

#### Description:
The underlying Jenkins job is used to create a branch for the provided gerrit repository.
 * * *

### Application dropback:
This stage runs a jenkins job [OSS-Integration-Fetch-Build-Upload-Using-ADP-Inca](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/OSS-Integration-Fetch-Build-Upload-Using-ADP-Inca) (Ticketmaster owned Jenkins job).

#### Description:
This job is used to generate either a chart or helmfile which can be used in the different testing phases. It can be used to generate either a snapshot of the artifact or a released version of the artifact. The Jenkins file uses the ADP INCA image as the backbone of its execution.
 * * *

### Copy site_values to OST:
This stage runs a jenkins job [oss-idun-release-cicd_Copy_EIC_Site_Values_To_OST](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/oss-idun-release-cicd_Copy_EIC_Site_Values_To_OST) (Thunderbee owned Jenkins job).

#### Description:
This job creates a copy of the EIC site values with the helmfile version before bump in OST bucket [eic_site_values_versioned](https://atvost.athtem.eei.ericsson.se/buckets/view/6470cc1f705c0c7eea109ced).
 * * *

### CSAR Build:
This stage runs a spinnaker pipeline [eic-release-csar-build-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/7a7c372b-a633-4c03-a4fe-ee5f08c433bd) (Ticketmaster owned pipeline).

#### Description:
This stage is a Spinnaker pipeline and is used to build all of the CSARs to be included in EIC.
The CSAR Build stage runs only if the USE_REAL_CSAR parameter is set to true. The default value for the pipeline is set to true.
