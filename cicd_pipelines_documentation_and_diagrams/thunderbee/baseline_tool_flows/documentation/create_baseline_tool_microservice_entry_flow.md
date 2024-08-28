[TOC]

# create-baseline-tool-microservice-entry-flow

![create-baseline-tool-microservice-entry-flow](../diagrams/create_baseline_tool_microservice_entry_flow.png)
[create-baseline-tool-microservice-entry-flow](https://spinnaker.rnd.gic.ericsson.se/#/applications/baselinetoolapp/executions/configure/1f36b9d9-5eff-4304-8a46-61e5a558fc34)


## Introduction

The Create Baseline tool Microservice Entry Flow is to update the Baseline tool with microservice data

* * *

## Pipeline Parameters

| Parameter | Description |
|-----|-----|
| name | Name of the microservice |
| version | Version of the microservice |
| ticket | Ticket number of the microservice change |
| codeReview | Code review URL of the microservice change |
 * * *

## Pipeline stages

### Create Baseline Tool Entry

This stage runs a webhook stage

#### Description

This stage makes POST rest call to [BLT microservice endpoint](https://blt.ews.gic.ericsson.se/api/v1/microservices)

Payload has these parameters:

* name
* version
* ticket
* codeReview
* changes


 * * *

### Check Webhook Status Code

This checks webhook stage's status code before continuing.

#### Description

This checks:

* "Create Baseline tool Entry stage's status code value" is `409` or `201`

 * * *
