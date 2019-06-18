package com.example.pipeline.dto

import com.example.pipeline.step.PipeLineStepImpl

class BuildRequestDTO {

    String applicationName
    String serviceType
    String deploymentRegion
    List<PipeLineStepImpl> pipeLineSteps


    @Override
    String toString() {
        return "BuildRequestDTO{" +
                "applicationName='" + applicationName + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", deploymentRegion='" + deploymentRegion + '\'' +
                ", pipeLineSteps=" + pipeLineSteps +
                '}'
    }
}