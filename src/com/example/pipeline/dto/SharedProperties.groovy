package com.example.pipeline.dto

import com.example.pipeline.enums.BuildStatus

class SharedProperties {
    def jenkinsScript
    BuildRequestDTO buildRequestDTO

    BuildStatus buildStatus = BuildStatus.STARTED

    GitProperties gitProperties = new GitProperties()

    GradleProperties gradleProperties = new GradleProperties()

    SharedProperties(jenkinsScript, BuildRequestDTO buildRequestDTO) {
        this.jenkinsScript = jenkinsScript
        this.buildRequestDTO = buildRequestDTO
    }

    class GradleProperties {
        String applicationName
        String version

        String getVersionWithoutSnapShot() {
            if (version.toUpperCase().contains("-SNAPSHOT")) {
                return version.toUpperCase() - '-SNAPSHOT'
            }
            return version
        }
    }

    class GitProperties {
        String remoteOriginUrl
        String committerName
        String committerEmail
        String commitDate
        String commitMessage
    }
}