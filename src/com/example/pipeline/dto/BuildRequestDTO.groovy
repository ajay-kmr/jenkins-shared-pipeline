package com.example.pipeline.dto

import com.example.pipeline.step.PipeLineStep

class BuildRequestDTO {

    String applicationName
    String serviceType
    String deploymentRegion
    List<PipeLineStep> pipeLineSteps


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