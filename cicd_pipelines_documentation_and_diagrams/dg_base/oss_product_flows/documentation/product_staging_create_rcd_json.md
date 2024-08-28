[TOC]

# product_staging_create_rcd_json

![product_staging_create_rcd_json](../diagrams/product_staging_create_rcd_json.png)
[product-staging-create-rcd-json](https://spinnaker.rnd.gic.ericsson.se/#/applications/common-e2e-cicd/executions/configure/9c11ef26-c660-4a13-b313-e93483984d03)
This Pipeline is triggered according to a Cron and creates a resource configuration data JSON.

## Pipeline Stages:

### Get last successful run from PED:
This stage does a get request to PED .

#### Description:
This stage queries the Pipeline Execution Data (PED) api to retrieve the productVersion of the last successful execution of a the product-staging pipeline.
 * * *

### Create-Rcd-Json:
This stage runs a Jenkins job [CICD-UTILS-Create-Rcd-Json](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/job/CICD-UTILS-Create-Rcd-Json/) (Thunderbee owned Jenkins job).

#### Description:
This stage creates a resource configuration data JSON
 * * *
