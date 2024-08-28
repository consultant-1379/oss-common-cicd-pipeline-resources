# Contributing to oss-common-cicd-pipeline-resources

[TOC]

## How You Can Contribute

We welcome contributions from the community to help improve the OSS Common CICD Pipeline Resources and Automation Pipeline projects. There are several ways that you can get involved and contribute to these projects:

### Making Pipeline Changes

If you have permission from the pipeline customers and owners, you can begin making changes to the pipelines by following the established CI and review process. This process ensures that all changes to the pipelines are reviewed and tested before they are merged into the main repository.

To make changes to a pipeline, you will need to clone the oss-common-cicd-pipeline-resources repository from Gerrit Central. You can then make your changes to the pipeline files and submit them for review using the Gerrit code review tool.

When writing commit messages for your changes, please make sure to include the ticket number that the work is associated with in the title of the message. This will help us to track and organize the changes that are being made to the pipelines.

If you have any questions about pipeline ownership or the review process, you can contact [Team Thunderbee](mailto:PDLENMCOUN@pdl.internal.ericsson.com?subject=Pipeline%20Resources) for more information.

### Providing Feedback and Suggestions

Another way that you can contribute to the OSS Common CICD Pipeline Resources and Automation Pipeline projects is by providing feedback and suggestions. If you have ideas for new features or improvements to the projects, or if you encounter any issues or bugs, you should create a support ticket on the pipeline owners.
If the ownership is part of the [Thunderbee support Scope](https://confluence-oss.seli.wh.rnd.internal.ericsson.com/x/8VTGHg) then create a support ticket on [Team Thunderbee](https://confluence-oss.seli.wh.rnd.internal.ericsson.com/x/fgfEGQ) for CI changes.
For Queries on Pipeline ownership you can contact [Team Thunderbee](mailto:PDLENMCOUN@pdl.internal.ericsson.com?subject=Pipeline%20Resources) for more information.

We welcome all feedback and suggestions, and we will do our best to address any issues or concerns that are raised. By providing feedback and suggestions, you can help us to make the OSS Common CICD Pipeline Resources and Automation Pipeline projects even better.

## CI

Code changes have to pass the pipeline-resources precode review [Bob CI
jobs](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/view/Common%20CICD%20Pipeline%20Resources/job/oss-common-cicd-pipeline-resources_Pre_Code_Review/).
Pushing to the refs/for/master should automatically trigger the CI job which should automatically give a score at the end in Gerrit.
If for any reason this does not happen, then the job needs to be triggered manually.
If you don't have credentials to do a manual trigger, please write to the <PDLENMCOUN@pdl.internal.ericsson.com> mailing list.

## Review Process

Code changes can be made by any relevant team, provided that they follow the established review process. However, pipelines should only be updated with permission from the pipeline customers.
This is to ensure that any changes to the pipeline do not negatively impact its performance or cause unexpected issues.
The pipeline owners are responsible for managing and maintaining the pipeline, and they should be consulted before making any changes to it. If you are unsure of the review process for a particular team, or if you need permission to update a pipeline, please contact the pipeline owners for more information.

### Generating Pipelines

Code changes can be rolled out to Spinnaker using the [OSS-CICD-AUTOMATION_Pipeline_Generator](https://fem5s11-eiffel216.eiffel.gic.ericsson.se:8443/jenkins/view/Common%20CICD%20Automation%20Pipeline/job/OSS-CICD-AUTOMATION_Pipeline_Generator/).
For information on this pipeline you can visit the documentation at [OSS Common CICD Automation Pipeline Management](https://confluence-oss.seli.wh.rnd.internal.ericsson.com/x/ohANGg).
