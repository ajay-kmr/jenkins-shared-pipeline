package com.example.pipeline.model

import com.example.pipeline.step.PipeLineStepImpl

class BuildRequestDetails {

    String applicationName
    String serviceType
    String deploymentRegion
    List<PipeLineStepImpl> pipeLineSteps


    public static BuildRequestDetails getInstance(Closure buildConfig) {
        def buildRequestDTO = new BuildRequestDetails()
        buildConfig.resolveStrategy = Closure.DELEGATE_FIRST
        buildConfig.delegate = buildRequestDTO
        buildConfig()
        return buildRequestDTO
    }

    @Override
    String toString() {
        return "BuildRequestDTO{" +
                "applicationName='" + applicationName + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", deploymentRegion='" + deploymentRegion + '\'' +
                ", pipeLineSteps=" + pipeLineSteps?.collect { it?.stageName } +
                '}'
    }
}