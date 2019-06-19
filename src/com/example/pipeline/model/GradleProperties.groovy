package com.example.pipeline.model


/**
 * This class is used to collect the Gradle related
 * properties required during the pipeline execution.
 * This information is generally collected during the Prepare Stage of PipeLine execution.
 */
class GradleProperties {
    String applicationName
    String version

    String getVersionWithoutSnapShot() {
        if (version?.toUpperCase()?.contains("-SNAPSHOT")) {
            return version.toUpperCase() - '-SNAPSHOT'
        }
        return version
    }

    @Override
    String toString() {
        return "GradleProperties{" +
                "applicationName='" + applicationName + '\'' +
                ", version='" + version + '\'' +
                ", version without SNAPSHOT='" + versionWithoutSnapShot + '\'' +
                '}'
    }
}

