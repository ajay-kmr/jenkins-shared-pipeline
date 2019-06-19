package com.example.pipeline.model

import com.example.pipeline.enums.Stage

/**
 * This details need to be defined in the application which is
 * suppose to be build using Jenkins pipeline.
 * Note:- Each property of this class is optional.
 * If not set will be assigned with default values.
 * The property of this class basically act as a hook to override the
 * default behaviour of pipeline from the application itself
 */
class BuildRequestDetails {

    String applicationName
    String serviceType
    String deploymentRegion
    List<Stage> stages

    static BuildRequestDetails getInstance(Closure buildConfig) {
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
                ", pipeLineSteps=" + stages +
                '}'
    }
}