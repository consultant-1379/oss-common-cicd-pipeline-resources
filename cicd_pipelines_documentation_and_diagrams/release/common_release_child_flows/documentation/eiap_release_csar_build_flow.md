[TOC]

# eiap-release-csar-build-flow

![eiap-release-csar-build-flow](../diagrams/eiap_release_csar_build_flow.png)
[eiap-release-csar-build-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eiap-release-e2e-cicd/executions/configure/c187f4db-555a-4a9a-a331-6dfdc79a5b97)
## Introduction:
This pipeline calls the oss-csar-build-flow pipeline to build CSARs to be included in EIAP.

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
This pipeline is used to build all of the CSARs to be included in EIAP.

 * * *
### Completed

Checks preconditions for successful execution of the pipeline.

