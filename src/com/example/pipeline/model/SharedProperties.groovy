package com.example.pipeline.model

import com.example.pipeline.enums.BuildStatus
import com.example.pipeline.enums.Stage

/**
 * This is the properties shared across the pipeline in each stages.
 * This information is generally collected during the Prepare Stage of PipeLine execution.
 */
class SharedProperties {
    final def jenkinsScript
    final BuildRequestDetails buildRequestDetails

    BuildStatus buildStatus = BuildStatus.STARTED

    final GitProperties gitProperties = new GitProperties()

    final GradleProperties gradleProperties = new GradleProperties()

    final Map<Stage, ResponseDetails> stageOutput = [:]

    SharedProperties(jenkinsScript, BuildRequestDetails buildRequestDetails) {
        this.jenkinsScript = jenkinsScript
        this.buildRequestDetails = buildRequestDetails
    }


    @Override
    String toString() {
        return "SharedProperties{" +
                "buildRequestDetails=" + buildRequestDetails +
                ", buildStatus=" + buildStatus +
                ", gitProperties=" + gitProperties +
                ", gradleProperties=" + gradleProperties +
                '}'
    }
}