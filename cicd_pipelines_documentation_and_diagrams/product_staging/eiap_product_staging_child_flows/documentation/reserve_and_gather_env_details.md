[TOC]

# reserve-and-gather-env-details

![reserve_and_gather_env_details](../diagrams/reserve_and_gather_env_details.png)
[reserve-and-gather-env-details](https://spinnaker.rnd.gic.ericsson.se/#/applications/product-e2e-cicd/executions/configure/0fbc3d73-7b81-4d26-9d30-f05fc56ffa49)
## Introduction:
This Pipeline Reserves an Environment in RPT, gets the env details and potentially unreserves the environment if there are failures.
## Pipeline Parameters:
| Parameter | Description |
|-----|-----|
| ENV_LABEL | This is the label to search for that is attached to the environments in the Lockable Resource Plugin on Jenkins |
| RCF_TAG | RCF Tag is used to identify the environment by Report Center Registration Stage for Child flows |
| FLOW_URL_TAG | Flow URL Tag is used when locking the invironment to add a tag to descript what has locked the environment for easier tracking |
| WAIT_TIME | This is the time to wait for an Environment to become available. After the time expirers the job will fail out |
| ENV_DETAILS_DIR | This is the directory within the Repo specified within the  Gather-Env-Details Jenkins job where to find the  pooling environment details |
| SPINNAKER_FLOW_ID | This is used when mapped a flow to an environment |
| AGENT_LABEL | Label used by PS agents |
 * * *

## Pipeline stages:

### Report Center Registration:
This stage runs a Jenkins job [Staging-Report-Register](https://fem4s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/Staging-Report-Register) (Regulus owned Jenkins job).

#### Description:
 This stage sends the pipeline execution ID for logging and monitoring

 * * *
### Reserve Env:
This stage runs a Jenkins job [RPT-RC_Reserve-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Reserve-Environment) (Thunderbee owned Jenkins job).

#### Description:
This Job implements a function to reserve a test environment in RPT.
 * * *
### Gather Env Details:
This stage runs a Jenkins job [Gather-Env-Details](https://fem7s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/Gather-Env-Details) (Thunderbee owned Jenkins job).

#### Description:
This Job gathers Environment details from gerrit.

### Unreserve Env:
This stage runs a Jenkins job [RPT-RC_Unreserve-Environment](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/RPT-RC_Unreserve-Environment) (Thunderbee owned Jenkins job).

#### Description:
This Job implements a function to unreserve a test environment in RPT.


### End Flow
Checks preconditions for successful execution of the pipeline.

