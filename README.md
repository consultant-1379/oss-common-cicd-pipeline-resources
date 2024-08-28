# oss-common-cicd-pipeline-resources

[TOC]

-----------

## Background

The [OSS Common CICD Pipeline Resources repo](https://gerrit-gamma.gic.ericsson.se/plugins/gitiles/OSS/com.ericsson.oss.cicd/oss-common-cicd-pipeline-resources/+/refs/heads/master) is a central repository for source-controlled Spinnaker pipelines, which are stored as JSON pipelines and CSV parameter file pairs. This allows teams to easily manage and share their pipelines and ensures that they are stored in a consistent and organized manner.

To learn more about the conventions and guidelines for creating and formatting pipelines, please refer to the documentation at [OSS Common CICD Pipeline Resources](https://confluence-oss.seli.wh.rnd.internal.ericsson.com/x/_hINGg). This page contains detailed information on how to structure your pipelines as valid CSV and JSON files, as well as how to create and manage them within the repository.

In addition to the pipeline resources repo, the [OSS Common CICD Automation Pipeline](https://gerrit-gamma.gic.ericsson.se/plugins/gitiles/OSS/com.ericsson.oss.cicd/oss-common-cicd-automation-pipeline/+/refs/heads/master) is a Dockerized Python CLI tool that can be used to generate Spinnaker pipelines from the templates stored in the pipeline resources repo. This tool simplifies the process of creating and deploying pipelines and allows teams to automate the generation of pipelines from pre-defined templates.

For more information on how to use the OSS Common CICD Automation Pipeline tool, please see the documentation at [OSS Common CICD Automation Pipeline Tool](https://confluence-oss.seli.wh.rnd.internal.ericsson.com/x/9xINGg). This page contains detailed instructions on how to use the tool, as well as examples of how it can be used to generate pipelines from templates.

-----------

## Templating Your Spinnaker Pipeline

### Clone Pipeline Resources Repo

* First thing is to clone the repo "oss-common-cicd-pipeline-resources", and replace the signum with your own Ericsson Signum.
* Once the repo is cloned please open it in your respective IDE.

```bash
git clone ssh://<signum>@gerrit-gamma.gic.ericsson.se:29418/OSS/com.ericsson.oss.cicd/oss-common-cicd-pipeline-resources
cd oss-common-cicd-pipeline-resources
git remote set-url origin ssh://gerrit-gamma-read.seli.gic.ericsson.se:29418/OSS/com.ericsson.oss.cicd/oss-common-cicd-pipeline-resources
git remote set-url --push origin ssh://gerrit-gamma.gic.ericsson.se:29418/OSS/com.ericsson.oss.cicd/oss-common-cicd-pipeline-resources
scp -p -P 29418 <signum>@gerrit-gamma.gic.ericsson.se:hooks/commit-msg .git/hooks/
```

### Creating Parameter CSV Files

* To create the pipelines using the templates we need to create the parameter CSV file which holds the parameter values for each pipeline
* This CSV is used to replace specific values in the Pipeline template with the values in the CSV.
* Each new line in the CSV is considered a new pipeline.
* This is useful when rolling out Pipelines for multiple products/applications/microservices that use the same template.
* Open the folder called "cicd_pipelines_parameters_and_templates".
* In this folder, please select the area associated with the pipeline you want to generate or update.
* Then select the flows associated with the pipeline you want to generate or update.
* Create a new file in the parameter_files folder in the repo and name the file according to this [naming convention](#Naming-Convention-For-The-Template-And-Parameter-Files).
  * The header of the CSV file is the parameter names and each row are the respective data for a single pipeline.
  * Important note: All the parameters from the template file to be used should be present in the header of CSV and all rows should have the respective values.

Once file change is pushed to the repository it will go through the pre-code review which will check for basic CSV syntax validation.
Once pre-code is a success your code will be ready for peer code review.

### Creating Spinnaker Pipeline JSON Files

#### Template Pipeline

* Create a POC pipeline from one of the pipelines that you are trying to template.
* Test run this pipeline manually and get a green run for that pipeline. See [Testing Spinnaker Pipeline Changes](#Testing-Spinnaker-Pipeline-Changes).
* Get the main structure for the pipeline template from Spinnaker UI using the Edit as JSON field (Configure →Pipeline Actions → Edit as JSON).
* In this folder, please select the area associated with the pipeline you want to generate or update.
* Then select the flows associated with the pipeline you want to generate or update.
* Create a new file in the pipeline_template folder in the repo and name the file according to this [naming convention](#Naming-Convention-For-The-Template-And-Parameter-Files).
  * Delete the "lastModifiedBy" and "updateTs" fields from the template.
  * Add "application" and "name" fields which should be parameterized and will have the values of the spinnaker application name and pipeline name (These are the main identifiers to create the pipeline).
  * Add the "notifications" field which should be parameterized and will contain the value of the notification email name.
  * Add the "description" field which gives the customer information about the pipeline.
    * Standard template is:
      * "description": "This pipeline is maintained by Spinnaker as a code, any changes made through the UI will be overwritten. Template name: <PIPELINE_NAME_HERE>.json in [oss-common-cicd-automation-pipeline](<https://gerrit-gamma.gic.ericsson.se/#/admin/projects/OSS/com.ericsson.oss.cicd/oss-common-cicd-automation-pipeline>) repo"
  * The fields that are different between the pipelines should be parameterized as variables.
  * Important note: The variable used for the parameter should be unique.

#### UI Lock

* UI lock prevents users from modifying a Spinnaker pipeline through the UI, currently, we leave the allowUnlockUi set to True to prevent blocking users from fixing bugs.

```json
"locked": {
    "allowUnlockUi": true,
    "description": "This pipeline is maintained by Spinnaker as a code, any changes made through the UI will be overwritten",
    "ui": true
 },
```

#### Reference IDs (RefId)

* Each stage will have a RefId which is a reference identifier for that stage.
* This is used when connecting stages. The stage refId should be the same as the name of the pipeline stage.

```json
 "name": "Get Environment Details",
 "refId": "Get Environment Details",
 "requisiteStageRefIds": ["Update Env Status to Refreshing"],
```

#### Stage Timeout

* Stage Timeouts can be added to a stage to increase from the default stage timeout of 3hrs.
* This should be developed upwards, Jenkins stages should have appropriate timeouts to prevent hanging jobs from taking excessive time.
* Child pipeline stages can have their timeout updated.
* Then Parent pipeline stages need to be updated to accommodate this increase in time.

```json
"stageTimeoutMs": 2400000,
```

-----------

## Naming Convention For The Template And Parameter Files

* We need to follow a certain naming convention for new templates/parameters files:
  * Name templates and parameters in the same way, expect file extension (.csv for params and .json for templates)
  * Example:
    * |JSON|CSV|
      |---|---|
      |idun_app_staging_baseline_pipeline.json|idun_app_staging_baseline_pipeline.csv|
  * If the pipeline will have multiple parameters files add a specific suffix to the end of the file name. The first part should be identical.
  * Example:
    * |JSON|CSV|
      |---|---|
      |e2e_app_idun_pipeline.json|e2e_app_idun_pipeline_dmm.csv|
  * Do not use template and parameter words in file names. It is already obvious due to the parent folder name and file extension.
  * Example:
    * |Template|Parameters|
      |---|---|
      |<Common_file_name>.json|<Common_file_name><_optional-specific-suffix>.csv|

-----------

## Testing Spinnaker Pipeline Changes

* When testing Spinnaker pipeline changes, consider a few different changes.
  * A small change to an existing pipeline.
  * A large change to an existing pipeline.
  * Creating a completely new pipeline.

### Pre-requisites

* Create a test folder with your signum in oss-common-cicd-pipeline-resources.
  * Drill down into the cicd_pipelines_parameters_and_templates folder.
  * In the testing folder.
    * Create a new folder called {signum}_testing_folder.
    * Inside created folder:
      * Create a parameters_files folder.
        * In the folder create test_pipeline.csv file.
      * Create a pipeline_template folder.
        * In the folder create test_pipeline.json file.

### Small Change

* What is considered a small change:
  * Stage parameter change.
  * Pipeline parameter change.
  * New parameter.
  * Spinnaker expression change.
* How to make this change:
  * Find pipeline JSON file in oss-common-cicd-pipeline-resources.
  * Update the JSON file with the small change.
  * Once the change has been updated do the following:
    * Copy the pipeline template into test_pipeline.json.
    * Copy the CSV file for the pipeline you want to test into test_pipeline.csv.
      * Ensure that under the SPINNAKER_APPLICATION_NAME heading change the value to "thunderbeetest" in the CSV file. This will create the updated pipeline in the thunderbeetest spinnaker area.
      * Ensure that under SPINNAKER_PIPELINE_NAME heading you prefix your signum to the pipeline name.
  * Push changes up for review.
  * Copy the review refspec value as a parameter into the Thunderbee_Test_OSS_Common_CICD_Pipeline_Generator Jenkins job.
    * Example:

    * ```text
      refs/changes/98/12538698/1
      ```

  * From the drop-down choose the following:
    * area: testing.
    * flow: {signum}_testing_folder.
    * pipeline_template: test_pipeline.json.
    * parameters_file: test_pipeline.csv.
  * Build the Jenkins Job.
  * Once the build completes check thunderbeetest to ensure your pipeline was created correctly.
  * Add this test pipeline change to the code review.
  * Add TB_Review_Ready label to code review.
  * Once changes have been merged delete the pipeline from thunderbeetest area.
  * Update any associated documentation. Ensure customer documentation is always up-to-date.

### Large Change

* What is considered a large change:
  * New stage/stages
  * New branch/branches
* How to make this change:
  * Copy the pipeline JSON you want to make changes to
    * Find pipeline in Spinnaker
    * Configure pipeline
      * Under "Pipeline Actions" click "edit as JSON"
      * Copy JSON
    * In thunderbeetest create a new pipeline
      * Name pipeline same as the original pipeline with the addition of your signum
      * Once the pipeline is created configure the pipeline
      * Under "Pipeline Actions" click "edit as JSON"
      * Copy in JSON from the main pipeline and "Update Pipeline"
  * Once you have a copy of the pipeline in the thunderbeetest area
    * Make changes to the test pipeline
    * Once changes are made get the customer to test the pipeline. If this is not possible test yourself
    * Once changes have been tested and work as expected update pipeline .json file in oss-common-cicd-pipeline-resources
    * If you need a deeper level of testing please follow the steps from point 3 onwards in the "small change" section above
  * Push code change up for review adding tested pipeline in the code review
  * Add TB_Review_Ready label to code review
  * Once changes have been merged delete the pipeline from thunderbeetest area
  * Update any associated documentation. Ensure customer documentation is always up-to-date

### New Pipeline

* What is considered a new pipeline:
  * A pipeline that does not exist currently
* How to make this change:
  * In thunderbeetest create a new pipeline.
  * Once the pipeline is created get the customer to test the pipeline. If this is not possible test yourself.
  * Once the pipeline is tested and works as expected you need to create a template for the pipeline in oss-common-cicd-pipeline-resources
  * Follow this documentation:
    * How to create a valid parameter CSV file
    * How to create a valid spinnaker pipeline as the JSON template
    * Naming Convention for the Template and Parameter files
  * If you need a deeper level of testing please follow the steps from point 3 onwards in the "small change" section above
  * Push code change up for review adding tested pipeline in the code review
  * Add TB_Review_Ready label to code review
  * Once changes have been merged delete a pipeline from thunderbeetest area
  * Update any associated documentation. Ensure customer documentation is always up-to-date
