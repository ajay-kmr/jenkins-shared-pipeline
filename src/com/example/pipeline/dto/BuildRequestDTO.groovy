package com.example.pipeline.dto

import com.example.pipeline.step.PipeLineStep
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class BuildRequestDTO {

    String applicationName
    String serviceType
    String deploymentRegion
    List<PipeLineStep> pipeLineSteps


}