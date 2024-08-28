[TOC]

# eic-release-k6-standalone-flow

![eic_release_k6_standalone](../diagrams/eic_release_k6_standalone.png)
[eic-release-k6-standalone-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/eic-release-e2e-cicd/executions/configure/c1e6632c-324d-4967-a574-746f4b624fac)
## Introduction:
This is a standalone flow to perform K6 Tests on a given deployment.

## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| DEPLOYMENT_NAME | Name of the deployment to run tests against |
| NAMESPACE | Namespace of environment |
| KUBECONFIG_FILE | Kubernetes configuration file to specify which environment to run tests against |
| RUN_TESTS | Run given tests scenarios and nothing else e.g. CANARY_UPGRADE_01, CANARY_UPGRADE_03 |
| SKIP_TESTS | Skips given test scenarios e.g. CANARY_UPGRADE_01, CANARY_UPGRADE_03 |
| CONFIG_FILES | By default these tests will run e.g., ['main_multiple_iterations.json', 'main_single_iteration.json', 'main_CU2.json'] Override it if needed |
| ADC_HOSTNAME | Hostname for ADC |
| APP_MGR_HOSTNAME | Hostname for App Manager |
| DMAPP_HOSTNAME | Hostname for DMAPP |
| ECM_HOSTNAME | Hostname for ECM |
| ENM_HOSTNAME | Hostname for ENM |
| GAS_HOSTNAME | Hostname for GAS |
| TH_HOSTNAME | Hostname for TH |
| GERRIT_REFSPEC | Ref Spec from the Gerrit review. |
| KAFKA_HOSTNAME | Hostname for KAFKA |
| BDR_HOSTNAME | Hostname for Bulk Data Repository (BDR) |
 * * *

## Pipeline Stages:

### Run K6 Tests:
This stage runs a Jenkins job [EIC_Prod_Eng_test_suite_deploy_job_live_release](https://fem5s11-eiffel052.eiffel.gic.ericsson.se:8443/jenkins/job/EIC_Prod_Eng_test_suite_deploy_job_live_release) (Banba owned Jenkins job).

#### Description:
Job used to run Continuous Tests against IDUN

