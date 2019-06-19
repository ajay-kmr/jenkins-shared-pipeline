package com.example.pipeline.model

import com.example.pipeline.enums.BuildStatus

class SharedProperties {
    def jenkinsScript
    BuildRequestDetails buildRequestDetails

    BuildStatus buildStatus = BuildStatus.STARTED

    GitProperties gitProperties = new GitProperties()

    GradleProperties gradleProperties = new GradleProperties()

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