[TOC]

# k6-Set-up

![k6_set_up](../diagrams/k6_set_up.png)
[k6-Set-up](https://spinnaker.rnd.gic.ericsson.se/#/applications/product-e2e-cicd/executions/configure/27e81f7d-731f-405e-8ded-ab40353f40c2)
## Introduction:
This Job sets up the k6 Tests. It generates CSARs and Onboards the CSARs to EVNFM.
## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| EVNFM_HOSTNAME  | Deployment Hostname for EVNFM |
 * * *

## Pipeline stages:

### K6 - Generate CSAR Files:
This stage runs a Jenkins job [IDUN_Prod_Eng_Generate_CSAR_files](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/IDUN_Prod_Eng_Generate_CSAR_files) (Banba owned Jenkins job).

#### Description:
This Job creates a CSAR File.
 * * *
### K6 - Onboard_CSAR_to_EVNFM:
This stage runs a Jenkins job [IDUN_Prod_Eng_onboard_CSAR_to_EVNFM](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/IDUN_Prod_Eng_onboard_CSAR_to_EVNFM) (Banba owned Jenkins job).

#### Description:
This job has been deprecated and CSARs should be onboarded to EOCM.

