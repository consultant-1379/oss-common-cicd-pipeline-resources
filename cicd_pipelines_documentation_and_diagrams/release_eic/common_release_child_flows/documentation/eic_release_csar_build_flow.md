[TOC]

# eic-release-csar-build-flow

![eic-release-csar-build-flow](../diagrams/eic_release_csar_build_flow.png)
[eic-release-csar-build-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/7a7c372b-a633-4c03-a4fe-ee5f08c433bd)
## Introduction:
This pipeline calls the oss-csar-build-flow pipeline to build CSARs to be included in EIC.

## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| INT_CHART_NAME | Name of the helmfile that holds the Application to build into CSAR's |
| INT_CHART_VERSION | Version of the Integration Chart that holds the new microservice |
| INT_CHART_REPO | Repo of the Integration Chart that holds the new microservice |
| PATH_TO_HELMFILE | Full path to the helmfile yaml file within the extracted product helmfile archive. |
| STATE_VALUES_FILE | Site values file used to generate what CSAR are to be built |
 * * *

## Pipeline Stages:

### csar-build-flow:
This stage runs a Spinnaker pipeline [oss-csar-build-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/common-e2e-cicd/executions/configure/a1761b58-9846-46db-8833-9c6e3b3eb293) (Ticketmaster owned pipeline).

#### Description:
This pipeline is used to build all of the CSARs to be included in EIC.

 * * *
### Completed

Checks preconditions for successful execution of the pipeline.

