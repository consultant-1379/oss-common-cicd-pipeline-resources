[Pipeline Inoperative]

_This pipeline is currently nonfunctional._

[TOC]
# TEST_PIPELINE-eic-connected-release-external-pre-operation-flow
![TEST_PIPELINE-eic-connected-release-external-pre-operation-flow](../diagrams/TEST_PIPELINE-eic-connected-release-external-pre-operation-flow.png)
[TEST_PIPELINE-eic-connected-release-external-pre-operation-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/4c3eba31-baa5-436d-b4ad-7b67c6aef30f)
## Introduction:
This pipeline is a standalone pipeline which performs pre-requisites and creates the workspace for install/upgrade of EIC on an environment. This flow is to facilitate the TEST flows.
 * * *
## Pipeline stages
### Get App Version from HelmFile:
This stage runs a Jenkins job [GetAppVersionFromHelmfile](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/GetAppVersionFromHelmfile) (Ticketmaster owned Jenkins job).
#### Description:
This job retrieves the application versions from the helmfile.
 * * *
### Pre-Operation:
This stage runs a Jenkins job [oss-idun-release-cicd_MANA_Internal_Testing_Pre_Operations](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/oss-idun-release-cicd_MANA_Internal_Testing_Pre_Operations/) (Thunderbee owned Jenkins job).
#### Description:
This job is used in CaaP TEST pipeline. This job performs the pre-requisite for install/upgrade of EIAE helmfile.
